package com.rubasace.bias.preset.manager.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class FileMapper {

    private final ObjectMapper objectMapper;

    FileMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T read(final File file, final Class<T> clazz) {
        try {
            return this.objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void write(final File file, final Object object) {
        try {
            this.objectMapper.writeValue(file, object);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
