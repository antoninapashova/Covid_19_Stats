package com.example.covid_19_stats.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="base_objects")
public class BaseObject extends BaseEntity{
    private String message;
    private Global global;
    private Set<Country> countries;
    private LocalDateTime date;

    public BaseObject() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @OneToOne
    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }


    @OneToMany(mappedBy = "baseObject", fetch = FetchType.EAGER)
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
