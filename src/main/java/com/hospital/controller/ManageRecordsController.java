package com.hospital.controller;

import com.hospital.dao.MedicalRecordDAO;
import com.hospital.model.MedicalRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    }

    private void loadRecords() {
        MedicalRecordDAO dao = new MedicalRecordDAO();

        try {
            ObservableList<MedicalRecord> records = FXCollections.observableArrayList(dao.selectAll());
            recordsTable.setItems(records);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load medical records.");
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
