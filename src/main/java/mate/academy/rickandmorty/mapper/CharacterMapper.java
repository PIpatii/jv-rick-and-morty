package mate.academy.rickandmorty.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CharacterMapper {
    CharacterDto toDto(Character character);

    Character toEntity(CharacterDto characterDto);

    default CharacterDto jsonToDto(JsonNode node) {
        CharacterDto characterDto = new CharacterDto();
        if (node.has("id")) {
            characterDto.setExternalId(node.get("id").asLong());
        }
        if (node.has("name")) {
            characterDto.setName(node.get("name").asText());
        }
        if (node.has("status")) {
            characterDto.setStatus(node.get("status").asText());
        }
        if (node.has("gender")) {
            characterDto.setGender(node.get("gender").asText());
        }
        return characterDto;
    }
}
