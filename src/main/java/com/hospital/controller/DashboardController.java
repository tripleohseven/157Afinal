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
        registerPatientButton.setOnAction(e -> System.out.println("Register New Patient clicked"));
        viewPatientsButton.setOnAction(e -> System.out.println("View/Edit Patients clicked"));
        manageAppointmentsButton.setOnAction(e -> System.out.println("Manage Appointments clicked"));
        manageRecordsButton.setOnAction(e -> System.out.println("Manage Medical Records clicked"));
        viewDoctorsButton.setOnAction(e -> System.out.println("View Doctors clicked"));
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }

    private void openScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) registerPatientButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
