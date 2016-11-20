package com.araneus.view;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Araneus on 25.10.2016.
 */
public class InnerFile {

    private final SimpleStringProperty name;
    private final SimpleStringProperty author;
    private final SimpleStringProperty permissions;

    public InnerFile(String name, String author, String permissions) {
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.permissions = new SimpleStringProperty(permissions);

    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public String getPermissions() {
        return permissions.get();
    }

    public SimpleStringProperty permissionsProperty() {
        return permissions;
    }
}