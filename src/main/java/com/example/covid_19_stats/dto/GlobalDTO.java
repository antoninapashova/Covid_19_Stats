package com.example.covid_19_stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GlobalDTO {

    @JsonProperty("NewConfirmed")
    public int newConfirmed;
    @JsonProperty("TotalConfirmed")
    public int totalConfirmed;
    @JsonProperty("NewDeaths")
    public int newDeaths;
    @JsonProperty("TotalDeaths")
    public int totalDeaths;
    @JsonProperty("NewRecovered")
    public int newRecovered;
    @JsonProperty("TotalRecovered")
    public int totalRecovered;
    @JsonProperty("Date")
    public String dateAndTime;


}
