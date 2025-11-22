//package com.ufro.microservice.location_API.incidence.service.impl;
//
//import com.google.cloud.vision.v1.AnnotateImageResponse;
//import com.google.cloud.vision.v1.Feature;
//import com.google.cloud.vision.v1.Likelihood;
//import com.google.cloud.vision.v1.SafeSearchAnnotation;
//import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class SafeSearchService {
//
//    private final CloudVisionTemplate cloudVisionTemplate;
//
//    public SafeSearchService(CloudVisionTemplate cloudVisionTemplate) {
//        this.cloudVisionTemplate = cloudVisionTemplate;
//    }
//    //    public boolean isImageSafe(byte[] imageBytes) throws IOException {
////        Image img = Image.newBuilder().setContent(ByteString.copyFrom(imageBytes)).build();
////        Feature feature = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
////        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
////                .addFeatures(feature)
////                .setImage(img)
////                .build();
////
////        // Enviamos la petición a la API de Vision
////        AnnotateImageResponse response = imageAnnotatorClient.batchAnnotateImages(
////                List.of(request)
////        ).getResponses(0);
////
////        if (response.hasError()) {
////            System.err.println("Error en Google Vision API: " + response.getError().getMessage());
////            // Por precaución, si la API falla, mejor rechazar la imagen
////            return false;
////        }
////
////        SafeSearchAnnotation safeSearch = response.getSafeSearchAnnotation();
////
////        // Puedes ajustar tu política. Aquí rechazamos si CUALQUIERA
////        // de estas categorías es "POSSIBLE" o más alto.
////        return safeSearch.getAdult() != Likelihood.POSSIBLE &&
////                safeSearch.getAdult() != Likelihood.LIKELY &&
////                safeSearch.getAdult() != Likelihood.VERY_LIKELY &&
////                safeSearch.getViolence() != Likelihood.POSSIBLE &&
////                safeSearch.getViolence() != Likelihood.LIKELY &&
////                safeSearch.getViolence() != Likelihood.VERY_LIKELY &&
////                safeSearch.getRacy() != Likelihood.POSSIBLE &&
////                safeSearch.getRacy() != Likelihood.LIKELY &&
////                safeSearch.getRacy() != Likelihood.VERY_LIKELY;
////    }
//
//    public boolean processImage(byte[] imageBytes) {
//        Resource imageResource = new ByteArrayResource(imageBytes);
//        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
//                imageResource, Feature.Type.LABEL_DETECTION);
//        return isSafe(response.getSafeSearchAnnotation());
//    }
//
//    private boolean isSafe(SafeSearchAnnotation safeSearch) {
//        // Verificamos las 5 categorías principales
//        return !isLikely(safeSearch.getAdult()) &&
//                !isLikely(safeSearch.getViolence()) &&
//                !isLikely(safeSearch.getRacy()) &&
//                !isLikely(safeSearch.getMedical()) &&
//                !isLikely(safeSearch.getSpoof());
//    }
//
//    /**
//     * Define qué nivel de probabilidad consideras "inseguro".
//     * Generalmente, POSSIBLE, LIKELY y VERY_LIKELY se rechazan.
//     */
//    private boolean isLikely(Likelihood likelihood) {
//        return likelihood == Likelihood.POSSIBLE ||
//                likelihood == Likelihood.LIKELY ||
//                likelihood == Likelihood.VERY_LIKELY;
//    }
//}