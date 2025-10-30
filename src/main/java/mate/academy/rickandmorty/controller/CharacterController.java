package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rick and Morty API", description = "Application for searching characters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rick_and_morty")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/search")
    @Operation(summary = "search by name",
            description = "get a list of all characters using a name")
    public List<CharacterDto> searchByName(@RequestParam String name) {
        return characterService.getAllCharacterContain(name);
    }

    @GetMapping
    @Operation(summary = "get a random character", description = "get a random character")
    public CharacterDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }
}
