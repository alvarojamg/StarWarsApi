package com.api.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;

public record StarWarsCharacterResponseDev(
        String name,
        String height,
        String mass,
        @JsonProperty("hair_color") String hairColor,
        @JsonProperty("skin_color") String skinColor,
        @JsonProperty("eye_color") String eyeColor,
        @JsonProperty("birth_year") String birthYear,
        String gender,
        String homeworld,
        List<String> films,
        List<String> species,
        List<String> vehicles,
        List<String> starships,
        ZonedDateTime created,
        ZonedDateTime edited,
        String url
) {}