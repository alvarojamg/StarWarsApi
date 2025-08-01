package com.api.demo.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class CharacterDto {
    private String name;
    private String height;
    private String mass;
    private String hairColor;
    private String gender;
    private String homeword;
    private String dob; //fecha de nacimiento
    private List<FilmBasicInfo> films;
    private String img;
}
