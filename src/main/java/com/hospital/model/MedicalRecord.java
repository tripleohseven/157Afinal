package com.hospital.model;

public class MedicalRecord {
    private int recordId;
    private int patientId;
    private int doctorId;
    private String diagnosis;
    private String treatment;
    private String notes;
    private String date;

    public MedicalRecord(int recordId, int patientId, int doctorId, String diagnosis, String treatment, String notes, String date) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.date = date;
    }

    // Getters
    public int getRecordId() { return recordId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getNotes() { return notes; }
    public String getDate() { return date; }

    // Setters
    public void setRecordId(int recordId) { this.recordId = recordId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "recordId=" + recordId +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                ", notes='" + notes + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
} 