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
            StarWarsFilmsResponse starWarsFilmsResponse = starWarsMicroservice.getFilms();

            return people.results().parallelStream()
                    .map(result -> {
                        Long id = result.uid();

                        Predicate<StarWarsFilmsResponse.FilmResult> characterFilter = film ->
                                film.properties().characters().stream()
                                        .anyMatch(url -> url.endsWith("/" + id));


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
            List<StarWarsCharacterResponse.PersonProperties> peopleDetail) {

        String planetSearch = homeword == null
                ? ""
                : homeword.trim().toLowerCase();

        List<StarWarsPlanetsResponse.PlanetResult> matchingPlanets =
                starWarsMicroservice.getPlanets()
                        .results()
                        .stream()
                        .filter(planet -> planet.name() != null
                                && planet.name().toLowerCase().contains(planetSearch))
                        .collect(Collectors.toList());

        Set<String> planetUrls = matchingPlanets.stream()
                .map(StarWarsPlanetsResponse.PlanetResult::url)
                .collect(Collectors.toSet());

        return peopleDetail.stream()
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

        String nameCl      = stringCleaner(name);
        String genderCl    = stringCleaner(gender);
        String hairColorCl = stringCleaner(hairColor);

        List<Predicate<StarWarsCharacterResponse.PersonProperties>> predicates = new ArrayList<>();
        if (nameCl      != null) predicates.add(p -> p.name().toLowerCase().contains(nameCl));
        if (genderCl    != null) predicates.add(p -> p.gender().equalsIgnoreCase(genderCl));
        if (hairColorCl != null) predicates.add(p -> p.hairColor().toLowerCase().contains(hairColorCl));

        if (predicates.isEmpty()) {
            return people;
        }
        Predicate<StarWarsCharacterResponse.PersonProperties> combined =
                predicates.stream().reduce(x -> false, Predicate::or);

        return people.stream()
                .filter(combined)
                .collect(Collectors.toList());
    }

    private String stringCleaner(String value){
        String valueCl = value!= null ? value.trim().toLowerCase() : null;
        return valueCl;
    }
}
