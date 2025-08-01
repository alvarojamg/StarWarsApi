package com.api.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.Map;

public record StarWarsCharacterResponse(
        String message,
        PersonResult result,
        String apiVersion,
        ZonedDateTime timestamp,
        Support support,
        Social social
) {

    public record PersonResult(
            PersonProperties properties,
            String _id,
            String description,
            String uid,
            @JsonProperty("__v") int version
    ) {}

    public record PersonProperties(
            ZonedDateTime created,
            ZonedDateTime edited,
            String name,
            String gender,
            @JsonProperty("skin_color") String skinColor,
            @JsonProperty("hair_color") String hairColor,
            String height,
            @JsonProperty("eye_color") String eyeColor,
            String mass,
            String homeworld,
            @JsonProperty("birth_year") String birthYear,
            String url
    ) {}

    public record Support(
            String contact,
            String donate,
            Map<String, PartnerDiscount> partnerDiscounts
    ) {}

    public record PartnerDiscount(
            String link,
            String details
    ) {}

    public record Social(
            String discord,
            String reddit,
            String github
    ) {}
}