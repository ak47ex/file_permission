package com.araneus;/**
 * Created by Araneus on 18.10.2016.
 */

import com.araneus.common.Logger;
import com.araneus.controller.screens.FileLoaderScreen;
import com.araneus.controller.screens.MainScreen;
import com.araneus.model.core.CoreManager;
import com.araneus.model.files.PttFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CoreApplication extends Application {

    private static final String LOG_TAG = CoreApplication.class.getSimpleName();

    private Screen currentScreen;

    public static void main(String[] args) {

        CoreManager.initialize();

        launch(args);
    }


    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Permission App");

        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CoreApplication.class.getResource("../../forms/FileLoaderScreen.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            FileLoaderScreen loaderScreen = loader.getController();
            loaderScreen.setCoreApplication(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScreen = Screen.FileLoader;
    }

   /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void openMainScreen(PttFile file) {
        if(currentScreen == Screen.Main) return;
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CoreApplication.class.getResource("../../forms/MainScreen.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            MainScreen mainScreen = loader.getController();
            mainScreen.setCoreApplication(this);
            mainScreen.setOpenedFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum Screen {
        FileLoader, Main;
    }
}
