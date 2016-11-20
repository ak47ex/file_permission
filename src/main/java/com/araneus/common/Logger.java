package com.araneus.common;

/**
 * Created by Araneus on 18.10.2016.
 */
public class Logger {
    public static void write(String place, String text, String... args){
        System.out.println(String.format("%s: %s",place,String.format(text, (Object) args)));
    }
}
