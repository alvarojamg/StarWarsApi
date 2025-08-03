package com.api.demo.controller;

import com.api.demo.dto.CharacterDto;
import com.api.demo.dto.StarWarsCharacterResponse;
import com.api.demo.service.ICharacter;
import com.api.demo.util.CharacterValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("starwars")
@Validated
@RequiredArgsConstructor
public class CharactersController {
    private final ICharacter iCharacter;
    private final CharacterValidator validator;
    @GetMapping("/people")
    public ResponseEntity<List<CharacterDto>> getPeople(
            @RequestParam(value = "page")  @Min(1) @Max(8) Integer page,
            @RequestParam(value = "limit")  @Min(1) @Max(8) Integer limit
    ){
        List<CharacterDto> characters = iCharacter.getCharacters(page, limit);
        return  new ResponseEntity<List<CharacterDto>>(characters, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StarWarsCharacterResponse.PersonProperties>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "homeworld", required = false) String homeWord,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "hairColor", required = false) String hairColor
    ){
        validator.validateParams(name, gender, hairColor, homeWord);
        List<StarWarsCharacterResponse.PersonProperties> characters = iCharacter.searchCharacter(name, homeWord,gender,hairColor);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }
}
