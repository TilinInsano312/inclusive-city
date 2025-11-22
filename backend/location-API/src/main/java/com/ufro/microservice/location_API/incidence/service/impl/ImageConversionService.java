package com.ufro.microservice.location_API.incidence.service.impl;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class ImageConversionService {

    public byte[] compressToJpeg(byte[] originalBytes, float quality) throws IOException {
        //Leer los bytes
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(originalBytes));
        if (image == null) {
            throw new IOException("No se pudo leer la imagen. Formato no soportado.");
        }

        //Prepara el jpg
        ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpegWriteParam = jpegWriter.getDefaultWriteParam();
        jpegWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //Compresion
        jpegWriteParam.setCompressionQuality(quality);

        //Foto a bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            jpegWriter.setOutput(ios);
            jpegWriter.write(null, new IIOImage(image, null, null), jpegWriteParam);
            jpegWriter.dispose();
        }

        return baos.toByteArray();
    }
}