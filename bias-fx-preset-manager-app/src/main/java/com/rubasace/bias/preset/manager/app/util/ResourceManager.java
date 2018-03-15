package com.rubasace.bias.preset.manager.app.util;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;

@Component
public class ResourceManager {

    private final ClassLoader classLoader;

    public ResourceManager() {
        this.classLoader = this.getClass().getClassLoader();
    }

    public static URL get(String name) {
        return ResourceManager.class.getResource(name);
    }

    public String getAsString(String name) {
        return classLoader.getResource(name).toExternalForm();
    }

    public InputStream getAsStream(String name) {
        return classLoader.getResourceAsStream(name);
    }
}
