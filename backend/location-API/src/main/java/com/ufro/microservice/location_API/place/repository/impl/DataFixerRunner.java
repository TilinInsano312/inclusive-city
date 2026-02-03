package com.ufro.microservice.location_API.place.repository.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.microservice.location_API.place.model.StatData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataFixerRunner implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---- INICIANDO LIMPIEZA DE DATOS ----");

        // 1. Traemos todos los documentos de la colección 'places' como documentos crudos
        List<Document> rawPlaces = mongoTemplate.findAll(Document.class, "places");
        int count = 0;

        for (Document doc : rawPlaces) {
            Object statsDataRaw = doc.get("statsData");

            // Verificamos si 'statsData' existe y es un Mapa/Documento
            if (statsDataRaw instanceof Document) {
                Document statsMap = (Document) statsDataRaw;
                boolean docChanged = false;

                // Recorremos cada clave del mapa (las claves dinámicas)
                for (String key : statsMap.keySet()) {
                    Object value = statsMap.get(key);

                    // AQUI ESTÁ LA CORRECCIÓN:
                    // Si el valor es un String (texto), intentamos convertirlo a Objeto StatData real
                    if (value instanceof String) {
                        try {
                            String jsonString = (String) value;
                            // Convertimos el texto JSON a un objeto Java real
                            StatData realObject = objectMapper.readValue(jsonString, StatData.class);

                            // Reemplazamos el String con el Objeto en el mapa
                            statsMap.put(key, realObject);
                            docChanged = true;
                        } catch (Exception e) {
                            System.err.println("Error parseando JSON para la clave: " + key + " en el documento " + doc.get("_id"));
                        }
                    }
                }

                // Si encontramos y corregimos algo, guardamos el cambio en la BD
                if (docChanged) {
                    doc.put("statsData", statsMap);
                    mongoTemplate.save(doc, "places");
                    count++;
                    System.out.println("Corregido documento ID: " + doc.get("_id"));
                }
            }
        }

        System.out.println("---- LIMPIEZA FINALIZADA: " + count + " documentos corregidos ----");
    }
}