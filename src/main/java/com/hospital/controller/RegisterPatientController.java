package com.hospital.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.hospital.model.Patient;
import com.hospital.dao.PatientDAO;

public class RegisterPatientController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField genderField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        submitButton.setOnAction(e -> registerPatient());
        backButton.setOnAction(e -> goBack());
    }

    private void registerPatient() {
        String fullName = firstNameField.getText() + " " + lastNameField.getText();
        String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        // Validate inputs
        if (fullName.trim().isEmpty() || dob.isEmpty() || gender.trim().isEmpty() ||
                phone.trim().isEmpty() || address.trim().isEmpty()) {

            showAlert("Warning", "Please fill all fields.");
            return;
        }

        try {
            // Create and insert patient
            Patient patient = new Patient(fullName, dob, gender, phone, address);
            PatientDAO dao = new PatientDAO();
            dao.insert(patient);  // <-- Make sure PatientDAO has `insert()` method as used before

            showAlert("Success", "Patient registered successfully.");

            // Go back after success
            goBack();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while registering patient.");
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
