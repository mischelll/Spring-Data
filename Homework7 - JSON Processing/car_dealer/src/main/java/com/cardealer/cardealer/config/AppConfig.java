package com.cardealer.cardealer.config;

import com.cardealer.cardealer.utils.impls.ValidationUtilImpl;
import com.cardealer.cardealer.utils.interfaces.ValidationUtil;
import com.google.gson.*;
import com.google.gson.internal.bind.DateTypeAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class AppConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                .create();

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
