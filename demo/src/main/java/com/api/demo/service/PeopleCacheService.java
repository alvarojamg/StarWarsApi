package com.api.demo.service;

import com.api.demo.dto.PersonResult;
import com.api.demo.dto.StarWarsApiResponse;
import com.api.demo.dto.StarWarsCharacterResponse;
import com.api.demo.microservice.StarWarsMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleCacheService {
    @Autowired
    StarWarsMicroservice starWarsMicroservice;
    private final Integer page =  17;
    private final Integer limit = 82;
    @Cacheable(value = "peopleCache")
    public List<StarWarsCharacterResponse.PersonProperties> getPeopleDetail(){

        StarWarsApiResponse personResults = starWarsMicroservice.getPeople(page,limit);
        List<PersonResult> people = personResults.results();

        List<StarWarsCharacterResponse.PersonProperties> peopleDetail = people.parallelStream()
                .map(result -> {
                            Long id = result.uid();
                            StarWarsCharacterResponse response = starWarsMicroservice.getCharacterById(id);

                            return response.result().properties();
                        }
                ).collect(Collectors.toList());

        return peopleDetail;
    }

    @Cacheable(value = "peopleGeneralCache", key = "#page + '-' + #limit")
    public StarWarsApiResponse getPeopleGeneral(Integer page, Integer limit){

        StarWarsApiResponse personResults = starWarsMicroservice.getPeople(page,limit);
        return personResults;
    }
}
