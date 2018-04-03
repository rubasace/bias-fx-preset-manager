package com.rubasace.bias.preset.manager.core.writers;

import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import com.rubasace.bias.preset.manager.core.util.FileMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;

@Component
public class PresetCollectionWriter {

    private final FileMapper fileMapper;

    public PresetCollectionWriter(final FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void create(final File biasDirectory, final String bankFolderName) {

        PresetCollection presetCollection = new PresetCollection();
        presetCollection.setPresets(new ArrayList<>());

        File bankFolder = new File(biasDirectory, bankFolderName);
        bankFolder.mkdir();

        File presetFile = new File(bankFolder, "preset.json");

        this.fileMapper.write(presetFile, presetCollection);

    }
}
