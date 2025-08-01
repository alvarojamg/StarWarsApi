package com.api.demo.dto;


import java.util.List;
import java.util.Map;

public record StarWarsApiResponse(
        String message,
        Integer total_records,
        Integer total_pages,
        String previous,
        String next,
        List<PersonResult> results,
        String apiVersion,
        String timestamp,
        Support support,
        Social social
) {
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