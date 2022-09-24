package com.example.covid_19_stats.web;

import com.example.covid_19_stats.model.CountryModel;
import com.example.covid_19_stats.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<CountryModel> getCountryByCountryCode(@PathVariable String countryCode){
        CountryModel countryByCountryCode = countryService.getCountryByCountryCode(countryCode);
        if(countryByCountryCode==null){
            return ResponseEntity.
                    notFound().
                    build();
        }
        return ResponseEntity.ok(countryByCountryCode);
    }
}
