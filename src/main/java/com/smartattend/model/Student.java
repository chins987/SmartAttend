package com.smartattend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {

    @Id
    private String studentId;

    private String name;
    private String department;
    private String section;
    private String year;
    private String password;

    public Student() {
        this.password = "student123";
    }

    public Student(String id, String name, String department, String section, String year) {
        this(id, name, department, section, year, "student123");
    }

    public Student(String id, String name, String department, String section, String year,
            String password) {
        this.studentId = id;
        this.name = name;
        this.department = department;
        this.section = section;
        this.year = year;
        this.password = (password == null || password.isBlank()) ? "student123" : password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String value) {
        studentId = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String value) {
        department = value;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String value) {
        section = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String value) {
        year = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }
}