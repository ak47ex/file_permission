package com.araneus.controller.screens;

import com.araneus.CoreApplication;
import com.araneus.common.L10n;
import com.araneus.common.Logger;
import com.araneus.model.core.CoreManager;
import com.araneus.model.files.PttFile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;

/**
 * Created by Araneus on 20.11.2016.
 */
public class FileLoaderScreen extends BaseScreen{
    @FXML private Button openPttFileButton;
    @FXML private Button createNewPttFileButton;
    private CoreApplication coreApplication;

    public FileLoaderScreen() {
    }

    public void setCoreApplication(CoreApplication coreApplication) {
        this.coreApplication = coreApplication;
    }

    @Override @FXML
    protected void initialize() {
        initializeOpenFileButton();
    }

    private void initializeOpenFileButton() {
        openPttFileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser fileChooser = new FileChooser();

            Pair<String,String> extensions = CoreManager.getInstance().getConfiguration().getSaveExtension();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensions.getKey(), extensions.getValue()));
            fileChooser.setTitle(L10n.get("OPEN_FILE_WINDOW_TITLE"));
            File file = fileChooser.showOpenDialog(coreApplication.getPrimaryStage());
            if (file != null){
                Logger.write(FileLoaderScreen.class.getSimpleName(), String.format("Opened file %s", file.getName()));
                fileOpened(new PttFile(file));
            }
        });

        createNewPttFileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(L10n.get("SAVE_AS_FILE_TITLE"));
            //TODO: extensions
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ptt","*.ptt"));
            File file = fileChooser.showSaveDialog(coreApplication.getPrimaryStage());

            if(file != null){
                Logger.write(FileLoaderScreen.class.getSimpleName(), String.format("Created file %s", file.getName()));
                fileOpened(new PttFile(file));
            }
        });
    }

    private void fileOpened(PttFile file) {
        coreApplication.openMainScreen(file);
    }
}
