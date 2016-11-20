package com.araneus.model.files;

import com.araneus.common.Logger;
import com.araneus.model.Metadata;
import com.araneus.model.configuration.Configuration;
import com.araneus.model.core.CoreManager;
import com.araneus.model.files.handlers.ITextFileHandler;
import com.araneus.model.files.handlers.PlainTextHandler;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Araneus on 24.10.2016.
 */
public class FileManager {

    private static final String LOG_TAG = FileManager.class.getSimpleName();

    private Metadata metadata;

    public FileManager() {
    }

    public PttFile openFile(File file){
        Logger.write(LOG_TAG, "opening file %s",file.getName());
        if(!file.canRead() || !file.canWrite()){
            Logger.write(LOG_TAG, "File is not readable or writable!");
            return null;
        }

        String[] split = file.getName().split("\\.(?=[^\\.]+$)");

        ITextFileHandler textHandler;
        switch (split[split.length-1]){
            case "ptt": return new PttFile(file);
            default: return null;
        }
    }


    public void openFileWithDesktop(File file){

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
