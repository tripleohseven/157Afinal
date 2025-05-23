package com.hospital.model;

public class Doctor {
    private int doctorId;
    private String fullName;
    private String specialty;
    private String phone;
    private String department;

    public Doctor(int doctorId, String fullName, String specialty, String phone, String department) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.phone = phone;
        this.department = department;
    }

    // Constructor (without ID → for inserting new doctor)
    public Doctor(String fullName, String specialty, String phone, String department) {
        this.fullName = fullName;
        this.specialty = specialty;
        this.phone = phone;
        this.department = department;
    }

    // No-args constructor (optional but good to have)
    public Doctor() {
    }

    // Getters
    public int getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
    public String getSpecialty() { return specialty; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }

    // Setters
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDepartment(String department) { this.department = department; }

    // ToString → useful for ComboBox or debug
    @Override
    public String toString() {
        return fullName + " (" + specialty + ")";
    }
}
