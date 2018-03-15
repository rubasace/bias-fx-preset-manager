package com.rubasace.bias.preset.manager.service;

import com.rubasace.bias.preset.manager.adapters.PresetsAdapter;
import com.rubasace.bias.preset.manager.model.Preset;
import com.rubasace.bias.preset.manager.model.PresetCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresetsUpdaterTest {

    @Mock
    private FileMapper fileMapper;

    @Mock
    private PresetsAdapter presetsAdapter;

    @InjectMocks
    private PresetsUpdater presetsUpdater;

    @Test
    public void shouldUpdate() {

        File presetFile = new File("presetFile");
        List<File> newPresets = Arrays.asList(new File("preset1"), new File("preset2"));

        PresetCollection presetCollection = mock(PresetCollection.class);
        List<Preset> originalPresets = new ArrayList<>();
        Preset originalPreset = mock(Preset.class);
        originalPresets.add(originalPreset);
        when(presetCollection.getPresets()).thenReturn(originalPresets);

        when(fileMapper.read(presetFile, PresetCollection.class)).thenReturn(presetCollection);
        Preset newPreset1 = mock(Preset.class);
        Preset newPreset2 = mock(Preset.class);
        List<Preset> mockedPresets = Arrays.asList(newPreset1, newPreset2);
        when(presetsAdapter.adapt(newPresets, presetCollection)).thenReturn(mockedPresets);

        PresetCollection updatedPresetCollection = presetsUpdater.update(presetFile, newPresets);

        assertThat(updatedPresetCollection, sameInstance(presetCollection));
        assertThat(updatedPresetCollection.getPresets(), is(Arrays.asList(originalPreset, newPreset1, newPreset2)));
        verify(fileMapper).write(presetFile, updatedPresetCollection);
    }
}