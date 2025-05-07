package com.hospital.controller;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class ManageAppointmentsController {

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, Integer> patientColumn;

    @FXML
    private TableColumn<Appointment, Integer> doctorColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private TableColumn<Appointment, String> timeColumn;

    @FXML
    private TableColumn<Appointment, String> reasonColumn;

    @FXML
    private TableColumn<Appointment, String> statusColumn;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentId"));
        patientColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("patientId"));
        doctorColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("doctorId"));
        dateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentTime"));
        reasonColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        loadAppointments();

        backButton.setOnAction(e -> goBack());
        deleteButton.setOnAction(e -> deleteAppointment());
    }

    /**
     * Loads appointments from database
     */
    private void loadAppointments() {
        try {
            List<Appointment> appointments = appointmentDAO.selectAll();
            ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
            appointmentsTable.setItems(observableList);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not load appointments from the database.");
        }
    }

    /**
     * Deletes selected appointment
     */
    private void deleteAppointment() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean deleted = appointmentDAO.delete(selected.getAppointmentId()); // No SQLException thrown

            if (deleted) {
                appointmentsTable.getItems().remove(selected);
                showAlert("Success", "Appointment deleted successfully.");
            } else {
                showAlert("Error", "Could not delete appointment.");
            }

        } else {
            showAlert("Warning", "Please select an appointment to delete.");
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
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not return to dashboard.");
        }
    }

    /**
     * Show alert box
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
