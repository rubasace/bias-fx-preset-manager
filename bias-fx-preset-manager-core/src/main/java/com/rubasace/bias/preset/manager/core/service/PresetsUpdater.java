package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.adapters.PresetsAdapter;
import com.rubasace.bias.preset.manager.core.model.Preset;
import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class PresetsUpdater {

    private final FileMapper fileMapper;
    private final PresetsAdapter presetsAdapter;

    public PresetsUpdater(final FileMapper fileMapper, final PresetsAdapter presetsAdapter) {
        this.fileMapper = fileMapper;
        this.presetsAdapter = presetsAdapter;
    }

    public PresetCollection update(final File presetFile, List<File> newPresets) {
        PresetCollection presetCollection = fileMapper.read(presetFile, PresetCollection.class);
        List<Preset> presets = presetsAdapter.adapt(newPresets, presetCollection);
        presetCollection.getPresets().addAll(presets);
        fileMapper.write(presetFile, presetCollection);
        return presetCollection;
    }
}
