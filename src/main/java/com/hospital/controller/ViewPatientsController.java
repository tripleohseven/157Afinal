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
import java.util.stream.Collectors;

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

    @FXML
    private Button searchButton;

    @FXML
    private Button clearSearchButton;

    private ObservableList<Patient> fullPatientList;

    @FXML
    private void initialize() {

        // Link columns
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
        searchButton.setOnAction(e -> searchPatients());
        clearSearchButton.setOnAction(e -> loadPatients());
    }

    /**
     * Load patients from database
     */
    private void loadPatients() {
        PatientDAO dao = new PatientDAO();

        try {
            List<Patient> patients = dao.selectAll();
            fullPatientList = FXCollections.observableArrayList(patients);
            patientsTable.setItems(fullPatientList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not load patients from database.");
        }
    }

    /**
     * Search patients by full name or phone
     */
    private void searchPatients() {
        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            showAlert("Warning", "Please enter a search term.");
            return;
        }

        List<Patient> filtered = fullPatientList.stream()
                .filter(p -> p.getFullName().toLowerCase().contains(keyword) ||
                        p.getPhone().contains(keyword))
                .collect(Collectors.toList());

        patientsTable.setItems(FXCollections.observableArrayList(filtered));

        if (filtered.isEmpty()) {
            showAlert("Info", "No matching patients found.");
        }
    }

    /**
     * Edit selected patient
     */
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

    /**
     * Delete selected patient (with confirmation)
     */
    private void deleteSelectedPatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to delete this patient?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    PatientDAO dao = new PatientDAO();
                    if (dao.delete(selected.getPatientId())) {
                        patientsTable.getItems().remove(selected);
                        fullPatientList.remove(selected);
                        showAlert("Success", "Patient deleted successfully.");
                    } else {
                        showAlert("Error", "Could not delete patient.");
                    }
                }
            });

        } else {
            showAlert("Warning", "Please select a patient to delete.");
        }
    }

    /**
     * Go back to Dashboard
     */
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

    /**
     * Show alert dialog
     */
    private void showAlert(String title, String content) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        if (title.equalsIgnoreCase("Error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("Warning")) {
            type = Alert.AlertType.WARNING;
        } else if (title.equalsIgnoreCase("Info")) {
            type = Alert.AlertType.INFORMATION;
        }

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
