package com.api.demo.service;

import com.api.demo.dto.CharacterDto;
import com.api.demo.dto.StarWarsCharacterResponse;

import java.util.List;

public interface ICharacter {

    List<CharacterDto> getCharacters(Integer page, Integer limit);

    List<StarWarsCharacterResponse.PersonProperties> searchCharacter(String name,String homeWord, String gender, String hairColor);
}
