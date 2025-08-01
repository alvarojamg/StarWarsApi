package com.api.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;


public record StarWarsPlanetsResponse(
        String message,
        @JsonProperty("total_records") int totalRecords,
        @JsonProperty("total_pages") int totalPages,
        String previous,
        String next,
        List<PlanetResult> results,
        @JsonProperty("apiVersion") String apiVersion,
        ZonedDateTime timestamp,
        Support support,
        Social social
) {
    public record PlanetResult(
            String uid,
            String name,
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