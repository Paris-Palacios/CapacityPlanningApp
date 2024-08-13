package org.capacity.controllers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import org.capacity.models.*;
import org.capacity.service.UIService;
import org.capacity.testPackage.ResourcePlanningTableViewManager;
import org.capacity.testPackage.ResourcePlanningTableViewModel;
import org.capacity.utilities.Constants;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static org.capacity.utilities.Utils.setCellText;

public class MainController implements Initializable {
    UIService uiService;

    @FXML
    private Button btnApply;

    @FXML
    private ComboBox<CostCenter> cmbCostCenters;

    @FXML
    private ComboBox<Initiative> cmbInitiatives;

    @FXML
    private ComboBox<Office> cmbOffices;

    @FXML
    private TableView<ResourcePlanningTableViewModel> tableViewResourcePlanning;

    @FXML
    private TableColumn<ResourcePlanningTableViewModel, String> tblViewResourcePlanningMonthColumn;

    @FXML
    private TableColumn<ResourcePlanningTableViewModel, String> tblViewResourcePlanningProjectColumn;

    @FXML
    private TableColumn<ResourcePlanningTableViewModel, Float> commitedColumn;

    @FXML
    private TableColumn<ResourcePlanningTableViewModel, Float> plannedColumn;


    @FXML
    private TableView<Resource> tableViewResources;

    @FXML
    private TableColumn<Resource, String> tblViewOfficeColumn = new TableColumn<>("Office");

    @FXML
    private TableColumn<Resource, String> tblViewCostCenterColumn = new TableColumn<>("Cost Center");

    @FXML
    private TableColumn<Resource, String> tblViewResourceColumn = new TableColumn<>("Resource");

    private ObservableList<Resource> resourceList = FXCollections.observableArrayList();
    private FilteredList<Resource> filteredResourcesList;
    private ObservableList<Office> officeList = FXCollections.observableArrayList();
    private ObservableList<CostCenter> costCentersList = FXCollections.observableArrayList();
    private ObservableList<Initiative> initiativesList = FXCollections.observableArrayList();

