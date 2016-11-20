package com.araneus.controller.screens;

import com.araneus.common.L10n;
import com.araneus.model.Metadata;
import com.araneus.model.core.CoreManager;
import com.araneus.view.InnerFile;
import com.araneus.model.files.PttFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Araneus on 18.10.2016.
 */
public class MainScreen extends BaseScreen{
    private static final String LOG_TAG = MainScreen.class.getSimpleName();

    @FXML private Button saveFileButton;
    @FXML private Button addFileButton;
    @FXML private Label filePathLabel;
    @FXML private TableView<InnerFile> pttContentTable;
    @FXML private TableColumn<InnerFile, String> fileName;
    @FXML private TableColumn<InnerFile, String> filePermissions;
    @FXML private TableColumn<InnerFile, String> fileAuthor;

    private PttFile pttFile;
    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public MainScreen() {


    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @Override @FXML
    protected void initialize() {
        //region Save file button

        saveFileButton.setText(L10n.get("SAVE_FILE_BUTTON"));
        saveFileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            if(pttFile != null) pttFile.save();
        });

        //endregion

        //region Add file button

        addFileButton.setText(L10n.get("ADD_FILE_BUTTON"));
        addFileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser    fileChooser = new FileChooser();

            Map<String,String> extensions = CoreManager.getInstance().getConfiguration().getOpenExtensions();
            extensions.forEach((description,extension) -> {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description,extension));
            });

            fileChooser.setTitle(L10n.get("ADD_FILE_WINDOW_TITLE"));
            File file = fileChooser.showOpenDialog(getCoreApplication().getPrimaryStage());
            //TODO: сделать проверку на наличие ptt файла
            if (file != null){
                pttFile.insertFile(file);
                refreshFileContent();
            }
        });

        //endregion

        filePermissions.setCellValueFactory(new PropertyValueFactory<>("permissions"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

    }

    public void refreshFileContent() {
        List<String> files = pttFile.getFiles();
        ObservableList<InnerFile> data = FXCollections.observableArrayList(
        files.stream().map( v -> (new InnerFile(v, CoreManager.getInstance().getAuthManager().getUsername(), "")))
                .collect(Collectors.toList()));
        pttContentTable.setItems(data);
    }

    public void setOpenedFile(PttFile openedFile) {
        this.pttFile = openedFile;
        filePathLabel.setText(pttFile.getAbsolutePath());

    }
}
