package com.rubasace.bias.preset.manager.core.adapters;

import com.rubasace.bias.preset.manager.core.model.Preset;
import com.rubasace.bias.preset.manager.core.model.PresetCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresetsAdapterTest {

    @Mock
    private PresetAdapter presetAdapter;

    @InjectMocks
    private PresetsAdapter presetsAdapter;

    @Test
    public void shouldAdapt() {

        File file1 = mock(File.class);
        File file2 = mock(File.class);

        PresetCollection presetCollection = new PresetCollection();
        Preset preset1 = mock(Preset.class);
        Preset preset2 = mock(Preset.class);

        presetCollection.setPresets(Arrays.asList(preset1, preset2));

        List<File> files = Arrays.asList(file1, file2);

        Preset preset3 = mock(Preset.class);
        Preset preset4 = mock(Preset.class);

        when(presetAdapter.adapt(file1, 2)).thenReturn(preset3);
        when(presetAdapter.adapt(file2, 3)).thenReturn(preset4);

        List<Preset> presets = presetsAdapter.adapt(files, presetCollection);

        assertThat(presets, is(Arrays.asList(preset3, preset4)));
    }
}
