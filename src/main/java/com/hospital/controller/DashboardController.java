package com.hospital.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;


public class DashboardController {

    @FXML
    private Button registerPatientButton;

    @FXML
    private Button viewPatientsButton;

    @FXML
    private Button manageAppointmentsButton;

    @FXML
    private Button manageRecordsButton;

    @FXML
    private Button viewDoctorsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {

        registerPatientButton.setOnAction(e -> openScreen("/views/RegisterPatient.fxml"));
        viewPatientsButton.setOnAction(e -> openScreen("/views/ViewPatients.fxml"));
        manageAppointmentsButton.setOnAction(e -> openScreen("/views/ManageAppointments.fxml"));
        manageRecordsButton.setOnAction(e -> openScreen("/views/ManageRecords.fxml"));
        viewDoctorsButton.setOnAction(e -> openScreen("/views/ViewDoctors.fxml"));

        exitButton.setOnAction(e -> exitApplication());

        if (logoutButton != null) {
            logoutButton.setOnAction(e -> logout());
        }
    }

    private void openScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) registerPatientButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not open the requested screen.\nPlease check the file or try again.");
        }
    }

    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not logout.");
        }
    }

    private void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the application?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
