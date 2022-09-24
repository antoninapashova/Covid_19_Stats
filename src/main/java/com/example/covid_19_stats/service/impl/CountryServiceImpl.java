package com.example.covid_19_stats.service.impl;


import com.example.covid_19_stats.entity.Country;
import com.example.covid_19_stats.model.CountryModel;
import com.example.covid_19_stats.repository.CountryRepository;
import com.example.covid_19_stats.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryModel getCountryByCountryCode(String countryCode) {
        Country country = countryRepository.findCountryByCountryCode(countryCode.toUpperCase()).orElse(null);
        return modelMapper.map(country, CountryModel.class);
    }
}
