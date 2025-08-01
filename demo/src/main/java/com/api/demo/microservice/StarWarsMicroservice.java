package com.api.demo.microservice;

import com.api.demo.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "starwarsholder", url = "https://www.swapi.tech/api")
public interface StarWarsMicroservice {
    @GetMapping("/people")
    StarWarsApiResponse getPeople(@RequestParam("page") int page,
                                  @RequestParam("limit") int limit);
    @GetMapping("/people/{id}")
    StarWarsCharacterResponse getCharacterById(@PathVariable("id") Long id);

    @GetMapping("/planets/{id}")
    StarWarsPlanetResponse getPlanetById(@PathVariable("id") String id);

    @GetMapping("/films")
    StarWarsFilmsResponse getFilms();

    @GetMapping("/people")
    StarWarsApiResponse searchBy(@RequestParam("search") String value);

    @GetMapping("/planets")
    StarWarsPlanetsResponse getPlanets();
}
