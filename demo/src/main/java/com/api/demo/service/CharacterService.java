package com.api.demo.service;

import com.api.demo.dto.*;
import com.api.demo.microservice.StarWarsMicroservice;
import com.api.demo.util.enums.Errors;
import com.api.demo.util.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService implements  ICharacter{
    private final StarWarsMicroservice starWarsMicroservice;
    private final  PeopleCacheService peopleCacheService;

    private final String imgExample = "https://www.swapi.tech/api/imge/";
    @Override
    public List<CharacterDto> getCharacters(Integer page, Integer limit) {

        try {
            StarWarsApiResponse people = starWarsMicroservice.getPeople(page,limit);

            return people.results().parallelStream()
                    .map(result -> {
                        Long id = result.uid();

                        Predicate<StarWarsFilmsResponse.FilmResult> characterFilter = film ->
                                film.properties().characters().stream()
                                        .anyMatch(url -> url.endsWith("/" + id));

                        StarWarsFilmsResponse starWarsFilmsResponse = starWarsMicroservice.getFilms();
                        List<StarWarsFilmsResponse.FilmResult> allFilms = starWarsFilmsResponse.result();
                        List<StarWarsFilmsResponse.FilmResult> films = allFilms.stream()
                                .filter(characterFilter)
                                .collect(Collectors.toList());

                        List<FilmBasicInfo> basicInfoList = films.stream()
                                .map(filmResult -> new FilmBasicInfo(
                                        filmResult.properties().title(),
                                        filmResult.properties().director(),
                                        filmResult.properties().producer()
                                ))
                                .collect(Collectors.toList());

                        StarWarsCharacterResponse response = starWarsMicroservice.getCharacterById(id);
                        StarWarsCharacterResponse.PersonProperties character = response.result().properties();
                        String homeUrl = character.homeworld();
                        int lastSlashIndex = homeUrl.lastIndexOf('/');
                        String idPlanet = homeUrl.substring(lastSlashIndex + 1);

                        StarWarsPlanetResponse planetResponse = starWarsMicroservice.getPlanetById(idPlanet);
                        StarWarsPlanetResponse.PlanetProperties planetProperties = planetResponse.result().properties();

                        return CharacterDto.builder()
                                .dob(character.birthYear())
                                .name(character.name())
                                .height(character.height())
                                .mass(character.mass())
                                .hairColor(character.hairColor())
                                .gender(character.gender())
                                .homeword(planetProperties.name())
                                .films(basicInfoList)
                                .img(imgExample+id)
                                .build();
                    })
                    .collect(Collectors.toList());

        }catch (Exception e){
            throw new CommonException(Errors.ERROR_INTERNAL);
        }
    }

    @Override
    public List<StarWarsCharacterResponse.PersonProperties> searchCharacter(String name, String homeWord, String gender, String hairColor) {
        try {
            List<StarWarsCharacterResponse.PersonProperties> peopleDetail = peopleCacheService.getPeopleDetail();

            if (homeWord != null){
                List<StarWarsCharacterResponse.PersonProperties> peopleByHomeWord =  searchByHomeWord(homeWord, peopleDetail);
                return peopleByHomeWord;
            }

            return searchPeople(peopleDetail,name,gender,hairColor);

        }catch (Exception e){
            throw new CommonException(Errors.ERROR_INTERNAL);
        }
    }

    private List<StarWarsCharacterResponse.PersonProperties> searchByHomeWord(
            String homeword,
            List<StarWarsCharacterResponse.PersonProperties> peopleBy) {

        String termLc = homeword == null
                ? ""
                : homeword.trim().toLowerCase();

        List<StarWarsPlanetsResponse.PlanetResult> matchingPlanets =
                starWarsMicroservice.getPlanets()
                        .results()
                        .stream()
                        .filter(planet -> planet.name() != null
                                && planet.name().toLowerCase().contains(termLc))
                        .collect(Collectors.toList());

        Set<String> planetUrls = matchingPlanets.stream()
                .map(StarWarsPlanetsResponse.PlanetResult::url)
                .collect(Collectors.toSet());

        return peopleBy.stream()
                .filter(person -> {
                    String hw = person.homeworld();
                    return hw != null && planetUrls.contains(hw);
                })
                .collect(Collectors.toList());
    }
    private List<StarWarsCharacterResponse.PersonProperties> searchPeople(
            List<StarWarsCharacterResponse.PersonProperties> people,
            String name,
            String gender,
            String hairColor) {

        String nameLower = name != null ? name.toLowerCase() : null;
        String genderLower = gender != null ? gender.toLowerCase() : null;
        String hairColorLower = hairColor != null ? hairColor.toLowerCase() : null;

        return people.parallelStream()
                .filter(person ->
                        (nameLower == null || person.name().toLowerCase().contains(nameLower)) &&
                                (genderLower == null || person.gender().equalsIgnoreCase(genderLower)) &&
                                (hairColorLower == null || person.hairColor().toLowerCase().contains(hairColorLower))
                )
                .collect(Collectors.toList());
    }
}
