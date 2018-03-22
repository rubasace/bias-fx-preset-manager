package com.rubasace.bias.preset.manager.app.action;

import eu.lestard.fluxfx.Action;

import java.io.File;

public class BiasDirectorySelectedAction implements Action {

    private final File biasDirectory;

    public BiasDirectorySelectedAction(File biasDirectory) {
        this.biasDirectory = biasDirectory;
    }

    public File getBiasDirectory() {
        return this.biasDirectory;
    }

}
