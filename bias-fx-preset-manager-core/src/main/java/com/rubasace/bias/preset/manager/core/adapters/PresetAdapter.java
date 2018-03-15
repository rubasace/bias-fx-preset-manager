package com.rubasace.bias.preset.manager.core.adapters;

import com.rubasace.bias.preset.manager.core.model.Preset;
import com.rubasace.bias.preset.manager.core.readers.PresetsReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
public class PresetAdapter {

    public Preset adapt(final File file, final int order) {
        Preset preset = new Preset();
        preset.setOrder(order);
        preset.setFavorite(false);
        String fileName = file.getName();
        preset.setName(fileName.substring(0, fileName.length() - PresetsReader.PRESET_EXTENSION.length()));
        preset.setUuid(UUID.randomUUID().toString());
        return preset;
    }
}
