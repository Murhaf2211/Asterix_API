package org.example.asterix_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {

    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping("/characters")
    public List<Character> getCharacters() {
        return characterRepository.findAll();
    }
}
