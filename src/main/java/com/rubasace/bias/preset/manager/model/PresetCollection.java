package com.rubasace.bias.preset.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PresetCollection {

    @JsonProperty("LivePresets")
    private List<Preset> presets;

    public List<Preset> getPresets() {
        return presets;
    }

    public void setPresets(final List<Preset> presets) {
        this.presets = presets;
    }
}
