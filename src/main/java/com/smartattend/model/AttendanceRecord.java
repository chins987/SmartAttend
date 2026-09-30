package com.smartattend.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceRecord {
    private static long nextId = 1;

    private final long id;
    private final String studentId;
    private final String studentName;
    private final String department;
    private final String section;
    private final String subject;
    private final String classTime;
    private String status;
    private final String proof;
    private final LocalDateTime markedAt;

    public AttendanceRecord(String studentId, String studentName, String department, String section,
                            String subject, String classTime, String status, String proof) {
        this.id = nextId++;
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

    public long getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getDepartment() { return department; }
    public String getSection() { return section; }
    public String getSubject() { return subject; }
    public String getClassTime() { return classTime; }
    public String getStatus() { return status; }
    public String getProof() { return proof; }
    public String getMarkedAt() {
        return markedAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }
    public void setStatus(String status) { this.status = status; }
}