    @FXML
    private Spinner<Integer> yearSelector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uiService = UIService.getInstance();
        setSpinnerYearSelector();
        populateLists();
        setComboBoxes();
        setFilterComboBoxListeners();
        setTableViewResources();
        setListenersForEnableButtonApply();
    }
    public void setTableViewResources() {
        filteredResourcesList = new FilteredList<>(resourceList, p -> true);
        tableViewResources.setItems(filteredResourcesList);
        tblViewOfficeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffice().getOfficeName()));
        tblViewCostCenterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostCenter().getCostCenterName()));
        tblViewResourceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResourceName()));

    }

    public void setSpinnerYearSelector() {
        var currentYear = Year.now().getValue();
        yearSelector.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(currentYear - 1, currentYear + 5, currentYear ));
    }

    public void setTableViewResourcePlanning() {
        Resource selectedResource = tableViewResources.getSelectionModel().getSelectedItems()
                .stream().findFirst().orElse(null);
        List<ResourcePlanning> resourcePlannings = uiService.getResourcePlanningById(selectedResource.getId());
        var resourcePlanningTableViewManager = new ResourcePlanningTableViewManager(selectedResource, cmbInitiatives.getSelectionModel().getSelectedItem(), Year.of(yearSelector.getValue()), resourcePlannings);
        tableViewResourcePlanning.setItems(FXCollections.observableArrayList(resourcePlanningTableViewManager.getResourcePlanningTableViewModels()));
        tblViewResourcePlanningMonthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonth().getMonthName()));

        commitedColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue()
                .getMonthAndSelectedInitiativeResourcePlanning().getCommited()).asObject());
        commitedColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        commitedColumn.setOnEditCommit(event -> {
            ResourcePlanningTableViewModel resource = event.getRowValue();
            resource.getMonthAndSelectedInitiativeResourcePlanning().setCommited(event.getNewValue());
        });

        plannedColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue()
                .getMonthAndSelectedInitiativeResourcePlanning().getPlanned()).asObject());
        plannedColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        plannedColumn.setOnEditCommit(event -> {
            ResourcePlanningTableViewModel resource = event.getRowValue();
            resource.getMonthAndSelectedInitiativeResourcePlanning().setPlanned(event.getNewValue());
        });
    }

    public void populateLists() {
        resourceList.setAll(uiService.getAllResources());
        officeList.setAll(uiService.getAllOffices());
        costCentersList.setAll(uiService.getAllCostCenters());
        initiativesList.setAll(uiService.getAllInitiatives());
    }

    public void setComboBoxes() {
        cmbOffices.setItems(officeList);
        cmbOffices.setButtonCell(setCellText(Office::getOfficeName));
        cmbOffices.setCellFactory(officeListView -> setCellText(Office::getOfficeName));
        cmbOffices.setValue(officeList.filtered(office -> office
                .getOfficeName().equals(Constants.ALL_OPTION))
                .stream().findFirst().get());

        cmbCostCenters.setItems(costCentersList);
        cmbCostCenters.setButtonCell(setCellText(CostCenter::getCostCenterName));
        cmbCostCenters.setCellFactory(costCenterListView -> setCellText(CostCenter::getCostCenterName));
        cmbCostCenters.setValue(costCentersList.filtered(office -> office
                        .getCostCenterName().equals(Constants.ALL_OPTION))
                .stream().findFirst().get());

        cmbInitiatives.setItems(initiativesList);
        cmbInitiatives.setButtonCell(setCellText(Initiative::getCustomerProjectName));
        cmbInitiatives.setCellFactory(initiativeListView -> setCellText(Initiative::getCustomerProjectName));

    }

    public void setFilterComboBoxListeners() {
        cmbOffices.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewResourceFilter();
        });

        cmbCostCenters.valueProperty().addListener((observable, oldValue, newValue) -> {
            listViewResourceFilter();
        });
    }

    public void listViewResourceFilter() {
        var cmbOfficeSelected = cmbOffices.valueProperty().getValue().getOfficeName();
        var cmbCostCenterSelected = cmbCostCenters.valueProperty().getValue().getCostCenterName();

        Predicate<Resource> officePredicate = resource -> cmbOfficeSelected.equals(Constants.ALL_OPTION) || resource
                .getOffice().getOfficeName().equals(cmbOfficeSelected);
        Predicate<Resource> costCenterPredicate = resource -> cmbCostCenterSelected.equals(Constants.ALL_OPTION) || resource
                .getCostCenter().getCostCenterName().equals(cmbCostCenterSelected);

        filteredResourcesList.setPredicate(resource -> costCenterPredicate.and(officePredicate).test(resource));
    }

    public void setListenersForEnableButtonApply() {
        btnApply.setDisable(true);
        ChangeListener<Object> comboBoxListener = (observable,  oldValue,  newValue) -> {
            boolean allSelected = cmbInitiatives.getValue() != null && yearSelector.getValue() != null && tableViewResources.getSelectionModel().getSelectedItem() != null;
            btnApply.setDisable(!allSelected); // Enable the button if all are selected
        };

        cmbInitiatives.valueProperty().addListener(comboBoxListener);
        yearSelector.valueProperty().addListener(comboBoxListener);
        tableViewResources.getSelectionModel().selectedItemProperty().addListener(comboBoxListener);
    }

    @FXML
    void onClickApply(ActionEvent event) {
        setTableViewResourcePlanning();
        tblViewResourcePlanningProjectColumn.setText(cmbInitiatives.getValue().getCustomerProjectName());
    }

    @FXML
    void onSave(ActionEvent event) {
        List<ResourcePlanningTableViewModel> resourcePlanning = tableViewResourcePlanning.getItems().stream().toList();
        
        System.out.println(tableViewResourcePlanning.getItems().stream().toList());
    }
}
