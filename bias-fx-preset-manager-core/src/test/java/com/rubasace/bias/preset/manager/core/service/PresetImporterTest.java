package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import com.rubasace.bias.preset.manager.core.readers.PresetsReader;
import com.rubasace.bias.preset.manager.core.util.FileManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresetImporterTest {

    @Mock
    private PresetsReader presetsReader;

    @Mock
    private PresetsUpdater presetsUpdater;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private PresetImporter presetImporter;

    @Test
    public void shouldAddAll() {

        File biasDirectory = new File("bias");
        String bankFolderName = "bank1";
        File presetsDirectory = new File("newPresets");

        File bankFolder = new File(biasDirectory, bankFolderName);
        File presetFile = new File(bankFolder, "preset.json");
        List<File> readPresets = Arrays.asList(new File("p1"), new File("p2"));

        when(presetsReader.readPresets(presetsDirectory)).thenReturn(readPresets);
        PresetCollection presetCollection = new PresetCollection();
        when(presetsUpdater.update(presetFile, readPresets)).thenReturn(presetCollection);

        PresetCollection returnedCollection = presetImporter.importAll(biasDirectory, bankFolderName, presetsDirectory);

        assertThat(returnedCollection, sameInstance(presetCollection));
        verify(fileManager).copyFiles(readPresets, bankFolder);
    }
}
