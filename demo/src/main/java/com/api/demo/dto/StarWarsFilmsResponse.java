package com.api.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public record StarWarsFilmsResponse(
        String message,
        List<FilmResult> result,
        String apiVersion,
        ZonedDateTime timestamp,
        Support support,
        Social social
) {
    public record FilmResult(
            FilmProperties properties,
            @JsonProperty("_id") String id,
            String description,
            String uid,
            @JsonProperty("__v") int version
    ) {}

    public record FilmProperties(
            ZonedDateTime created,
            ZonedDateTime edited,
            List<String> starships,
            List<String> vehicles,
            List<String> planets,
            String producer,
            String title,
            @JsonProperty("episode_id") int episodeId,
            String director,
            @JsonProperty("release_date") String releaseDate, // String para formato personalizado
            @JsonProperty("opening_crawl") String openingCrawl,
            List<String> characters,
            List<String> species,
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
