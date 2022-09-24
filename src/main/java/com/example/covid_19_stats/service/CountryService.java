package com.example.covid_19_stats.service;


import com.example.covid_19_stats.model.CountryModel;

public interface CountryService {
    CountryModel getCountryByCountryCode(String countryCode);
}
