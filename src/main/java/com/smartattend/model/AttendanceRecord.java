package com.smartattend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "attendanceRecords")
public class AttendanceRecord {

    @Id
    private long id;

    private String studentId;
    private String studentName;
    private String department;
    private String section;
    private String subject;
    private String classTime;
    private String status;
    private String proof;
    private LocalDateTime markedAt;

    // Required by MongoDB
    public AttendanceRecord() {
    }

    public AttendanceRecord(String studentId, String studentName, String department, String section,
            String subject, String classTime, String status, String proof) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.department = department;
        this.section = section;
        this.subject = subject;
        this.classTime = classTime;
        this.status = status;
        this.proof = proof;
        this.markedAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDepartment() {
        return department;
    }

    public String getSection() {
        return section;
    }

    public String getSubject() {
        return subject;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getStatus() {
        return status;
    }

    public String getProof() {
        return proof;
    }

    public String getMarkedAt() {
        if (markedAt == null) {
            return "";
        }

        return markedAt.format(
                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public void setStatus(String status) {
        this.status = status;
    }
}