package com.hospital.controller;

import com.hospital.dao.MedicalRecordDAO;
import com.hospital.model.MedicalRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ManageRecordsController {

    @FXML
    private TableView<MedicalRecord> recordsTable;

    @FXML
    private TableColumn<MedicalRecord, Integer> idColumn;

    @FXML
    private TableColumn<MedicalRecord, Integer> patientIdColumn;

    @FXML
    private TableColumn<MedicalRecord, Integer> doctorIdColumn;

    @FXML
    private TableColumn<MedicalRecord, String> diagnosisColumn;

    @FXML
    private TableColumn<MedicalRecord, String> treatmentColumn;

    @FXML
    private TableColumn<MedicalRecord, String> notesColumn;

    @FXML
    private TableColumn<MedicalRecord, String> dateColumn;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearSearchButton;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private ObservableList<MedicalRecord> fullRecordList;

    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadRecords();

        backButton.setOnAction(e -> goBack());
        searchButton.setOnAction(e -> searchRecords());
        clearSearchButton.setOnAction(e -> loadRecords());
        addButton.setOnAction(e -> openRecordForm(null));
        editButton.setOnAction(e -> editSelectedRecord());
        deleteButton.setOnAction(e -> deleteSelectedRecord());
    }

    private void loadRecords() {
        MedicalRecordDAO dao = new MedicalRecordDAO();

        try {
            List<MedicalRecord> records = dao.selectAll();
            fullRecordList = FXCollections.observableArrayList(records);
            recordsTable.setItems(fullRecordList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load medical records from database.");
        }
    }

    private void searchRecords() {
        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            showAlert("Warning", "Please enter a search term.");
            return;
        }

        List<MedicalRecord> filtered = fullRecordList.stream()
                .filter(record -> String.valueOf(record.getPatientId()).contains(keyword) ||
                        record.getDiagnosis().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        recordsTable.setItems(FXCollections.observableArrayList(filtered));

        if (filtered.isEmpty()) {
            showAlert("Info", "No matching medical records found.");
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
            showAlert("Error", "Could not return to Dashboard.");
        }
    }

    private void openRecordForm(MedicalRecord record) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecordForm.fxml"));
            Parent root = loader.load();

            RecordFormController controller = loader.getController();
            if (record != null) {
                controller.setRecord(record);
            }

            Stage stage = new Stage();
            stage.setTitle(record == null ? "Add Medical Record" : "Edit Medical Record");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadRecords();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not open form.");
        }
    }

    private void editSelectedRecord() {
        MedicalRecord selected = recordsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openRecordForm(selected);
        } else {
            showAlert("Warning", "Please select a record to edit.");
        }
    }

    private void deleteSelectedRecord() {
        MedicalRecord selected = recordsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to delete this medical record?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        MedicalRecordDAO dao = new MedicalRecordDAO();
                        if (dao.delete(selected.getRecordId())) {
                            recordsTable.getItems().remove(selected);
                            fullRecordList.remove(selected);
                            showAlert("Success", "Medical record deleted successfully.");
                        } else {
                            showAlert("Error", "Could not delete record.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Error", "A database error occurred while deleting the record.");
                    }
                }
            });
        } else {
            showAlert("Warning", "Please select a record to delete.");
        }
    }


    private void showAlert(String title, String content) {
        Alert.AlertType type = Alert.AlertType.INFORMATION;

        if (title.equalsIgnoreCase("Error")) {
            type = Alert.AlertType.ERROR;
        } else if (title.equalsIgnoreCase("Warning")) {
            type = Alert.AlertType.WARNING;
        } else if (title.equalsIgnoreCase("Info")) {
            type = Alert.AlertType.INFORMATION;
        }

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
