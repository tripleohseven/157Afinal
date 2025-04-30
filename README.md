# Hospital Management System

A Java-based hospital management system that handles medical records and appointments.

## Project Structure

```
src/main/java/com/hospital/
├── dao/
│   ├── AppointmentDAO.java
│   ├── DBConnection.java
│   └── MedicalRecordDAO.java
└── model/
    ├── Appointment.java
    └── MedicalRecord.java
```

## Features

- Medical Record Management
  - Create, read, update, and delete medical records
  - Search records by patient or doctor
  - Track diagnosis, treatment, and notes

- Appointment Management
  - Schedule and manage appointments
  - Track appointment status
  - Search appointments by patient or doctor

## Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Database Setup

1. Create a MySQL database named `hospital_db`
2. Update the database connection details in `DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

## Building the Project

```bash
mvn clean install
```

## Usage

The project provides DAO (Data Access Object) classes for managing medical records and appointments:

- `MedicalRecordDAO`: Handles all medical record operations
- `AppointmentDAO`: Manages appointment scheduling and tracking

Example usage:

```java
// Create a new medical record
MedicalRecord record = new MedicalRecord(0, patientId, doctorId, diagnosis, treatment, notes, date);
MedicalRecordDAO recordDAO = new MedicalRecordDAO();
int recordId = recordDAO.insert(record);

// Schedule an appointment
Appointment appointment = new Appointment(0, patientId, doctorId, date, time, reason, "Scheduled");
AppointmentDAO appointmentDAO = new AppointmentDAO();
int appointmentId = appointmentDAO.insert(appointment);
```

## License

This project is licensed under the MIT License - see the LICENSE file for details. 