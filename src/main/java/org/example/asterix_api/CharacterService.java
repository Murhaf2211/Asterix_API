package org.example.asterix_api;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> findAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> findById(String id) {
        return characterRepository.findById(id);
    }

    public Character updateCharacter(Character updatedCharacter) {
        return characterRepository.save(updatedCharacter);
    }

    public void deleteCharacter(String id) {
        characterRepository.deleteById(id);
    }
}