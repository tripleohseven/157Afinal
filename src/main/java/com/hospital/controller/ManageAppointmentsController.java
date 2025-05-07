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
import java.util.stream.Collectors;

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

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearSearchButton;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ObservableList<Appointment> appointmentList;

    @FXML
    private void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentId"));
        patientColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("patientId"));
        doctorColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("doctorId"));
        dateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("appointmentTime"));
        reasonColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        // Load appointments from DB
        loadAppointments();

        // Button actions
        backButton.setOnAction(e -> goBack());
        deleteButton.setOnAction(e -> deleteAppointment());
        searchButton.setOnAction(e -> searchAppointments());
        clearSearchButton.setOnAction(e -> loadAppointments());
        addButton.setOnAction(e -> openAppointmentForm(null)); // null means new appointment
        editButton.setOnAction(e -> {
            Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openAppointmentForm(selected);
            } else {
                showAlert("Warning", "Please select an appointment to edit.");
            }
        });
    }

    /**
     * Loads appointments from database
     */
    private void loadAppointments() {
        try {
            List<Appointment> appointments = appointmentDAO.selectAll();
            appointmentList = FXCollections.observableArrayList(appointments);
            appointmentsTable.setItems(appointmentList);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not load appointments from the database.");
        }
    }

    /**
     * Deletes selected appointment with confirmation
     */
    private void deleteAppointment() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to delete this appointment?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean deleted = appointmentDAO.delete(selected.getAppointmentId());
                    if (deleted) {
                        appointmentsTable.getItems().remove(selected);
                        appointmentList.remove(selected);
                        showAlert("Success", "Appointment deleted successfully.");
                    } else {
                        showAlert("Error", "Could not delete appointment.");
                    }
                }
            });
        } else {
            showAlert("Warning", "Please select an appointment to delete.");
        }
    }

    /**
     * Search appointments by Patient ID or Reason
     */
    private void searchAppointments() {
        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            showAlert("Warning", "Please enter a search term.");
            return;
        }

        List<Appointment> filtered = appointmentList.stream()
                .filter(app -> String.valueOf(app.getPatientId()).contains(keyword)
                        || app.getReason().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        appointmentsTable.setItems(FXCollections.observableArrayList(filtered));

        if (filtered.isEmpty()) {
            showAlert("Info", "No matching appointments found.");
        }
    }

    /**
     * Opens Add or Edit form
     */
    private void openAppointmentForm(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AppointmentForm.fxml"));
            Parent root = loader.load();

            AppointmentFormController controller = loader.getController();
            controller.setAppointment(appointment); // Pass existing appointment or null

            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not open Appointment form.");
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
     * Show alert dialog
     */
    private void showAlert(String title, String content) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        if (title.equalsIgnoreCase("Error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("Warning")) {
            type = Alert.AlertType.WARNING;
        } else if (title.equalsIgnoreCase("Info") || title.equalsIgnoreCase("Success")) {
            type = Alert.AlertType.INFORMATION;
        }

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
