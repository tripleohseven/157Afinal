package com.hospital.controller;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ViewPatientsController {

    @FXML
    private TableView<Patient> patientsTable;

    @FXML
    private TableColumn<Patient, Integer> idColumn;

    @FXML
    private TableColumn<Patient, String> fullNameColumn;

    @FXML
    private TableColumn<Patient, String> dobColumn;

    @FXML
    private TableColumn<Patient, String> genderColumn;

    @FXML
    private TableColumn<Patient, String> phoneColumn;

    @FXML
    private TableColumn<Patient, String> addressColumn;

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchField;

    private ObservableList<Patient> fullPatientList;

    @FXML
    private void initialize() {

        // Link columns with Patient attributes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Load data
        loadPatients();

        // Button actions
        backButton.setOnAction(e -> goBack());
        editButton.setOnAction(e -> editSelectedPatient());
        deleteButton.setOnAction(e -> deleteSelectedPatient());

        // Search filter
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPatients(newValue);
        });
    }

    private void loadPatients() {
        PatientDAO dao = new PatientDAO();

        try {
            List<Patient> patients = dao.selectAll();
            fullPatientList = FXCollections.observableArrayList(patients);
            patientsTable.setItems(fullPatientList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not connect to the database or load patients.\nPlease make sure the database server is running.");
        }
    }

    private void filterPatients(String filter) {
        if (filter == null || filter.isEmpty()) {
            patientsTable.setItems(fullPatientList);
            return;
        }

        ObservableList<Patient> filteredList = FXCollections.observableArrayList();
        for (Patient patient : fullPatientList) {
            if (patient.getFullName().toLowerCase().contains(filter.toLowerCase()) ||
                    patient.getPhone().contains(filter)) {
                filteredList.add(patient);
            }
        }

        patientsTable.setItems(filteredList);
    }

    private void editSelectedPatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditPatient.fxml"));
                Parent root = loader.load();

                EditPatientController controller = loader.getController();
                controller.setPatient(selected);

                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Could not open Edit Patient screen.");
            }
        } else {
            showAlert("Warning", "Please select a patient to edit.");
        }
    }

    private void deleteSelectedPatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            PatientDAO dao = new PatientDAO();
            if (dao.delete(selected.getPatientId())) {
                patientsTable.getItems().remove(selected);
                fullPatientList.remove(selected);
                showAlert("Success", "Patient deleted successfully.");
            } else {
                showAlert("Error", "Could not delete patient.");
            }
        } else {
            showAlert("Warning", "Please select a patient to delete.");
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not return to dashboard.");
        }
    }

    private void showAlert(String title, String content) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        if (title.equalsIgnoreCase("Error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("Warning")) {
            type = Alert.AlertType.WARNING;
        }

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
