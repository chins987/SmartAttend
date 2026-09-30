package com.smartattend.model;

public class Student {
    private String studentId;
    private String name;
    private String department;
    private String section;
    private String year;
    private String whatsappId;
    private String password;

    public Student() {
        this.password = "student123";
    }

    public Student(String id, String name, String department, String section, String year, String whatsappId) {
        this(id, name, department, section, year, whatsappId, "student123");
    }

    public Student(String id, String name, String department, String section, String year,
                   String whatsappId, String password) {
        this.studentId = id;
        this.name = name;
        this.department = department;
        this.section = section;
        this.year = year;
        this.whatsappId = whatsappId;
        this.password = (password == null || password.isBlank()) ? "student123" : password;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String value) { studentId = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    public String getDepartment() { return department; }
    public void setDepartment(String value) { department = value; }
    public String getSection() { return section; }
    public void setSection(String value) { section = value; }
    public String getYear() { return year; }
    public void setYear(String value) { year = value; }
    public String getWhatsappId() { return whatsappId; }
    public void setWhatsappId(String value) { whatsappId = value; }
    public String getPassword() { return password; }
    public void setPassword(String value) { password = value; }
}
