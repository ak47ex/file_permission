package com.araneus.controller.screens;

import com.araneus.CoreApplication;
import javafx.fxml.FXML;

/**
 * Created by Araneus on 21.11.2016.
 */
abstract class BaseScreen  {

    private CoreApplication coreApplication;

    // Метод вызывается при инициализации JavaFX после конструктора
    @FXML
    abstract protected void initialize();

    public void setCoreApplication(CoreApplication application){
        coreApplication = application;
    }

    public CoreApplication getCoreApplication() {
        return coreApplication;
    }
}
