package com.hospital.dao;

import com.hospital.model.MedicalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {

    private static final String INSERT_SQL = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, treatment, notes, date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM medical_records WHERE record_id = ?";
    private static final String SELECT_BY_PATIENT_SQL = "SELECT * FROM medical_records WHERE patient_id = ?";
    private static final String SELECT_BY_DOCTOR_SQL = "SELECT * FROM medical_records WHERE doctor_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM medical_records";
    private static final String UPDATE_SQL = "UPDATE medical_records SET patient_id = ?, doctor_id = ?, diagnosis = ?, treatment = ?, notes = ?, date = ? WHERE record_id = ?";
    private static final String DELETE_SQL = "DELETE FROM medical_records WHERE record_id = ?";

    public int insert(MedicalRecord record) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, record.getPatientId());
            pstmt.setInt(2, record.getDoctorId());
            pstmt.setString(3, record.getDiagnosis());
            pstmt.setString(4, record.getTreatment());
            pstmt.setString(5, record.getNotes());
            pstmt.setString(6, record.getDate());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Creating record failed, no ID obtained.");
            }
        }
    }

    public MedicalRecord select(int recordId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pstmt.setInt(1, recordId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MedicalRecord(
                            rs.getInt("record_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("diagnosis"),
                            rs.getString("treatment"),
                            rs.getString("notes"),
                            rs.getString("date")
                    );
                }
                return null;
            }
        }
    }

    public List<MedicalRecord> selectByPatient(int patientId) throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_PATIENT_SQL)) {

            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    records.add(new MedicalRecord(
                            rs.getInt("record_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("diagnosis"),
                            rs.getString("treatment"),
                            rs.getString("notes"),
                            rs.getString("date")
                    ));
                }
            }
        }

        return records;
    }

    public List<MedicalRecord> selectByDoctor(int doctorId) throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_DOCTOR_SQL)) {

            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    records.add(new MedicalRecord(
                            rs.getInt("record_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("diagnosis"),
                            rs.getString("treatment"),
                            rs.getString("notes"),
                            rs.getString("date")
                    ));
                }
            }
        }

        return records;
    }

    public List<MedicalRecord> selectAll() throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                records.add(new MedicalRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("notes"),
                        rs.getString("date")
                ));
            }
        }

        return records;
    }

    public boolean update(MedicalRecord record) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {

            pstmt.setInt(1, record.getPatientId());
            pstmt.setInt(2, record.getDoctorId());
            pstmt.setString(3, record.getDiagnosis());
            pstmt.setString(4, record.getTreatment());
            pstmt.setString(5, record.getNotes());
            pstmt.setString(6, record.getDate());
            pstmt.setInt(7, record.getRecordId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int recordId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {

            pstmt.setInt(1, recordId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
