//package com.ufro.microservice.location_API.incidence.service;
//
//import com.google.cloud.vision.v1.*;
//import com.google.protobuf.ByteString;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class SafeSearchService {
//
//    // ImageAnnotatorClient se crea automáticamente gracias al starter de Spring
//    private final ImageAnnotatorClient imageAnnotatorClient;
//
//    public SafeSearchService(ImageAnnotatorClient imageAnnotatorClient) {
//        this.imageAnnotatorClient = imageAnnotatorClient;
//    }
//
//    /**
//     * Verifica si una imagen es segura según Google SafeSearch.
//     * @return true si la imagen es segura, false si contiene contenido explícito.
//     */
//    public boolean isImageSafe(byte[] imageBytes) throws IOException {
//        Image img = Image.newBuilder().setContent(ByteString.copyFrom(imageBytes)).build();
//        Feature feature = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
//        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
//                .addFeatures(feature)
//                .setImage(img)
//                .build();
//
//        // Enviamos la petición a la API de Vision
//        AnnotateImageResponse response = imageAnnotatorClient.batchAnnotateImages(
//                List.of(request)
//        ).getResponses(0);
//
//        if (response.hasError()) {
//            System.err.println("Error en Google Vision API: " + response.getError().getMessage());
//            // Por precaución, si la API falla, mejor rechazar la imagen
//            return false;
//        }
//
//        SafeSearchAnnotation safeSearch = response.getSafeSearchAnnotation();
//
//        // Puedes ajustar tu política. Aquí rechazamos si CUALQUIERA
//        // de estas categorías es "POSSIBLE" o más alto.
//        return safeSearch.getAdult() != Likelihood.POSSIBLE &&
//                safeSearch.getAdult() != Likelihood.LIKELY &&
//                safeSearch.getAdult() != Likelihood.VERY_LIKELY &&
//                safeSearch.getViolence() != Likelihood.POSSIBLE &&
//                safeSearch.getViolence() != Likelihood.LIKELY &&
//                safeSearch.getViolence() != Likelihood.VERY_LIKELY &&
//                safeSearch.getRacy() != Likelihood.POSSIBLE &&
//                safeSearch.getRacy() != Likelihood.LIKELY &&
//                safeSearch.getRacy() != Likelihood.VERY_LIKELY;
//    }
//}