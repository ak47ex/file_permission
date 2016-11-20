package com.araneus.common;


/**
 * Created by Araneus on 24.10.2016.
 */
public class L10n {

    private static final String LOG_TAG = L10n.class.getSimpleName();

    public static String get(String key){

        Logger.write(LOG_TAG,key);

        return key;
    }
}
