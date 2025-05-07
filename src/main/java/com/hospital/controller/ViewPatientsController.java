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

        // Back button action
        backButton.setOnAction(e -> goBack());

        // Edit button action
        editButton.setOnAction(e -> editSelectedPatient());
    }

    private void loadPatients() {
        PatientDAO dao = new PatientDAO();

        try {
            List<Patient> patients = dao.selectAll();
            ObservableList<Patient> patientsList = FXCollections.observableArrayList(patients);
            patientsTable.setItems(patientsList);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not load appointments from the database.");
        }
    }

    private void editSelectedPatient() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            // For now just show alert with patient info
            showAlert("Edit Patient",
                    "Selected Patient:\n\n" +
                            "ID: " + selectedPatient.getPatientId() + "\n" +
                            "Name: " + selectedPatient.getFullName() + "\n" +
                            "DOB: " + selectedPatient.getDob() + "\n" +
                            "Gender: " + selectedPatient.getGender() + "\n" +
                            "Phone: " + selectedPatient.getPhone() + "\n" +
                            "Address: " + selectedPatient.getAddress() + "\n\n" +
                            "You can now continue to implement Edit screen if needed.");
        } else {
            showAlert("Warning", "Please select a patient to edit.");
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
