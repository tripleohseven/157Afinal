-- Create the database
CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- -------------------------
-- Table: Patients
-- -------------------------
CREATE TABLE IF NOT EXISTS patients (
                                        patient_id INT AUTO_INCREMENT PRIMARY KEY,
                                        full_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- -------------------------
-- Table: Doctors
-- -------------------------
CREATE TABLE IF NOT EXISTS doctors (
                                       doctor_id INT AUTO_INCREMENT PRIMARY KEY,
                                       full_name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100),
    phone VARCHAR(20),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- -------------------------
-- Table: Medical Records
-- -------------------------
CREATE TABLE IF NOT EXISTS medical_records (
                                               record_id INT AUTO_INCREMENT PRIMARY KEY,
                                               patient_id INT NOT NULL,
                                               doctor_id INT NOT NULL,
                                               diagnosis VARCHAR(255) NOT NULL,
    treatment TEXT,
    notes TEXT,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- -------------------------
-- Table: Appointments
-- -------------------------
CREATE TABLE IF NOT EXISTS appointments (
                                            appointment_id INT AUTO_INCREMENT PRIMARY KEY,
                                            patient_id INT NOT NULL,
                                            doctor_id INT NOT NULL,
                                            appointment_date DATE NOT NULL,
                                            appointment_time TIME NOT NULL,
                                            reason VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- -------------------------
-- Table: Users
-- -------------------------
CREATE TABLE IF NOT EXISTS users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- -------------------------
-- Indexes
-- -------------------------
-- Speeds up lookups by patient or doctor in medical records
/*
CREATE INDEX idx_medical_records_patient_id ON medical_records(patient_id);
CREATE INDEX idx_medical_records_doctor_id ON medical_records(doctor_id);

-- Speeds up appointment searches by patient, doctor, or date
CREATE INDEX idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX idx_appointments_date ON appointments(appointment_date);

-- Useful for searching/filtering patients or doctors by name or specialty
CREATE INDEX idx_patients_full_name ON patients(full_name);
CREATE INDEX idx_doctors_full_name ON doctors(full_name);
CREATE INDEX idx_doctors_specialty ON doctors(specialty);

-- Ensures fast login and enforces username uniqueness
CREATE UNIQUE INDEX idx_users_username ON users(username);
*/

-- Insert our sample data
INSERT INTO patients (full_name, dob, gender, phone, address)
VALUES 
('Jose Garcia', '1990-03-12', 'male', '6691112233', '455 Alum Rock Ave'),
('Maria Martinez', '2000-06-22', 'female', '4081234567', '88 Story Rd'),
('David Nguyen', '1992-09-10', 'male', '6692223344', '1900 King Rd'),
('Jessica Kim', '2001-01-18', 'female', '4089876543', '67 Blossom Hill Rd'),
('Michael Singh', '1979-11-30', 'male', '6693334455', '76 Curtner Ave'),
('Emily Chen', '1995-07-04', 'female', '4084567890', '2557 Capitol Expy'),
('Daniel Patel', '1982-04-27', 'male', '4081112233', '344 E San Fernando St'),
('Ashley Johnson', '1999-02-17', 'female', '6694445566', '1230 S King Rd'),
('Christopher Tran', '1968-10-29', 'male', '4089998888', '789 Tully Rd'),
('Stephanie Lee', '1993-08-14', 'female', '6695556677', '2030 E Capitol Expy'),
('Brian Rodriguez', '1980-12-09', 'male', '4082223344', '321 S 10th St'),
('Sofia Nguyen', '1996-05-03', 'female', '6696667788', '2100 Monterey Rd'),
('Andrew Morales', '1984-03-21', 'male', '4083334455', '1981 N First St'),
('KC Hernandez', '2003-09-26', 'female', '4080001101', '123 Mckee Rd'),
('Olivia Ramirez', '1991-11-13', 'female', '6697778899', '500 Julian St'),
('Anthony Park', '1989-06-25', 'male', '4084445566', '2231 White Rd');

INSERT INTO doctors (full_name, specialty, phone, department)
VALUES
('Jennifer Nguyen', 'Cardiology', '4081011122', 'Cardiology'),
('Michael Patel', 'Internal Medicine', '6691213141', 'General Medicine'),
('Samantha Garcia', 'Pediatrics', '4081314151', 'Pediatrics'),
('David Kim', 'Orthopedics', '4081415161', 'Orthopedics'),
('Angela Hernandez', 'Neurology', '6691516171', 'Neurology'),
('Kevin Tran', 'Dermatology', '4081617181', 'Dermatology'),
('Maria Singh', 'Psychiatry', '6691718191', 'Mental Health'),
('Christopher Lee', 'Ophthalmology', '4081819201', 'Ophthalmology'),
('Emily Rodriguez', 'Obstetrics & Gynecology', '6691920211', 'OB/GYN'),
('Daniel Martinez', 'ENT', '4082021221', 'Otolaryngology'),
('Ashley Park', 'Oncology', '6692122231', 'Oncology'),
('Jose Chen', 'Emergency Medicine', '4082223241', 'ER'),
('Stephanie Morales', 'Endocrinology', '6692324251', 'Endocrinology'),
('Brian Ramirez', 'Gastroenterology', '4082425261', 'GI'),
('Jessica Johnson', 'Urology', '6692526271', 'Urology');

INSERT INTO users (username, password, role)
VALUES
-- Doctors
('jennguyen431', 'pass123', 'doctor'),
('micpatel812', 'pass123', 'doctor'),
('samgarcia759', 'pass123', 'doctor'),
('davkim204', 'pass123', 'doctor'),
('anhernandez387', 'pass123', 'doctor'),
('kevtran991', 'pass123', 'doctor'),
('marsingh643', 'pass123', 'doctor'),
('chlee732', 'pass123', 'doctor'),
('emrodriguez587', 'pass123', 'doctor'),
('danmartinez946', 'pass123', 'doctor'),
('ashpark325', 'pass123', 'doctor'),
('joschen808', 'pass123', 'doctor'),
('stemorales119', 'pass123', 'doctor'),
('braramirez264', 'pass123', 'doctor'),
('jesjohnson310', 'pass123', 'doctor'),

-- Admins
('admjames101', 'pass123', 'admin'),
('admlopez202', 'pass123', 'admin'),
('admclark303', 'pass123', 'admin'),
('admnguyen404', 'pass123', 'admin'),
('admkim505', 'pass123', 'admin');

INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, reason, status)
VALUES
(1, 1, '2025-05-06', '09:00:00', 'Annual check-up', 'Scheduled'),
(2, 2, '2025-05-07', '10:30:00', 'Follow-up', 'Scheduled'),
(3, 3, '2025-02-08', '14:00:00', 'Child fever', 'Completed'),
(4, 4, '2025-03-09', '11:15:00', 'Knee pain', 'Scheduled'),
(5, 5, '2025-03-10', '13:00:00', 'Headaches', 'Cancelled'),
(6, 6, '2025-01-11', '15:30:00', 'Rash consultation', 'Scheduled'),
(7, 7, '2025-03-12', '08:45:00', 'Anxiety symptoms', 'Scheduled'),
(8, 8, '2025-04-13', '10:00:00', 'Eye irritation', 'Scheduled'),
(9, 9, '2025-01-14', '09:30:00', 'Pregnancy consult', 'Scheduled'),
(10, 10, '2025-02-15', '13:30:00', 'Ear infection', 'Scheduled'),
(11, 11, '2025-02-16', '16:00:00', 'Cancer screening', 'Completed'),
(12, 12, '2025-01-17', '12:15:00', 'Chest pain', 'Completed'),
(13, 13, '2025-04-18', '14:30:00', 'Thyroid follow-up', 'Scheduled'),
(14, 4, '2025-01-19', '11:45:00', 'Hip pain', 'Cancelled'),
(15, 15, '2025-02-20', '10:20:00', 'UTI symptoms', 'Scheduled'),
(16, 1, '2025-03-21', '09:10:00', 'New patient intake', 'Scheduled'),
(2, 3, '2025-04-22', '14:10:00', 'Child check-up', 'Scheduled'),
(4, 5, '2025-03-23', '15:00:00', 'Seizure follow-up', 'Completed'),
(6, 7, '2025-02-24', '09:40:00', 'Depression consult', 'Scheduled'),
(8, 9, '2025-01-25', '13:45:00', 'Pelvic pain', 'Scheduled');

INSERT INTO medical_records (patient_id, doctor_id, diagnosis, treatment, notes, date)
VALUES
(1, 1, 'Hypertension (HTN)', 'Prescribed ACE inhibitors and advised lifestyle changes.', 'Monitor BP weekly.', '2025-05-06'),
(2, 2, 'Type 2 Diabetes (T2DM)', 'Metformin 500mg twice daily, advised dietary changes.', 'Scheduled follow-up in 3 months.', '2025-05-07'),
(3, 3, 'Viral Fever', 'Administered acetaminophen and fluids.', 'Fever reduced in 48 hours.', '2025-02-08'),
(4, 4, 'Patellofemoral Pain Syndrome', 'Physical therapy and NSAIDs prescribed.', 'Knee brace recommended.', '2025-03-09'),
(5, 5, 'Migraine', 'Prescribed triptan and advised sleep hygiene.', 'Patient reported improvement.', '2025-03-10'),
(6, 6, 'Contact Dermatitis', 'Topical corticosteroids and moisturizers given.', 'Patient to avoid triggers.', '2025-01-11'),
(7, 7, 'Generalized Anxiety Disorder', 'Initiated SSRIs and recommended counseling.', 'Patient agreed to CBT.', '2025-03-12'),
(8, 8, 'Conjunctivitis', 'Antibiotic eye drops prescribed.', 'Symptoms improved in 3 days.', '2025-04-13'),
(9, 9, 'Routine Prenatal Check-up', 'Ultrasound conducted, normal fetal development.', 'Next visit in 4 weeks.', '2025-01-14'),
(10, 10, 'Otitis Media', 'Oral antibiotics for 7 days.', 'Pain resolved.', '2025-02-15'),
(11, 11, 'Breast Cancer Screening', 'Mammogram performed, results pending.', 'Follow-up scheduled.', '2025-02-16'),
(12, 12, 'Non-STEMI', 'Aspirin, statin, and ECG monitoring.', 'Referred to cardiology.', '2025-01-17'),
(13, 13, 'Hypothyroidism', 'Levothyroxine 50mcg daily.', 'Labs to be rechecked in 6 weeks.', '2025-04-18'),
(14, 4, 'Hip osteoarthritis', 'Prescribed NSAIDs and physical therapy.', 'MRI ordered for further evaluation.', '2025-01-19'),
(15, 15, 'Urinary Tract Infection (UTI)', 'Prescribed nitrofurantoin for 5 days.', 'Increased fluid intake advised.', '2025-02-20'),
(16, 1, 'Hypertension', 'Continue ACE inhibitors, reduce sodium intake.', 'BP reading still elevated.', '2025-03-21'),
(2, 3, 'Routine Pediatric Check-up', 'Vaccinations up to date, no issues.', 'Growth normal.', '2025-04-22'),
(4, 5, 'Post-Seizure Review', 'Neurological exam normal, no meds adjusted.', 'EEG scheduled.', '2025-03-23'),
(6, 7, 'Depressive Disorder', 'Therapy ongoing, mood improved.', 'Will reassess meds next visit.', '2025-02-24'),
(8, 9, 'Dysmenorrhea', 'Prescribed NSAIDs and OCP.', 'Patient counseled on symptom tracking.', '2025-01-25');

