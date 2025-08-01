package com.api.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.Map;

public record StarWarsPlanetResponse(
        String message,
        PlanetResult result,
        String apiVersion,
        ZonedDateTime timestamp,
        Support support,
        Social social
) {

    public record PlanetResult(
            PlanetProperties properties,
            String _id,
            String description,
            String uid,
            @JsonProperty("__v") int version
    ) {}

    public record PlanetProperties(
            ZonedDateTime created,
            ZonedDateTime edited,
            String climate,
            @JsonProperty("surface_water") String surfaceWater,
            String name,
            String diameter,
            @JsonProperty("rotation_period") String rotationPeriod,
            String terrain,
            String gravity,
            @JsonProperty("orbital_period") String orbitalPeriod,
            String population,
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