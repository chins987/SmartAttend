package com.smartattend.controller;

import com.smartattend.model.Student;
import com.smartattend.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AttendanceController {
    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        addDashboardData(model);
        model.addAttribute("role", session.getAttribute("role"));
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password,
            @RequestParam String role, HttpSession session, Model model) {
        boolean valid = ("ADMIN".equals(role) && "admin".equals(username) && "admin123".equals(password))
                || ("FACULTY".equals(role) && "faculty".equals(username) && "faculty123".equals(password));
        if (valid) {
            session.setAttribute("role", role);
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid login details.");
        return "login";
    }

    @GetMapping("/student/login")
    public String studentLogin() {
        return "student-login";
    }

    @PostMapping("/student/login")
    public String doStudentLogin(@RequestParam String studentId, @RequestParam String password,
            HttpSession session, Model model) {
        Student student = service.authenticateStudent(studentId, password);
        if (student == null) {
            model.addAttribute("error", "Invalid student ID or password.");
            return "student-login";
        }
        session.setAttribute("role", "STUDENT");
        session.setAttribute("studentId", student.getStudentId());
        return "redirect:/student";
    }

    @GetMapping("/student")
    public String studentPage(Model model, HttpSession session) {
        if (!"STUDENT".equals(session.getAttribute("role")))
            return "redirect:/student/login";
        Student student = service.find((String) session.getAttribute("studentId"));
        if (student == null)
            return "redirect:/student/login";
        model.addAttribute("student", student);
        model.addAttribute("records", service.getRecordsForStudent(student.getStudentId()));
        model.addAttribute("message", requestMessage(session));
        return "student";
    }

    @PostMapping("/student/attendance")
    public String markStudentAttendance(@RequestParam String subject, @RequestParam String classTime,
            @RequestParam double latitude, @RequestParam double longitude,
            HttpSession session) {
        if (!"STUDENT".equals(session.getAttribute("role")))
            return "redirect:/student/login";
        String studentId = (String) session.getAttribute("studentId");
        String result = service.markPresentUsingLocation(studentId, subject, classTime, latitude, longitude);
        session.setAttribute("message", result);
        return "redirect:/student";
    }

    @GetMapping("/manage")
    public String manage(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role) && !"FACULTY".equals(role))
            return "redirect:/login";
        addDashboardData(model);
        model.addAttribute("role", role);
        return "manage";
    }

    @PostMapping("/attendance/update")
    public String updateAttendance(@RequestParam long recordId, @RequestParam String status,
            HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role) || "FACULTY".equals(role)) {
            service.updateStatus(recordId, status);
        }
        return "redirect:/manage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    private void addDashboardData(Model model) {
        model.addAttribute("students", service.getStudents());
        model.addAttribute("records", service.getRecords());
        model.addAttribute("presentCount", service.present());
        model.addAttribute("absentCount", service.absent());
    }

    private String requestMessage(HttpSession session) {
        Object message = session.getAttribute("message");
        session.removeAttribute("message");
        return message == null ? "" : message.toString();
    }

    @ModelAttribute("message")
    public String message(@RequestParam(required = false) String message) {
        return message == null ? "" : message;
    }
}
