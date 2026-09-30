package com.smartattend.service;

import com.smartattend.model.AttendanceRecord;
import com.smartattend.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private final List<Student> students = new ArrayList<>();
    private final List<AttendanceRecord> records = new ArrayList<>();

    // Replace these with your actual campus coordinates.
    private static final double CAMPUS_LATITUDE = 13.6200;
    private static final double CAMPUS_LONGITUDE = 79.2903;
    private static final double ALLOWED_RADIUS_METRES = 1000;

    public AttendanceService() {
        students.add(new Student("STU001", "Sample Student", "BCA", "A", "2", "WA001"));
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<AttendanceRecord> getRecords() {
        return records;
    }

    public List<AttendanceRecord> getRecordsForStudent(String studentId) {
        List<AttendanceRecord> studentRecords = new ArrayList<>();

        for (AttendanceRecord record : records) {
            if (record.getStudentId().equalsIgnoreCase(studentId)) {
                studentRecords.add(record);
            }
        }

        return studentRecords;
    }

    public boolean addStudent(Student student) {
        if (student == null || student.getStudentId() == null || student.getStudentId().isBlank())
            return false;
        if (students.stream().anyMatch(s -> s.getStudentId().equalsIgnoreCase(student.getStudentId())))
            return false;
        students.add(student);
        return true;
    }

    public Student find(String id) {
        if (id == null)
            return null;
        return students.stream()
                .filter(s -> s.getStudentId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Student authenticateStudent(String studentId, String password) {
        Student student = find(studentId);
        if (student != null && student.getPassword().equals(password))
            return student;
        return null;
    }

    public String markPresentUsingLocation(String studentId, String subject, String classTime,
            double latitude, double longitude) {
        Student student = find(studentId);
        if (student == null)
            return "Student account was not found.";
        if (subject == null || subject.isBlank() || classTime == null || classTime.isBlank()) {
            return "Subject and class time are required.";
        }

        double distance = distanceMetres(CAMPUS_LATITUDE, CAMPUS_LONGITUDE, latitude, longitude);
        if (distance > ALLOWED_RADIUS_METRES) {
            return "Attendance rejected: you are outside the campus area (" + Math.round(distance) + " m away).";
        }

        boolean alreadyMarked = records.stream().anyMatch(r -> r.getStudentId().equalsIgnoreCase(studentId)
                && r.getSubject().equalsIgnoreCase(subject)
                && r.getClassTime().equals(classTime));
        if (alreadyMarked)
            return "Attendance was already marked for this subject and class.";

        records.add(new AttendanceRecord(
                student.getStudentId(), student.getName(), student.getDepartment(), student.getSection(),
                subject, classTime, "Present", "Browser GPS verified | " + Math.round(distance) + " m from campus"));
        return "Attendance marked Present. Location verified successfully.";
    }

    public boolean updateStatus(long recordId, String status) {
        if (!"Present".equals(status) && !"Absent".equals(status))
            return false;
        for (AttendanceRecord record : records) {
            if (record.getId() == recordId) {
                record.setStatus(status);
                return true;
            }
        }
        return false;
    }

    private double distanceMetres(double lat1, double lon1, double lat2, double lon2) {
        double radius = 6371000;
        double p1 = Math.toRadians(lat1);
        double p2 = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(p1) * Math.cos(p2)
                        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        return 2 * radius * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public long present() {
        return records.stream().filter(r -> "Present".equals(r.getStatus())).count();
    }

    public long absent() {
        return records.stream().filter(r -> "Absent".equals(r.getStatus())).count();
    }
}
