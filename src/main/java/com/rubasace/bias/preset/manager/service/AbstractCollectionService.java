package com.rubasace.bias.preset.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public abstract class AbstractCollectionService<T> {

    protected final ObjectMapper objectMapper;

    public AbstractCollectionService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected T read(final File file) {
        try {
            return objectMapper.readValue(file, getCollectionClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Class<T> getCollectionClass();
}
