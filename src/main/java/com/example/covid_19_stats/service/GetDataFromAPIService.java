package com.example.covid_19_stats.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GetDataFromAPIService {
    void convertDataToDTO(String data) throws JsonProcessingException;
    String getDataFromAPI();
}
