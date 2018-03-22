package com.rubasace.bias.preset.manager.app.action;

import eu.lestard.fluxfx.Action;

import java.io.File;

public class PresetsDirectorySelectedAction implements Action {

    private final File presetsDirectory;

    public PresetsDirectorySelectedAction(File presetsDirectory) {
        this.presetsDirectory = presetsDirectory;
    }

    public File getPresetsDirectory() {
        return this.presetsDirectory;
    }

}
