package com.smartattend.service;

import com.smartattend.model.AttendanceRecord;
import com.smartattend.model.Student;
import com.smartattend.repository.StudentRepository;
import com.smartattend.repository.AttendanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private final StudentRepository studentRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

    // Replace these with your actual campus coordinates.
    private static final double CAMPUS_LATITUDE = 13.6200;
    private static final double CAMPUS_LONGITUDE = 79.2903;
    private static final double ALLOWED_RADIUS_METRES = 1000;

    public AttendanceService(
            StudentRepository studentRepository,
            AttendanceRecordRepository attendanceRecordRepository) {

        this.studentRepository = studentRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
        // Add sample student only if the database is empty.
        if (studentRepository.count() == 0) {
            studentRepository.save(
                    new Student(
                            "STU001",
                            "Sample Student",
                            "BCA",
                            "A",
                            "2"));
        }
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student find(String id) {
        if (id == null) {
            return null;
        }

        return studentRepository.findById(id).orElse(null);
    }

    public Student authenticateStudent(String studentId, String password) {
        Student student = find(studentId);

        if (student != null && student.getPassword().equals(password)) {
            return student;
        }

        return null;
    }

    public boolean addStudent(Student student) {
        if (student == null ||
                student.getStudentId() == null ||
                student.getStudentId().isBlank()) {
            return false;
        }

        if (studentRepository.existsById(student.getStudentId())) {
            return false;
        }

        studentRepository.save(student);
        return true;
    }

    public List<AttendanceRecord> getRecords() {
        return attendanceRecordRepository.findAll();
    }

    public List<AttendanceRecord> getRecordsForStudent(String studentId) {
        List<AttendanceRecord> studentRecords = new ArrayList<>();

        for (AttendanceRecord record : attendanceRecordRepository.findAll()) {
            if (record.getStudentId().equalsIgnoreCase(studentId)) {
                studentRecords.add(record);
            }
        }

        return studentRecords;
    }

    public String markPresentUsingLocation(
            String studentId,
            String subject,
            String classTime,
            double latitude,
            double longitude) {

        Student student = find(studentId);

        if (student == null) {
            return "Student account was not found.";
        }

        if (subject == null || subject.isBlank()
                || classTime == null || classTime.isBlank()) {
            return "Subject and class time are required.";
        }

        double distance = distanceMetres(
                CAMPUS_LATITUDE,
                CAMPUS_LONGITUDE,
                latitude,
                longitude);

        if (distance > ALLOWED_RADIUS_METRES) {
            return "Attendance rejected: you are outside the campus area ("
                    + Math.round(distance) + " m away).";
        }

        boolean alreadyMarked = attendanceRecordRepository.findAll().stream()
                .anyMatch(r -> r.getStudentId().equalsIgnoreCase(studentId)
                        && r.getSubject().equalsIgnoreCase(subject)
                        && r.getClassTime().equals(classTime));

        if (alreadyMarked) {
            return "Attendance was already marked for this subject and class.";
        }

        attendanceRecordRepository.save(
                new AttendanceRecord(
                        student.getStudentId(),
                        student.getName(),
                        student.getDepartment(),
                        student.getSection(),
                        subject,
                        classTime,
                        "Present",
                        "Browser GPS verified | "
                                + Math.round(distance)
                                + " m from campus"));

        return "Attendance marked Present. Location verified successfully.";
    }

    public boolean updateStatus(long recordId, String status) {

        if (!"Present".equals(status)
                && !"Absent".equals(status)) {
            return false;
        }

        AttendanceRecord record = attendanceRecordRepository.findById(recordId).orElse(null);

        if (record != null) {
            record.setStatus(status);
            attendanceRecordRepository.save(record);
            return true;
        }

        return false;
    }

    private double distanceMetres(
            double lat1,
            double lon1,
            double lat2,
            double lon2) {

        double radius = 6371000;

        double p1 = Math.toRadians(lat1);
        double p2 = Math.toRadians(lat2);

        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(p1)
                        * Math.cos(p2)
                        * Math.sin(deltaLon / 2)
                        * Math.sin(deltaLon / 2);

        return 2 * radius
                * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a));
    }

    public long present() {
        return attendanceRecordRepository.findAll().stream()
                .filter(r -> "Present".equals(r.getStatus()))
                .count();
    }

    public long absent() {
        return attendanceRecordRepository.findAll().stream()
                .filter(r -> "Absent".equals(r.getStatus()))
                .count();
    }
}