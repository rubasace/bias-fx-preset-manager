package com.rubasace.bias.preset.manager.core.service;

import com.rubasace.bias.preset.manager.core.adapters.PresetsAdapter;
import com.rubasace.bias.preset.manager.core.model.Preset;
import com.rubasace.bias.preset.manager.core.model.PresetCollection;
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

        Preset originalPreset = getPreset("original");

        originalPresets.add(originalPreset);
        when(presetCollection.getPresets()).thenReturn(originalPresets);

        when(this.fileMapper.read(presetFile, PresetCollection.class)).thenReturn(presetCollection);
        Preset newPreset1 = getPreset("newPreset1");
        Preset newPreset2 = getPreset("newPreset2");

        List<Preset> mockedPresets = Arrays.asList(newPreset1, newPreset2);
        when(this.presetsAdapter.adapt(newPresets, presetCollection)).thenReturn(mockedPresets);

        PresetCollection updatedPresetCollection = this.presetsUpdater.update(presetFile, newPresets);

        assertThat(updatedPresetCollection, sameInstance(presetCollection));
        assertThat(updatedPresetCollection.getPresets(), is(Arrays.asList(originalPreset, newPreset1, newPreset2)));
        verify(this.fileMapper).write(presetFile, updatedPresetCollection);
    }

    @Test
    public void shouldIgnoreDuplicates() {

        File presetFile = new File("presetFile.preset");

        File newPreset1File = new File("preset1.preset");
        File newPreset2File = new File("preset2.Preset");
        List<File> newPresets = Arrays.asList(newPreset1File, newPreset2File, new File("original.PRESET"));
        List<File> filteredNewPresets = Arrays.asList(newPreset1File, newPreset2File);

        PresetCollection presetCollection = mock(PresetCollection.class);
        List<Preset> originalPresets = new ArrayList<>();

        Preset originalPreset = getPreset("original");

        originalPresets.add(originalPreset);
        when(presetCollection.getPresets()).thenReturn(originalPresets);

        when(this.fileMapper.read(presetFile, PresetCollection.class)).thenReturn(presetCollection);
        Preset newPreset1 = getPreset("newPreset1");
        Preset newPreset2 = getPreset("newPreset2");

        List<Preset> mockedPresets = Arrays.asList(newPreset1, newPreset2);
        when(this.presetsAdapter.adapt(filteredNewPresets, presetCollection)).thenReturn(mockedPresets);

        PresetCollection updatedPresetCollection = this.presetsUpdater.update(presetFile, newPresets);

        assertThat(updatedPresetCollection, sameInstance(presetCollection));
        assertThat(updatedPresetCollection.getPresets(), is(Arrays.asList(originalPreset, newPreset1, newPreset2)));
        verify(this.fileMapper).write(presetFile, updatedPresetCollection);
    }

    private Preset getPreset(String name) {
        Preset originalPreset = new Preset();
        originalPreset.setName(name);
        return originalPreset;
    }
}
