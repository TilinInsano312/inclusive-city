package com.ufro.microservice.location_API.incidence.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class StorageService {

    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;
    private @Value("${cloudflare.r2.project}")
    String r2Project;

    public StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Sube un archivo a Cloudflare R2.
     * @param fileName El nombre/key del archivo como se guardará en R2.
     * @param fileBytes Los bytes del archivo.
     */
    public String uploadFile(String fileName, byte[] fileBytes) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName) // Este es el "path" completo en el bucket
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(fileBytes));
        return "https://pub-"+r2Project+".r2.dev/" + fileName;
    }

    public ResponseInputStream<GetObjectResponse> downloadFileAsStream(String fileName) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        // Devolvemos el stream para que el controlador lo maneje
        return s3Client.getObject(objectRequest);
    }
}