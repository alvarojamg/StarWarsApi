package com.api.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterSearchDto {
    private String name;
    private String height;
    private String mass;
    private String hairColor;
    private String gender;
    private String homeword;
    private String dob; //fecha de nacimiento
    private String img;
}