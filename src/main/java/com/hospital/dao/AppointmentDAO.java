package com.hospital.dao;

import com.hospital.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private static final String INSERT_SQL = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM appointments WHERE appointment_id = ?";
    private static final String SELECT_BY_PATIENT_SQL = "SELECT * FROM appointments WHERE patient_id = ?";
    private static final String SELECT_BY_DOCTOR_SQL = "SELECT * FROM appointments WHERE doctor_id = ?";
    private static final String UPDATE_SQL = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, reason = ?, status = ? WHERE appointment_id = ?";
    private static final String DELETE_SQL = "DELETE FROM appointments WHERE appointment_id = ?";

    public int insert(Appointment appointment) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setString(3, appointment.getAppointmentDate());
            pstmt.setString(4, appointment.getAppointmentTime());
            pstmt.setString(5, appointment.getReason());
            pstmt.setString(6, appointment.getStatus());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Creating appointment failed, no ID obtained.");
            }
        }
    }

    public Appointment select(int appointmentId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("reason"),
                        rs.getString("status")
                    );
                }
                return null;
            }
        }
    }

    public List<Appointment> selectByPatient(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_PATIENT_SQL)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("reason"),
                        rs.getString("status")
                    ));
                }
            }
        }
        return appointments;
    }

    public List<Appointment> selectByDoctor(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_DOCTOR_SQL)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("reason"),
                        rs.getString("status")
                    ));
                }
            }
        }
        return appointments;
    }

    public boolean update(Appointment appointment) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setString(3, appointment.getAppointmentDate());
            pstmt.setString(4, appointment.getAppointmentTime());
            pstmt.setString(5, appointment.getReason());
            pstmt.setString(6, appointment.getStatus());
            pstmt.setInt(7, appointment.getAppointmentId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int appointmentId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
            pstmt.setInt(1, appointmentId);
            return pstmt.executeUpdate() > 0;
        }
    }
} 