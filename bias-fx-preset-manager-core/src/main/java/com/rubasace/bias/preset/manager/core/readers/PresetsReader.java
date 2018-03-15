package com.rubasace.bias.preset.manager.core.readers;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PresetsReader {

    public static final String PRESET_EXTENSION = ".preset";

    public List<File> readPresets(final File directory) {
        return Arrays.stream(directory.listFiles())
                     .filter(file -> file.getName().endsWith(PRESET_EXTENSION))
                     .collect(Collectors.toList());
    }
}
