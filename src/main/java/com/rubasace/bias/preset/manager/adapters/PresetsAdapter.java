package com.rubasace.bias.preset.manager.adapters;

import com.rubasace.bias.preset.manager.model.Preset;
import com.rubasace.bias.preset.manager.model.PresetCollection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PresetsAdapter {

  private final PresetAdapter presetAdapter;

  public PresetsAdapter(final PresetAdapter presetAdapter) {
    this.presetAdapter = presetAdapter;
  }

  public List<Preset> adapt(final File[] files, final PresetCollection presetCollection) {
    int size = presetCollection.getPresets().size();
    return IntStream.range(0, files.length)
                    .mapToObj(index -> presetAdapter.adapt(files[index], index + size))
                    .collect(Collectors.toList());
  }
}
