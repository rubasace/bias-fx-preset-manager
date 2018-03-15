package com.rubasace.bias.preset.manager.core.readers;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PresetsReaderTest {

    private final PresetsReader presetsReader = new PresetsReader();

    @Test
    public void shouldRead() {
        File directory = mock(File.class);
        File file1 = new File("file1.preset");
        File file2 = new File("file2.preset");
        File file3 = new File("file3.nopreset");
        File file4 = new File("file4.preset");
        when(directory.listFiles()).thenReturn(new File[]{file1, file2, file3, file4});

        List<File> files = presetsReader.readPresets(directory);

        assertThat(files, is(Arrays.asList(file1, file2, file4)));
    }
}
