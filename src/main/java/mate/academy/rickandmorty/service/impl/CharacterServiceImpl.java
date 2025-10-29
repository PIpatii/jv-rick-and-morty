package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.Random;
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
        Random random = new Random();
        long count = characterRepository.count();
        long randomId = 1 + random.nextLong(count);
        return characterRepository.findById(randomId)
                .map(characterMapper::toDto)
                .orElseGet(this::getRandomCharacter);
    }

    @Override
    public List<CharacterDto> getAllCharacterContain(String name) {
        List<Character> characters = characterRepository.findAllByNameContainingIgnoreCase(name);
        return characters.stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
