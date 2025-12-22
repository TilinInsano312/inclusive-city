package com.ufro.microservice.location_API.incidence.service.impl;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Likelihood;
import com.google.cloud.vision.v1.SafeSearchAnnotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SafeSearchService {

    private final CloudVisionTemplate cloudVisionTemplate;

    public SafeSearchService(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }
    public boolean processImage(byte[] imageBytes) {
        Resource imageResource = new ByteArrayResource(imageBytes);
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                imageResource, Feature.Type.SAFE_SEARCH_DETECTION);
        if (response.hasError()) {
            log.error("Error al procesar la imagen: {}", response.getError().getMessage());
            return false;
        }

        return isSafe(response.getSafeSearchAnnotation());
    }

    private boolean isSafe(SafeSearchAnnotation safeSearch) {
        if (safeSearch == null || safeSearch.getAdult() == Likelihood.UNKNOWN) {
            log.warn("No se pudo determinar la seguridad de la imagen (UNKNOWN)");
            return false;
        }
        log.info("Adult: {}", safeSearch.getAdult().name());
        log.info("Violence: {}", safeSearch.getViolence().name());
        log.info("Racy: {}", safeSearch.getRacy().name());
        log.info("Medical: {}", safeSearch.getMedical().name());
        log.info("Spoof: {}", safeSearch.getSpoof().name());
        return !isLikely(safeSearch.getAdult()) &&
                !isLikely(safeSearch.getViolence()) &&
                !isLikely(safeSearch.getRacy()) &&
                !isLikely(safeSearch.getMedical()) &&
                !isLikely(safeSearch.getSpoof());
    }
    private boolean isLikely(Likelihood likelihood) {
        return likelihood == Likelihood.LIKELY || likelihood == Likelihood.VERY_LIKELY;
    }
}