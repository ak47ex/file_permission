package com.araneus.model.files.handlers;

import java.io.File;
import java.util.List;

/**
 * Created by Araneus on 24.10.2016.
 */
public interface ITextFileHandler {

    public String toString();
    public List<String> toLines();
    public File getFile();

}
