package com.araneus.model.core;

import com.araneus.model.authorization.AuthManager;
import com.araneus.model.configuration.Configuration;
import com.araneus.model.files.FileManager;

/**
 * Created by Araneus on 24.10.2016.
 */
public class CoreManager {


    private Configuration configuration;
    private FileManager fileManager;
    private AuthManager authManager;
    //region Singleton

    private static CoreManager INSTANCE;

    public static void initialize(){
        INSTANCE = new CoreManager();
    }

    public static CoreManager getInstance() {
        return INSTANCE;
    }

    //endregion

    private CoreManager(){

        configuration = Configuration.getInstance();
        fileManager = new FileManager();
        authManager = new AuthManager();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }
}
