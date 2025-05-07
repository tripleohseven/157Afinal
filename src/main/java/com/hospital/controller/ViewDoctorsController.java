package com.hospital.controller;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private void initialize() {

        // Link columns with Doctor attributes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Load data
        loadDoctors();

        // Back button action
        backButton.setOnAction(e -> goBack());
    }

    private void loadDoctors() {
        DoctorDAO dao = new DoctorDAO();

        try {
            ObservableList<Doctor> doctorsList = FXCollections.observableArrayList(dao.selectAll());
            doctorsTable.setItems(doctorsList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load doctors from database. Please try again later.");
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
            showAlert("Error", "Could not return to Dashboard.");
        }
    }

    // Show Alert Dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
