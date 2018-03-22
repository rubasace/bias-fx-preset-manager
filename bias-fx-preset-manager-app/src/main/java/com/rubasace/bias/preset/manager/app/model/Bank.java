package com.rubasace.bias.preset.manager.app.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bank {

    private final StringProperty folder = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();

    public String getFolder() {
        return this.folder.get();
    }

    public void setFolder(final String folder) {
        this.folder.set(folder);
    }

    public ReadOnlyStringProperty folderProperty() {
        return this.folder;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(final String name) {
        this.name.set(name);
    }

    public ReadOnlyStringProperty nameProperty() {
        return this.name;
    }

}
