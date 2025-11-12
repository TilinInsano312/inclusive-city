package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.PhotoDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceSearchDTO;
import com.ufro.microservice.location_API.spot.service.IPlaceService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

//Controlador para gestionar las operaciones relacionadas con los places (lugares/establecimientos)
@RequestMapping("inclusive/api/v1/place/")
@RestController
public class PlaceController {
    private final IPlaceService placeService;

    public PlaceController(IPlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("{placeId}")
    public ResponseEntity<ApiResponse<PlaceDetailDTO>> getPlaceById(@PathVariable String placeId) {
        return ResponseEntity.ok().body(new ApiResponse<>(placeService.getPlaceDetails(placeId)));
    }
    @PostMapping("search")
    public ResponseEntity<ApiResponse<List<PlaceSearchDTO>>> getPlaceBySearch(@RequestBody String query) {
        return ResponseEntity.ok().body(new ApiResponse<>(placeService.getPlaceBySearch(query)));
    }

    //El photoReference es una id que se arroja en la variable photos de los placesDetail/Search
    @GetMapping(value = "photo/{photoReference}",produces = "image/jpeg")
    public ResponseEntity<byte[]> getPhotoByReference(@PathVariable String photoReference) {
        PhotoDTO photoDTO = placeService.getPlacePhoto(photoReference, 400);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(photoDTO.getContentType()))
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)) // Cachear por 1 día
                .body(photoDTO.getData());
    }
}
