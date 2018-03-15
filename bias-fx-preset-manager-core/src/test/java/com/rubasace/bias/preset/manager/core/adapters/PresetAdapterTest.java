package com.rubasace.bias.preset.manager.core.adapters;

import com.rubasace.bias.preset.manager.core.model.Preset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PresetAdapterTest {

    private final PresetAdapter presetAdapter = new PresetAdapter();

    @Test
    public void shouldAdapt() {

        String presetName = "test file";
        File file = new File(presetName + ".preset");

        int order = 23;
        Preset preset = presetAdapter.adapt(file, order);

        assertThat(preset.getName(), is(presetName));
        assertThat(preset.getOrder(), is(order));
        assertNotNull(preset.getUuid());
        assertThat(preset.isFavorite(), is(false));
    }
}
