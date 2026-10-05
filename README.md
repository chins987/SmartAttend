# SmartAttend

**SmartAttend** is a Java Spring Boot smart attendance management system that aims to record classroom attendance in a secure, practical, and automated way using **fingerprint-based student identification** through the **Windows Biometric Framework (WinBio)**.

🚧 **Status:** Ongoing / Under Development. 
This is a learning, academic, and portfolio project, and the architecture may evolve as fingerprint hardware, Windows biometric capabilities, and requirements are evaluated.

---

## 🎯 Project Objective

SmartAttend is designed for classrooms where students may not be permitted to use smartphones. It aims to:

- Identify students using fingerprints
- Associate each biometric identity with a registered student
- Prevent students from marking attendance for someone else
- Let faculty manage attendance sessions from a classroom computer
- Store attendance records reliably
- Reduce manual attendance work
- Provide attendance history and reports

---

## 👆 Intended Attendance Workflow

```text
Faculty starts attendance session
            ↓
Student places finger on scanner
            ↓
Fingerprint is captured
            ↓
Windows Biometric Framework
            ↓
Fingerprint identity is identified
            ↓
Identity mapped to SmartAttend student
            ↓
Attendance validation
            ↓
Attendance recorded
```

Students do **not** need individual Windows accounts on the classroom computer.

This workflow represents the intended final architecture. Fingerprint identification is currently under research and has not yet been integrated into the Spring Boot application.
---

## 🧑‍💻 Intended Biometric Architecture

The student's application identity is kept separate from the biometric system:

```text
Fingerprint
     ↓
Windows Biometric Framework
     ↓
Biometric Identity / GUID
     ↓
SmartAttend Student ID
     ↓
Student Record
```

SmartAttend uses the biometric identity only to look up the registered student. Raw fingerprint data is not stored inside the Java application.

---

## 🪟 Windows Biometric Framework (WinBio) Prototype

A separate native C++ prototype, based on Microsoft's WinBio **private-pool sample architecture**, is being developed to investigate:

- Fingerprint sensor enumeration
- Private biometric database configuration
- Sensor configuration
- Biometric template enrollment
- Biometric identification
- Mapping biometric identities to application-level student IDs

The repository contains an experimental native C++ component:
```text
WinBioTest/
├── WinBioTest.cpp
├── WinBioAsyncTest.cpp
│
└── PrivatePool/
    ├── BioHelper.h
    ├── Config.cpp
    ├── Display.cpp
    ├── PrivatePoolCommonDefs.h
    ├── PrivatePoolSetup.cpp
    ├── Stdafx.h
    └── Targetver.h
```

**Experimental status:** The native WinBio prototype has been successfully compiled using the Windows SDK and Microsoft C/C++ compiler. Further execution and hardware-level validation are still required. It is **not yet integrated** with the Spring Boot application, and it is a research component, not a production-ready fingerprint implementation. Further testing is needed to confirm that the available fingerprint hardware and Windows configuration can support the intended architecture.

---

## 👥 Planned Features

### 👨‍🎓 Student
- Fingerprint-based identification (no smartphone required)
- View attendance records, percentage, and history

### 👨‍🏫 Faculty
- Faculty login
- Start and manage attendance sessions
- Select department, section, class, and subject
- View students and record attendance
- View attendance records

### 👨‍💼 Administrator
- Admin login
- Add, edit, and manage student records
- Manage faculty information
- View attendance records

---

## 🛠️ Technology Stack

| Area | Technologies |
|------|--------------|
| Backend | Java, Spring Boot, Spring MVC |
| Frontend | HTML, CSS, Thymeleaf, JavaScript |
| Build Tool | Maven |
| Native Biometric Prototype | C++, Windows Biometric Framework (WinBio), Windows SDK, Microsoft C/C++ Compiler |
| Database | Not yet connected (planned). The current version uses in-memory data. |

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
├── WinBioTest/
│   ├── WinBioTest.cpp
│   ├── WinBioAsyncTest.cpp
│   │
│   └── PrivatePool/
│       ├── BioHelper.h
│       ├── Config.cpp
│       ├── Display.cpp
│       ├── PrivatePoolCommonDefs.h
│       ├── PrivatePoolSetup.cpp
│       ├── Stdafx.h
│       └── Targetver.h
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🚀 How to Run

### Prerequisites

- Java 25 or a compatible JDK
- Maven
- Git

For the experimental WinBio component only:

- Windows
- Windows SDK
- Microsoft C/C++ compiler
- Compatible fingerprint hardware

### Run the Spring Boot application

```
bash
git clone https://github.com/chins987/SmartAttend.git
cd SmartAttend
mvn spring-boot:run
```

Then open: [http://localhost:8080](http://localhost:8080)

> The WinBio component in `WinBioTest/` is built and tested separately from the Spring Boot application.

---

## 🔐 Security Considerations

## 🔐 Security Considerations

Fingerprint data is sensitive biometric information.

The intended SmartAttend architecture is designed so that:

- The biometric framework handles fingerprint processing
- The application uses an application-level identifier to associate a biometric identity with a student record
- Raw fingerprint data is not unnecessarily stored in the Java application

These security and privacy decisions will be reviewed further as the biometric integration is developed and before any production deployment.

---

## ⚠️ Current Limitations

- Fingerprint integration is still experimental
- WinBio prototype is not yet integrated with Spring Boot
- Fingerprint hardware compatibility is not yet fully established
- No database integration yet (data is in-memory)
- Secure production authentication and password hashing are not implemented
- Advanced role-based authorization is not implemented
- Permanent attendance storage is not implemented
- Attendance reports and analytics are incomplete
- Not yet deployed for production

---

## 📌 Current Progress

The following work has been completed or explored so far:

- Spring Boot attendance application structure created
- Student and attendance components developed
- Basic web interface developed using Thymeleaf
- Windows fingerprint hardware investigated
- Windows Biometric Framework (WinBio) researched
- Synchronous WinBio experimentation performed
- Asynchronous WinBio experimentation performed
- WinBio private sensor-pool sample studied and compiled
- Native C++ WinBio prototype added to the project
- Fingerprint-based attendance architecture being evaluated

The biometric component is currently separate from the Spring Boot application, and database integration is planned for a later stage.

---

## 🔮 Future Improvements

- 👆 Fingerprint-based student identification
- 🧑‍🏫 Faculty-controlled attendance sessions
- 🗄️ Database integration
- 🔐 Secure authentication and 🔑 password hashing
- 👥 Role-based access control
- 📊 Attendance reports, analytics, and statistics
- 🧑‍🎓 Student attendance history
- 🖥️ Improved classroom interface
- ☁️ Cloud/production deployment

---

## 🗺️ Development Roadmap

```text
Spring Boot Application
        ↓
Student & Attendance Management
        ↓
WinBio / Fingerprint Research
        ↓
WinBio Experiments
        ↓
Private Sensor Pool Prototype
        ↓
Fingerprint Hardware Validation
        ↓
Spring Boot ↔ Native Biometric Integration
        ↓
Database Integration
        ↓
Authentication & Authorization
        ↓
Attendance Reports
        ↓
Classroom Deployment
```

**Current focus:** Developing and validating a fingerprint-based attendance mechanism suitable for classrooms where students cannot use smartphones. The WinBio prototype is maintained separately so biometric integration can be tested independently before being connected to the Java application.

---

## 🎯 Long-Term Goal

A classroom attendance system built on **Fingerprint Identification + Secure Attendance Management**, where faculty manage attendance from a classroom computer while students identify themselves with a fingerprint.
