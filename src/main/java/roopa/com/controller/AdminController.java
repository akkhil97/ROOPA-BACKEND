package roopa.com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roopa.com.entity.Admin;
import roopa.com.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        boolean isValid = adminService.validateAdmin(admin.getUsername(), admin.getPassword());
        return isValid ? "Login successful!" : "Invalid username or password.";
    }

    @PostMapping("/register")
    public String register(@RequestBody Admin admin) {
        Admin saved = adminService.saveAdmin(admin);
        return saved != null ? "Registration successful!" : "Registration failed.";
    }

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        admins.forEach(a -> a.setPassword(null)); // Hide passwords
        return admins;
    }

    @PutMapping("/update")
    public String updateAdmin(@RequestBody Admin updatedAdmin) {
        Admin existing = adminService.getAdminByUsername(updatedAdmin.getUsername());
        if (existing != null) {
            existing.setPassword(updatedAdmin.getPassword());
            adminService.saveAdmin(existing);
            return "Admin updated successfully!";
        } else {
            return "Admin not found.";
        }
    }
}


