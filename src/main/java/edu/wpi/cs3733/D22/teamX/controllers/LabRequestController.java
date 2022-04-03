package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.LabServiceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LabRequestController implements Initializable {
  @FXML private VBox dropdownCol;
  @FXML private VBox labelCol;
  @FXML private VBox submitCol;
  @FXML private HBox buttonRow;
  @FXML private TableColumn<LabServiceRequest, String> requestID;
  @FXML private TableColumn<LabServiceRequest, String> patientID;
  @FXML private TableColumn<LabServiceRequest, String> assigneeTable;
  @FXML private TableColumn<LabServiceRequest, String> service;
  @FXML private TableColumn<LabServiceRequest, String> status;
  @FXML private TableColumn<LabServiceRequest, String> destination;
  @FXML private TableView<LabServiceRequest> table;
  @FXML private Button ReturnToMain;
  @FXML private Button submitRequest;
  @FXML
  private ChoiceBox<String> selectLab, patientName, assigneeDrop, serviceStatus, selectDestination;
  private LabServiceRequest request;
  private LocationDAO locationDAO;
  private List<Location> locations;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();

    selectDestination.setItems(this.getLocationNames());

    selectLab.getSelectionModel().select("");
    patientName.getSelectionModel().select("");
    assigneeDrop.getSelectionModel().select("");
    serviceStatus.getSelectionModel().select("");
    selectDestination.getSelectionModel().select("");

    // FORMATTING----------------------------------------------------
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    patientName.getItems().addAll("Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5");
    assigneeDrop
        .getItems()
        .addAll("Doctor 1", "Doctor 2", "Doctor 3", "Nurse 1", "Nurse 2", "Nurse 3");
    selectLab
        .getItems()
        .addAll("Blood Work", "MRI", "Urine Sample", "Stool Sample", "Saliva Sample");
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    submitCol.setSpacing(20);
    buttonRow.setSpacing(20);
    dropdownCol.setSpacing(20);
    labelCol.setSpacing(28);
    // TABLE COLUMN PLACING----------------------------------------------------
    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    patientID.setCellValueFactory(new PropertyValueFactory<>("patientFor"));
    assigneeTable.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    service.setCellValueFactory(new PropertyValueFactory<>("service"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
    // table.setItems(labDeliveryList());
  }

  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  @FXML
  public void checkAllBoxes(){
    //submitRequest.setDisable(selectLab);
  }

  // UNCOMMENT WHEN DAO AND DAOIMPL ARE PULLED TO MAIN
  //  private ObservableList<LabServiceRequest> labDeliveryList() {

  //    ObservableList<LabServiceRequest> labList = FXCollections.observableArrayList();
  //    LabServiceRequestDAO allLabs = new LabServiceRequestDAOImpl();
  //    List<LabServiceRequest> inpLabsList =
  //            allLabs.getAllLabServiceRequests();
  //    labList.addAll(inpLabsList);
  //    return labList;
  //  }

  @FXML
  public void submitRequest() {
    request = new LabServiceRequest();
    request.setRequestID(request.makeRequestID());
    request.setPatientFor(patientName.getValue());
    request.setAssignee(assigneeDrop.getValue());
    request.setService(selectLab.getValue());
    request.setStatus(serviceStatus.getValue());
    request.setDestination(locations.get(selectDestination.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    // UNCOMMENT WHEN DAO AND DAOIMPL ARE PULLED TO MAIN
    // LabServiceRequestDAO submit = new LabServiceRequestDAOImpl();
    // submit.addLabServiceRequest(request);
    this.resetFields();
  }

  // private ChoiceBox<String> selectLab, patientName, assigneeDrop, serviceStatus,
  // selectDestination;

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    patientName.setValue("");
    assigneeDrop.setValue("");
    serviceStatus.setValue("");
    selectLab.setValue("");
    selectDestination.setValue("");
  }
}
