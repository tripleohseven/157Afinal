package com.hospital.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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
    private void initialize() {

        registerPatientButton.setOnAction(e -> openScreen("/views/RegisterPatient.fxml"));
        viewPatientsButton.setOnAction(e -> openScreen("/views/ViewPatients.fxml"));
        manageAppointmentsButton.setOnAction(e -> openScreen("/views/ManageAppointments.fxml"));
        manageRecordsButton.setOnAction(e -> openScreen("/views/ManageRecords.fxml"));
        viewDoctorsButton.setOnAction(e -> openScreen("/views/ViewDoctors.fxml"));

        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }

    private void openScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerPatientButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
