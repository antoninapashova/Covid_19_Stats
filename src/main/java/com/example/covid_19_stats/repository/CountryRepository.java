package com.example.covid_19_stats.repository;


import com.example.covid_19_stats.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    Optional<Country> findCountryByCountryCode(String countryCode);
}
