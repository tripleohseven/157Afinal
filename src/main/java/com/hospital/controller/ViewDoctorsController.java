package com.hospital.controller;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ViewDoctorsController {

    @FXML
    private TableView<Doctor> doctorsTable;

    @FXML
    private TableColumn<Doctor, Integer> idColumn;

    @FXML
    private TableColumn<Doctor, String> fullNameColumn;

    @FXML
    private TableColumn<Doctor, String> specialtyColumn;

    @FXML
    private TableColumn<Doctor, String> phoneColumn;

    @FXML
    private TableColumn<Doctor, String> departmentColumn;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearSearchButton;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private ObservableList<Doctor> doctorList;

    @FXML
    private void initialize() {

        // Link columns with Doctor attributes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Load data
        loadDoctors();

        // Button actions
        backButton.setOnAction(e -> goBack());
        searchButton.setOnAction(e -> searchDoctors());
        clearSearchButton.setOnAction(e -> loadDoctors());
    }

    /**
     * Load all doctors from database
     */
    private void loadDoctors() {
        try {
            List<Doctor> doctors = doctorDAO.selectAll();
            doctorList = FXCollections.observableArrayList(doctors);
            doctorsTable.setItems(doctorList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load doctors from database.");
        }
    }

    /**
     * Go back to dashboard
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
            showAlert("Error", "Could not return to Dashboard.");
        }
    }

    /**
     * Search doctors by full name or specialty
     */
    private void searchDoctors() {
        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            showAlert("Warning", "Please enter a search term.");
            return;
        }

        List<Doctor> filtered = doctorList.stream()
                .filter(doc -> doc.getFullName().toLowerCase().contains(keyword) ||
                        doc.getSpecialty().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        doctorsTable.setItems(FXCollections.observableArrayList(filtered));

        if (filtered.isEmpty()) {
            showAlert("Info", "No matching doctors found.");
        }
    }

    // Show Alert Dialog
    private void showAlert(String title, String message) {
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
        alert.setContentText(message);
        alert.showAndWait();
    }
}
