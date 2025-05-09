package com.example.studentframeworkapi.util;

import com.example.studentframeworkapi.model.ConfigJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class JsonConfigReader {

    @Getter
    private static final ConfigJson config;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            config = mapper.readValue(new File("src/main/resources/config.json"), ConfigJson.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON config", e);
        }
    }
}

