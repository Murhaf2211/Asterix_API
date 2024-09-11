package org.example.asterix_api;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private IdService idService;

    @InjectMocks
    private CharacterService characterService;

    private Character asterix;

    @BeforeEach
    void setUp() {
        asterix = new Character("1", "Asterix", 35, "Krieger");
    }
    @Test
    void addCharacter_ShouldSaveCharacterWithGeneratedId() {
        String newId = "generated-id";
        when(idService.generateId()).thenReturn(newId);
        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Character newCharacter = new Character(null, asterix.name(), asterix.age(), asterix.profession()); // ID is null
        Character savedCharacter = characterService.addCharacter(newCharacter);

        assertEquals(newId, savedCharacter.id());
        assertEquals(asterix.name(), savedCharacter.name());
        assertEquals(asterix.age(), savedCharacter.age());
        assertEquals(asterix.profession(), savedCharacter.profession());
        verify(idService, times(1)).generateId();
        verify(characterRepository, times(1)).save(any(Character.class));
    }
    @Test
    void findAllCharacters_ShouldReturnAllCharacters() {
        List<Character> expectedCharacters = List.of(asterix, new Character("2", "Obelix", 35, "Lieferant"));
        when(characterRepository.findAll()).thenReturn(expectedCharacters);

        List<Character> actualCharacters = characterService.findAllCharacters();

        assertEquals(expectedCharacters, actualCharacters);
        verify(characterRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnCharacter_WhenCharacterExists() {
        when(characterRepository.findById("1")).thenReturn(Optional.of(asterix));

        Optional<Character> foundCharacter = characterService.findById("1");

        assertTrue(foundCharacter.isPresent());
        assertEquals(asterix, foundCharacter.get());
        verify(characterRepository, times(1)).findById("1");
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCharacterDoesNotExist() {
        when(characterRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Character> foundCharacter = characterService.findById("1");

        assertFalse(foundCharacter.isPresent());
        verify(characterRepository, times(1)).findById("1");
    }

    @Test
    void updateCharacter_ShouldReturnUpdatedCharacter() {
        Character updatedAsterix = new Character("1", "Asterix", 36, "Krieger");
        when(characterRepository.save(updatedAsterix)).thenReturn(updatedAsterix);

        Character result = characterService.updateCharacter(updatedAsterix);

        assertEquals(updatedAsterix, result);
        verify(characterRepository, times(1)).save(updatedAsterix);
    }

    @Test
    void deleteCharacter_ShouldDeleteCharacter() {
        characterService.deleteCharacter("1");

        verify(characterRepository, times(1)).deleteById("1");
    }
}