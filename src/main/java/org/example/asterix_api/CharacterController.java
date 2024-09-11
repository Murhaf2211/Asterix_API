package org.example.asterix_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // GET /asterix/characters
    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterService.findAllCharacters();
        return ResponseEntity.ok(characters);
    }

    // GET /asterix/characters/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable String id) {
        Optional<Character> character = characterService.findById(id);
        return character.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /asterix/characters/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable String id, @RequestBody Character character) {
        if (!id.equals(character.id())) {
            return ResponseEntity.badRequest().build();  // The ID in the path and body must match
        }
        Character updatedCharacter = characterService.updateCharacter(character);
        return ResponseEntity.ok(updatedCharacter);
    }

    // DELETE /asterix/characters/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
