package com.hospital.controller;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditPatientController {

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
    private Button saveButton;

    @FXML
    private Button backButton;

    // The patient to edit
    private Patient patient;

    @FXML
    private void initialize() {
        saveButton.setOnAction(e -> saveChanges());
        backButton.setOnAction(e -> goBack());
    }

    // This method will be called from ViewPatientsController when opening this screen
    public void setPatient(Patient patient) {
        this.patient = patient;

        // Split full name
        String[] names = patient.getFullName().split(" ", 2);
        String firstName = names.length > 0 ? names[0] : "";
        String lastName = names.length > 1 ? names[1] : "";

        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        dobPicker.setValue(LocalDate.parse(patient.getDob()));
        genderField.setText(patient.getGender());
        phoneField.setText(patient.getPhone());
        addressField.setText(patient.getAddress());
    }

    private void saveChanges() {
        String fullName = firstNameField.getText() + " " + lastNameField.getText();
        String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        // Validate
        if (fullName.isEmpty() || dob.isEmpty() || gender.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showAlert("Warning", "Please fill all fields.");
            return;
        }

        // Update patient object
        patient.setFullName(fullName);
        patient.setDob(dob);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setAddress(address);

        // Update in DB
        PatientDAO dao = new PatientDAO();
        boolean updated = dao.update(patient);

        if (updated) {
            showAlert("Success", "Patient updated successfully!");
            goBack();
        } else {
            showAlert("Error", "Failed to update patient.");
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViewPatients.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not return to patients list.");
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
