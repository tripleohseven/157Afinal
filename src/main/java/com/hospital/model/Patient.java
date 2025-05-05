package com.hospital.model;

/**
 * This class represents a Patient entity corresponding to the 'patients' table in the database.
 */
public class Patient {
    private int patientId;
    private String fullName;
    private String dob;
    private String gender;
    private String phone;
    private String address;

    // Constructor for fetching or displaying full patient record
    public Patient(int patientId, String fullName, String dob, String gender, String phone, String address) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    // Constructor for inserting new patients (no ID yet)
    public Patient(String fullName, String dob, String gender, String phone, String address) {
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public int getPatientId() { return patientId; }
    public String getFullName() { return fullName; }
    public String getDob() { return dob; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // Setters
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDob(String dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}