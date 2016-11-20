package com.araneus.model.files.handlers;

import com.araneus.common.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Araneus on 24.10.2016.
 */
public class PlainTextHandler implements ITextFileHandler {

    private static final String LOG_TAG = PlainTextHandler.class.getSimpleName();

    private File openedFile;
    private Path filePath;

    public PlainTextHandler(File file){
        Logger.write(LOG_TAG,"Opening file %s with plain text",file.getName());
        openedFile = file;

        filePath = FileSystems.getDefault().getPath(file.getAbsolutePath());
    }

    @Override
    public String toString(){
        String content = "";

        Logger.write(LOG_TAG,"Converting file into string");

        try {
            Files.lines(filePath).forEach(content::concat);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public List<String> toLines() {
        try {
            return Files.lines(filePath).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public File getFile() {
        return openedFile;
    }
}
