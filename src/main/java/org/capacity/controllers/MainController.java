package org.capacity.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.capacity.models.Project;
import org.capacity.models.Resource;
import org.capacity.repository.RepositoryDAO;
import org.capacity.service.DatabaseManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    DatabaseManager databaseManager;

    @FXML
    private Button btnAddNew;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Label btnField;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField field;

    @FXML
    private ListView<Project> listView;

    @FXML
    private Label listViewlbl;

    @FXML
    private TextField searchField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseManager = DatabaseManager.getInstance();
        listViewlbl.setText("Project List");
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Project> call(ListView<Project> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Project project, boolean empty) {
                        super.updateItem(project, empty);
                        if (empty || project == null) {
                            setText(null);
                        } else {
                            setText(project.getProjectName());
                        }
                    }
                };
            }
        });
        listView.setItems(FXCollections.observableArrayList(databaseManager.executeQuery(RepositoryDAO::getAllProjects)));
    }
}
