package com.araneus.model;

import com.araneus.model.core.CoreManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Araneus on 24.10.2016.
 */

public class Metadata {

    private static final int READ_PERMISSION = 0x0001;
    private static final int WRITE_PERMISSION = 0x0002;

    @JsonProperty private int authorId;
    @JsonProperty private int permissions = READ_PERMISSION & WRITE_PERMISSION;
    @JsonProperty private List<FileData> files;

    public Metadata() {
        this.authorId = CoreManager.getInstance().getAuthManager().getUserId();
        files = new ArrayList<>();
    }

    //region Getters

    @JsonIgnore
    public int getAuthoId() {
        return authorId;
    }

    @JsonIgnore
    public int getPermissions() {
        return permissions;
    }

    //endregion

    //region Setters

    @JsonIgnore
    public void setAuthorId(int id) {
        this.authorId = id;
    }

    @JsonIgnore
    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    //endregion

    @Override
    public String toString(){

        return String.format("Author is %s, permission bits: %s", authorId, Integer.toBinaryString(permissions));
    }

    public List<FileData> getFiles() {
        return files;
    }

    public void removeFile(FileData file) {
        files.remove(file);
    }

    public void addFile(FileData file) {
        files.add(file);
    }
}
