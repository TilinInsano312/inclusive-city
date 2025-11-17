package com.ufro.microservice.location_API.incidence.controller;

//import com.ufro.microservice.location_API.incidence.service.SafeSearchService;
import com.ufro.microservice.location_API.common.response.ApiResponse;
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
@RequestMapping("inclusive/api/v1/images")
public class ImageUploadController {

//    private final SafeSearchService safeSearchService;
    private final StorageService storageService;

//    public ImageUploadController(SafeSearchService safeSearchService,
//                                 StorageService storageService) {
//        this.safeSearchService = safeSearchService;
//        this.storageService = storageService;
//    }


    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload") //to do: refactor
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("El archivo está vacío."));
        }
        try {
            byte[] imageBytes = file.getBytes();

//            if (!safeSearchService.isImageSafe(imageBytes)) {
//                // Si no es segura, la rechazamos
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("La imagen contiene contenido inapropiado.");
//            }
            String originalFileName = file.getOriginalFilename();
            String fileExtension = (originalFileName != null) ?
                    originalFileName.substring(originalFileName.lastIndexOf(".")) : "";

            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            storageService.uploadFile(uniqueFileName, imageBytes);

            return ResponseEntity.ok(new ApiResponse<>(uniqueFileName) );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo.", e);
        }
    }
}