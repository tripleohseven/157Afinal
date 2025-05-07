package com.hospital.controller;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AppointmentFormController {

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField doctorIdField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private TextField reasonField;

    @FXML
    private TextField statusField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Appointment editingAppointment;  // if not null → editing mode

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    private void initialize() {

        saveButton.setOnAction(e -> saveAppointment());
        cancelButton.setOnAction(e -> goBack());
    }

    /**
     * Called by ManageAppointmentsController to set data
     */
    public void setAppointment(Appointment appointment) {
        this.editingAppointment = appointment;

        if (appointment != null) {
            // Edit mode → populate fields
            patientIdField.setText(String.valueOf(appointment.getPatientId()));
            doctorIdField.setText(String.valueOf(appointment.getDoctorId()));
            datePicker.setValue(java.time.LocalDate.parse(appointment.getAppointmentDate()));
            timeField.setText(appointment.getAppointmentTime());
            reasonField.setText(appointment.getReason());
            statusField.setText(appointment.getStatus());
        }
    }

    /**
     * Save appointment (Add or Update)
     */
    private void saveAppointment() {

        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int doctorId = Integer.parseInt(doctorIdField.getText());
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
            String time = timeField.getText();
            String reason = reasonField.getText();
            String status = statusField.getText();

            if (date.isEmpty() || time.isEmpty() || reason.isEmpty() || status.isEmpty()) {
                showAlert("Warning", "Please fill in all fields.");
                return;
            }

            if (editingAppointment == null) {
                // New appointment
                Appointment newAppointment = new Appointment(0, patientId, doctorId, date, time, reason, status);
                appointmentDAO.insert(newAppointment);
                showAlert("Success", "Appointment added successfully.");
            } else {
                // Edit existing appointment
                editingAppointment.setPatientId(patientId);
                editingAppointment.setDoctorId(doctorId);
                editingAppointment.setAppointmentDate(date);
                editingAppointment.setAppointmentTime(time);
                editingAppointment.setReason(reason);
                editingAppointment.setStatus(status);

                appointmentDAO.update(editingAppointment);
                showAlert("Success", "Appointment updated successfully.");
            }

            goBack();

        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid input in Patient ID or Doctor ID.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "An error occurred while saving appointment.");
        }
    }

    /**
     * Go back to ManageAppointments screen
     */
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageAppointments.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not return to Manage Appointments.");
        }
    }

    /**
     * Show alert
     */
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
