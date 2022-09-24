package com.example.covid_19_stats.repository;

import com.example.covid_19_stats.entity.BaseObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaseObjectRepository extends JpaRepository<BaseObject, UUID> {
}
