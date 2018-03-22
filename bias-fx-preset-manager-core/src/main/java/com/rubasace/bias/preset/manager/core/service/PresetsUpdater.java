package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.adapters.PresetsAdapter;
import com.rubasace.bias.preset.manager.core.model.Preset;
import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import com.rubasace.bias.preset.manager.core.readers.PresetsReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class PresetsUpdater {

    private final FileMapper fileMapper;
    private final PresetsAdapter presetsAdapter;

    PresetsUpdater(final FileMapper fileMapper, final PresetsAdapter presetsAdapter) {
        this.fileMapper = fileMapper;
        this.presetsAdapter = presetsAdapter;
    }

    public PresetCollection update(final File presetFile, List<File> newPresets) {
        PresetCollection presetCollection = this.fileMapper.read(presetFile, PresetCollection.class);
        List<File> filteredNewPresets = filterNewPresets(presetCollection.getPresets(), newPresets);
        List<Preset> presets = this.presetsAdapter.adapt(filteredNewPresets, presetCollection);
        presetCollection.getPresets().addAll(presets);
        this.fileMapper.write(presetFile, presetCollection);
        return presetCollection;
    }

    private List<File> filterNewPresets(List<Preset> presets, List<File> newPresets) {

        Set<String> presetNames = presets.stream()
                                         .map(p -> (p.getName() + PresetsReader.PRESET_EXTENSION).toLowerCase())
                                         .collect(Collectors.toSet());

        return newPresets.stream()
                         .filter(f -> !presetNames.contains(f.getName().toLowerCase()))
                         .collect(Collectors.toList());

    }
}
