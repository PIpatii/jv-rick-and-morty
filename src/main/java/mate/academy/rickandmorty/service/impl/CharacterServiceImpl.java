package mate.academy.rickandmorty.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterDto getRandomCharacter() {
        if (characterRepository.count() == 0) {
            throw new IllegalStateException("No character found");
        }
        return characterMapper.toDto(characterRepository.getRandomCharacter());
    }

    @Override
    public List<CharacterDto> getAllCharacterContain(String name) {
        List<Character> characters = characterRepository.findAllByNameContainingIgnoreCase(name);
        return characters.stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
