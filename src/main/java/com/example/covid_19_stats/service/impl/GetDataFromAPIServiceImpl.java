package com.example.covid_19_stats.service.impl;

import com.example.covid_19_stats.dto.BaseObjectDTO;
import com.example.covid_19_stats.dto.CountryDTO;
import com.example.covid_19_stats.dto.GlobalDTO;
import com.example.covid_19_stats.dto.PremiumDTO;
import com.example.covid_19_stats.entity.BaseObject;
import com.example.covid_19_stats.entity.Country;
import com.example.covid_19_stats.entity.Global;
import com.example.covid_19_stats.entity.Premium;
import com.example.covid_19_stats.repository.BaseObjectRepository;
import com.example.covid_19_stats.repository.CountryRepository;
import com.example.covid_19_stats.repository.GlobalRepository;
import com.example.covid_19_stats.repository.PremiumRepository;
import com.example.covid_19_stats.service.GetDataFromAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetDataFromAPIServiceImpl implements GetDataFromAPIService {

    private final ObjectMapper objectMapper;

    private final CountryRepository countryRepository;
    private final BaseObjectRepository baseObjectRepository;
    private final GlobalRepository globalRepository;
    private final PremiumRepository premiumRepository;
    private final ModelMapper modelMapper;
    private final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS]'Z'";

    public GetDataFromAPIServiceImpl(ObjectMapper objectMapper,
                                     CountryRepository countryRepository, BaseObjectRepository baseObjectRepository,
                                     GlobalRepository globalRepository, PremiumRepository premiumRepository, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.countryRepository = countryRepository;
        this.baseObjectRepository = baseObjectRepository;
        this.globalRepository = globalRepository;
        this.premiumRepository = premiumRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String getDataFromAPI() {

        String url = "https://api.covid19api.com/summary";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public void convertDataToDTO(String data) throws JsonProcessingException {
        BaseObjectDTO baseObjectDTO = objectMapper.readValue(data, BaseObjectDTO.class);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        saveDataToDatabase(baseObjectDTO);
    }

    private Premium savePremium(CountryDTO country) {
        PremiumDTO premiumDTO = country.premiumDTO;
        Premium map = modelMapper.map(premiumDTO, Premium.class);
        return premiumRepository.save(map);
    }

    private Global saveGlobal(GlobalDTO globalDTO) {
        if (globalRepository.count() == 0) {
            Global global = new Global();
            global.setNewConfirmed(globalDTO.newConfirmed);
            global.setNewDeaths(globalDTO.newDeaths);
            global.setNewRecovered(globalDTO.newRecovered);
            global.setTotalDeaths(globalDTO.totalDeaths);
            global.setTotalConfirmed(globalDTO.totalConfirmed);
            global.setTotalRecovered(globalDTO.totalRecovered);
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(PATTERN);
            LocalDateTime date = LocalDateTime.parse(globalDTO.dateAndTime, inputFormatter);
            global.setDateAndTime(date);
            globalRepository.save(global);
            return global;
        }
        return null;

    }

    private Set<Country> saveCountriesToDatabase(Set<CountryDTO> countries) {
        if (countryRepository.count() == 0) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(PATTERN);

            Set<Country> countrySet = countries.stream().map(c -> {
                Country country = new Country();
                country.setId(UUID.fromString(c.id));
                country.setCountry(c.country);
                country.setCountryCode(c.countryCode);
                country.setSlug(c.slug);
                country.setNewConfirmed(c.newConfirmed);
                country.setNewDeaths(c.newDeaths);
                country.setNewRecovered(c.newRecovered);
                country.setTotalConfirmed(c.totalConfirmed);
                country.setTotalDeaths(c.totalDeaths);
                country.setTotalRecovered(c.totalRecovered);
                LocalDateTime date = LocalDateTime.parse(c.dateAndTime, inputFormatter);
                country.setDateAndTime(date);
                Premium premium = savePremium(c);
                country.setPremium(premium);
                return countryRepository.save(country);
            }).collect(Collectors.toSet());
            return countrySet;
        }

        return null;

    }

    private void saveDataToDatabase(BaseObjectDTO data) throws JsonProcessingException {
        if (baseObjectRepository.count() == 0) {
            BaseObject baseObject = new BaseObject();

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(PATTERN);
            LocalDateTime date = LocalDateTime.parse(data.date, inputFormatter);
            baseObject.setDate(date);

            baseObject.setMessage(data.message);
            baseObject.setId(UUID.fromString(data.id));

            baseObject.setCountries(saveCountriesToDatabase(data.countries));
            baseObject.setGlobal(saveGlobal(data.globalDTO));

            baseObjectRepository.save(baseObject);
        }
    }
}
