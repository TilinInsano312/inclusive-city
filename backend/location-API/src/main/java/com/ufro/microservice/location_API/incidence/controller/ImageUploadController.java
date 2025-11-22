package com.ufro.microservice.location_API.incidence.controller;

//import com.ufro.microservice.location_API.incidence.service.impl.SafeSearchService;
import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.incidence.service.impl.ImageConversionService;
import com.ufro.microservice.location_API.incidence.service.impl.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("inclusive/api/v1/incidence/image")
public class ImageUploadController {

//    private final SafeSearchService safeSearchService;
    private final StorageService storageService;
    private final ImageConversionService imageConversionService;

//    public ImageUploadController(SafeSearchService safeSearchService, StorageService storageService, ImageConversionService imageConversionService) {
//        this.safeSearchService = safeSearchService;
//        this.storageService = storageService;
//        this.imageConversionService = imageConversionService;
//    }


    public ImageUploadController(StorageService storageService, ImageConversionService imageConversionService) {
        this.storageService = storageService;
        this.imageConversionService = imageConversionService;
    }

    @PostMapping("/upload") //to do: refactor
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("El archivo está vacío."));
        }
        try {
            byte[] imageBytes = file.getBytes();

//            if (!safeSearchService.processImage(imageBytes)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ApiResponse <> ("La imagen contiene contenido inapropiado y no puede ser subida."));
//            }

            byte[] compressedImageBytes = imageConversionService.compressToJpeg(imageBytes, 0.8f);
            String finalFileName = UUID.randomUUID().toString() + ".jpg";

            return ResponseEntity.ok(new ApiResponse<>(storageService.uploadFile(finalFileName, compressedImageBytes)) );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo.", e);
        }
    }
}