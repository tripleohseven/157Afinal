package com.hospital.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
    private void initialize() {
        submitButton.setOnAction(e -> registerPatient());
    }

    private void registerPatient() {
        String fullName = firstNameField.getText() + " " + lastNameField.getText();
        String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        // Validate
        if (fullName.isEmpty() || dob.isEmpty() || gender.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            System.out.println("Please fill all fields.");
            return;
        }

        // Create patient object
        Patient patient = new Patient(fullName, dob, gender, phone, address);

        // Insert into DB
        PatientDAO dao = new PatientDAO();
        dao.addPatient(patient);

        System.out.println("Patient registered successfully!");
    }
}
