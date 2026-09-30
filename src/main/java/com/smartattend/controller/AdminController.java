package com.smartattend.controller;

import com.smartattend.model.Student;
import com.smartattend.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AttendanceService service;

    public AdminController(AttendanceService service) { this.service = service; }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    @GetMapping
    public String page(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("students", service.getStudents());
        return "admin";
    }

    @PostMapping("/students")
    public String add(@ModelAttribute Student student, HttpSession session) {
        if (isAdmin(session)) service.addStudent(student);
        return "redirect:/admin";
    }
}
