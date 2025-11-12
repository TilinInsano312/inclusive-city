package com.ufro.microservice.location_API.config;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final GoogleMapsConfig googleMapsConfig;

    public AppConfig(GoogleMapsConfig googleMapsConfig) {
        this.googleMapsConfig = googleMapsConfig;
    }

    //Cliente de google
    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(googleMapsConfig.getApiKey())
                .build();
    }
}
