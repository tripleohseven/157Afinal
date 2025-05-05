package com.hospital.dao;

import com.hospital.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private static final String INSERT_SQL = "INSERT INTO doctors (full_name, specialty, phone, department) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM doctors WHERE doctor_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM doctors";
    private static final String UPDATE_SQL = "UPDATE doctors SET full_name = ?, specialty = ?, phone = ?, department = ? WHERE doctor_id = ?";
    private static final String DELETE_SQL = "DELETE FROM doctors WHERE doctor_id = ?";

    public int insert(Doctor doctor) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, doctor.getFullName());
            pstmt.setString(2, doctor.getSpecialty());
            pstmt.setString(3, doctor.getPhone());
            pstmt.setString(4, doctor.getDepartment());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Creating doctor failed, no ID returned.");
            }
        }
    }

    public Doctor select(int doctorId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            pstmt.setInt(1, doctorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialty"),
                        rs.getString("phone"),
                        rs.getString("department")
                    );
                }
                return null;
            }
        }
    }

    public List<Doctor> selectAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("full_name"),
                    rs.getString("specialty"),
                    rs.getString("phone"),
                    rs.getString("department")
                ));
            }
        }
        return doctors;
    }

    public boolean update(Doctor doctor) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {
            pstmt.setString(1, doctor.getFullName());
            pstmt.setString(2, doctor.getSpecialty());
            pstmt.setString(3, doctor.getPhone());
            pstmt.setString(4, doctor.getDepartment());
            pstmt.setInt(5, doctor.getDoctorId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int doctorId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
            pstmt.setInt(1, doctorId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
