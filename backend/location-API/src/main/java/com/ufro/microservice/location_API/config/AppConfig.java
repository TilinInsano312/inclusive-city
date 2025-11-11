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

    @Bean
    public GeoApiContext geoApiContext() {
        // Creamos el cliente de Google Maps
        // Se reutilizará en toda la aplicación
        return new GeoApiContext.Builder()
                .apiKey(googleMapsConfig.getApiKey())
                .build();
    }
}
