package com.araneus.model.configuration;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Araneus on 24.10.2016.
 */
public class Configuration {
    private Map<String,String> extensions;

    //region Singleton

    private static Configuration INSTANCE = new Configuration();;

    public static final Configuration getInstance(){
        return INSTANCE;
    }

    //endregion

    private Configuration(){
        extensions = new HashMap<>();
        extensions.put("Plain text(*.txt)", "*.txt");

    }

    public Map<String,String> getOpenExtensions(){
        return extensions;
    }

    public Pair<String, String> getSaveExtension() {
        return new Pair<>("Protected file(*.ptt)", "*.ptt");
    }

    public String getMetafileName() {
        return "META";
    }
}
