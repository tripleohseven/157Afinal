package com.hospital.controller;

import com.hospital.dao.MedicalRecordDAO;
import com.hospital.model.MedicalRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RecordFormController {

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField doctorIdField;

    @FXML
    private TextField diagnosisField;

    @FXML
    private TextField treatmentField;

    @FXML
    private TextField notesField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private MedicalRecord record;

    @FXML
    private void initialize() {
        saveButton.setOnAction(e -> saveRecord());
        cancelButton.setOnAction(e -> closeForm());
    }

    public void setRecord(MedicalRecord record) {
        this.record = record;

        if (record != null) {
            patientIdField.setText(String.valueOf(record.getPatientId()));
            doctorIdField.setText(String.valueOf(record.getDoctorId()));
            diagnosisField.setText(record.getDiagnosis());
            treatmentField.setText(record.getTreatment());
            notesField.setText(record.getNotes());
            if (record.getDate() != null && !record.getDate().isEmpty()) {
                datePicker.setValue(java.time.LocalDate.parse(record.getDate()));
            }
        }
    }

    private void saveRecord() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int doctorId = Integer.parseInt(doctorIdField.getText());
            String diagnosis = diagnosisField.getText();
            String treatment = treatmentField.getText();
            String notes = notesField.getText();
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

            if (diagnosis.isEmpty() || treatment.isEmpty() || date.isEmpty()) {
                showAlert("Warning", "Please fill in all required fields including the Date.");
                return;
            }

            MedicalRecordDAO dao = new MedicalRecordDAO();

            if (record == null) {
                // Add new record
                MedicalRecord newRecord = new MedicalRecord(0, patientId, doctorId, diagnosis, treatment, notes, date);
                dao.insert(newRecord);
                showAlert("Success", "Medical record added successfully.");
            } else {
                // Edit existing record
                record.setPatientId(patientId);
                record.setDoctorId(doctorId);
                record.setDiagnosis(diagnosis);
                record.setTreatment(treatment);
                record.setNotes(notes);
                record.setDate(date);

                dao.update(record);
                showAlert("Success", "Medical record updated successfully.");
            }

            closeForm();

        } catch (NumberFormatException e) {
            showAlert("Error", "Patient ID and Doctor ID must be valid numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not save the medical record.");
        }
    }

    private void closeForm() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        if (title.equalsIgnoreCase("Error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("Warning")) {
            type = Alert.AlertType.WARNING;
        }

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
