package com.rubasace.bias.preset.manager.service;

import com.rubasace.bias.preset.manager.model.PresetCollection;
import com.rubasace.bias.preset.manager.readers.PresetsReader;
import com.rubasace.bias.preset.manager.util.FileManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PresetImporter {

    private static final String PRESET_JSON_FILE = "preset.json";
    private final PresetsReader presetsReader;
    private final PresetsUpdater presetsUpdater;
    private final FileManager fileManager;

    public PresetImporter(final PresetsReader presetsReader, final PresetsUpdater presetsUpdater, final FileManager fileManager) {
        this.presetsReader = presetsReader;
        this.presetsUpdater = presetsUpdater;
        this.fileManager = fileManager;
    }

    public PresetCollection importAll(final File biasDirectory, final String bankFolderName, final File presetsDirectory) {

        File bankFolder = new File(biasDirectory, bankFolderName);
        File presetFile = new File(bankFolder, PRESET_JSON_FILE);
        List<File> newPresets = presetsReader.readPresets(presetsDirectory);
        PresetCollection presetCollection = presetsUpdater.update(presetFile, newPresets);
        fileManager.copyFiles(newPresets, bankFolder);
        return presetCollection;
    }
}
