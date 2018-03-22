package com.rubasace.bias.preset.manager.core.util;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Component
public class FileManager {

    public void copyFiles(final List<File> files, final File destinationFolder) {
        files.forEach(file -> copyFile(file, destinationFolder));
    }

    private void copyFile(final File file, final File destinationFolder) {
        try {
            File destinationFile = new File(destinationFolder, file.getName());
            // TODO revisit (don't do twice)
            if (!destinationFile.exists()) {
                FileCopyUtils.copy(file, destinationFile);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
