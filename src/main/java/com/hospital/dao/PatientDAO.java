package com.hospital.dao;

import com.hospital.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private static final String INSERT_SQL = "INSERT INTO patients (full_name, dob, gender, phone, address) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM patients WHERE patient_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM patients";
    private static final String UPDATE_SQL = "UPDATE patients SET full_name = ?, dob = ?, gender = ?, phone = ?, address = ? WHERE patient_id = ?";
    private static final String DELETE_SQL = "DELETE FROM patients WHERE patient_id = ?";

    // Insert new patient
    public int insert(Patient patient) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, patient.getFullName());
            pstmt.setString(2, patient.getDob());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getPhone());
            pstmt.setString(5, patient.getAddress());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Add patient
    public void addPatient(Patient patient) {
        insert(patient);
    }

    // Select one patient by ID
    public Patient select(int patientId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pstmt.setInt(1, patientId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("patient_id"),
                            rs.getString("full_name"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Select all patients (throws SQLException)
    public List<Patient> selectAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }

        } // No catch → will throw SQLException if error

        return patients;
    }

    // Update patient
    public boolean update(Patient patient) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {

            pstmt.setString(1, patient.getFullName());
            pstmt.setString(2, patient.getDob());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getPhone());
            pstmt.setString(5, patient.getAddress());
            pstmt.setInt(6, patient.getPatientId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete patient
    public boolean delete(int patientId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {

            pstmt.setInt(1, patientId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
