package mate.academy.rickandmorty.service.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterClient implements ApplicationRunner {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final CharacterMapper characterMapper;
    private final CharacterRepository characterRepository;

    public List<CharacterDto> importCharacters() {
        String url = BASE_URL;
        List<CharacterDto> result = new ArrayList<>();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            while (url != null) {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());
                JsonNode root = mapper.readTree(response.body());
                JsonNode resultsNode = root.get("results");

                if (resultsNode != null && resultsNode.isArray()) {
                    for (JsonNode node : resultsNode) {
                        CharacterDto dto = new CharacterDto();
                        if (node.has("id")) {
                            dto.setExternalId(node.get("id").asLong());
                        }
                        if (node.has("name")) {
                            dto.setName(node.get("name").asText());
                        }
                        if (node.has("status")) {
                            dto.setStatus(node.get("status").asText());
                        }
                        if (node.has("gender")) {
                            dto.setGender(node.get("gender").asText());
                        }
                        result.add(dto);
                    }
                }

                if (!root.get("info").get("next").isNull()) {
                    url = root.get("info").get("next").asText();
                } else {
                    url = null;
                }
            }
            return result;
        } catch (InterruptedException | IOException ex) {
            throw new RuntimeException("Cannot get characters from external API", ex);
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        importCharacters().stream()
                .map(characterMapper::toEntity)
                .filter(c -> !characterRepository.existsByExternalId(c.getExternalId()))
                .forEach(characterRepository::save);
    }
}
