package com.rubasace.bias.preset.manager.core.writers;

import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import com.rubasace.bias.preset.manager.core.util.FileMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PresetCollectionWriterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private PresetCollectionWriter presetCollectionWriter;

    @Test
    public void shouldWrite() throws IOException {

        String bankFolderName = "test";
        File biasDirectory = this.temporaryFolder.newFolder("biasDirectory");
        PresetCollection expectedPresetCollection = new PresetCollection();
        expectedPresetCollection.setPresets(new ArrayList<>());

        File expectedBankFolder = new File(biasDirectory, bankFolderName);
        File expectedPresetFile = new File(expectedBankFolder, "preset.json");

        this.presetCollectionWriter.create(biasDirectory, bankFolderName);

        assertTrue(expectedBankFolder.isDirectory());
        verify(this.fileMapper).write(expectedPresetFile, expectedPresetCollection);

    }
}
