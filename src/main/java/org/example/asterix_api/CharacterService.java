package org.example.asterix_api;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final IdService idService;

    public CharacterService(CharacterRepository characterRepository, IdService idService) {
        this.characterRepository = characterRepository;
        this.idService = idService;
    }

    public List<Character> findAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> findById(String id) {
        return characterRepository.findById(id);
    }
    public Character addCharacter(Character character) {
        String id = idService.generateId();
        Character newCharacter = new Character(id, character.name(), character.age(), character.profession());
        return characterRepository.save(newCharacter);
    }

    public Character updateCharacter(Character updatedCharacter) {
        return characterRepository.save(updatedCharacter);
    }

    public void deleteCharacter(String id) {
        characterRepository.deleteById(id);
    }
}