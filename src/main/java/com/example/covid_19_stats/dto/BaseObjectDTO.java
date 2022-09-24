package com.example.covid_19_stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class BaseObjectDTO {

    @JsonProperty("ID")
    public String id;
    @JsonProperty("Message")
    public String message;
    @JsonProperty("Global")
    public GlobalDTO globalDTO;
    @JsonProperty("Countries")
    public Set<CountryDTO> countries;
    @JsonProperty("Date")
    public String date;



}