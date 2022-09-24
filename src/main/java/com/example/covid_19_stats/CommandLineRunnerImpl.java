package com.example.covid_19_stats;


import com.example.covid_19_stats.service.GetDataFromAPIService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final GetDataFromAPIService getDataFromAPIService;

    public CommandLineRunnerImpl(GetDataFromAPIService getDataFromAPIService) {
        this.getDataFromAPIService = getDataFromAPIService;
    }

    @Override
    public void run(String... args) throws Exception {
        String dataFromAPI = getDataFromAPIService.getDataFromAPI();
        getDataFromAPIService.convertDataToDTO(dataFromAPI);
    }
}
