# SmartAttend

**SmartAttend** is a Java Spring Boot-based smart attendance management system designed to simplify and improve the attendance process for students, faculty, and administrators.

🚧 **Project Status: Ongoing / Under Development**

## 🎯 Project Objective

The goal of SmartAttend is to create a secure and efficient attendance system that can verify a student's identity and location before recording attendance.

The planned system will combine **fingerprint identification** with **location verification** to reduce manual attendance errors and prevent attendance from being recorded by unauthorized users.

---

## ✨ Features

### 👨‍🎓 Student

* Student login
* View attendance records
* Attendance verification using location
* Planned fingerprint-based student identification

### 👨‍🏫 Faculty

* Faculty login
* Mark and manage student attendance
* View attendance records
* Select department, section, and class details

### 👨‍💼 Admin

* Admin login
* Add and manage students
* View attendance records
* Manage student information

### 📍 Location Verification

* Uses the browser's Geolocation API
* Retrieves the student's current latitude and longitude
* Calculates the distance between the student and the configured campus location
* Attendance can be restricted to a defined campus radius

### 👆 Fingerprint Attendance — Planned

The planned biometric attendance system will use fingerprint verification to:

1. Identify the student using their fingerprint
2. Verify that the fingerprint belongs to a registered student
3. Perform location verification
4. Record the attendance

> **Note:** Fingerprint integration is currently a planned feature and is not yet implemented in the current version.

---

## 🛠️ Technology Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring MVC**

### Frontend

* **HTML**
* **CSS**
* **Thymeleaf**
* JavaScript

### Build Tool

* **Maven**

### Planned Database

* MySQL / PostgreSQL

### Planned Hardware Integration

* Fingerprint biometric scanner

---

## 📂 Project Structure

```text
SmartAttend/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── smartattend/
│       │           ├── controller/
│       │           ├── model/
│       │           └── service/
│       │
│       └── resources/
│           ├── static/
│           └── templates/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🚀 How to Run

### Prerequisites

Make sure you have the following installed:

* Java 25 or compatible JDK
* Maven
* Git

### Clone the Repository

```bash
git clone <https://github.com/chins987/SmartAttend.git>
cd SmartAttend
```

### Run the Application

```bash
mvn spring-boot:run
```

Then open the application in your browser:

```text
http://localhost:8080
```

---

## 📍 Current Attendance Verification

The current version uses browser-based location verification.

The system:

```text
Student
   ↓
Browser requests location
   ↓
Latitude & Longitude obtained
   ↓
Distance from campus calculated
   ↓
Location verified
   ↓
Attendance recorded
```

---

## 👆 Planned Fingerprint + Location Verification

The planned final workflow is:

```text
Student
   ↓
Fingerprint Scan
   ↓
Student Identification
   ↓
Location Verification
   ↓
Attendance Validation
   ↓
Attendance Recorded
```

This is intended to provide an additional layer of verification compared with traditional manual attendance.

---

## ⚠️ Current Limitations

SmartAttend is an ongoing development project. The following features are still under development:

* Fingerprint biometric integration
* Database integration
* Secure production authentication
* Password hashing
* Advanced role-based authorization
* Permanent attendance storage
* Fingerprint hardware communication
* Advanced attendance reports and analytics
* Production deployment

The current development version uses in-memory data, so some data may be lost when the application is restarted.

---

## 🔮 Future Improvements

Planned improvements include:

* 👆 Fingerprint-based student identification
* 📍 GPS/location verification
* 🗄️ MySQL/PostgreSQL database integration
* 🔐 Secure authentication and password hashing
* 👥 Improved role-based access control
* 📊 Attendance reports and analytics
* 📸 Photo verification
* 📱 WhatsApp-based attendance workflow
* ☁️ Cloud/production deployment
* 📈 Improved dashboard and user interface

---

## 🎯 Long-Term Goal

The long-term goal of SmartAttend is to develop a **multi-layer attendance verification system** that combines:

**Biometric Identification + Secure Attendance Records**

This can help reduce manual work, improve attendance accuracy, and provide a more reliable method of recording student attendance.

---

## 📌 Project Status

**Status:** 🚧 Under Development

SmartAttend is currently being developed as a learning and portfolio project. Features, architecture, and implementation details may change as development progresses.
