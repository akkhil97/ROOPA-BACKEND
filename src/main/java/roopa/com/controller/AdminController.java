package roopa.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roopa.com.entity.Admin;
import roopa.com.service.AdminService;
import roopa.com.service.EmailService;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmailService emailService;

    // Store OTPs in memory
    private ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    public AdminController(EmailService emailService) {
        this.emailService = emailService;
    }

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

    // === FORGOT PASSWORD FEATURE ===
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store OTP in memory
        otpStorage.put(email, otp);

        // Send OTP email
        try {
            emailService.sendOtpEmail(email, otp);  // ONLY pass email + OTP

            System.out.println("OTP " + otp + " sent to: " + email);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            Admin existing = adminService.getAdminByUsername(email);
            if (existing != null) {
                existing.setPassword(newPassword);
                adminService.saveAdmin(existing);
                otpStorage.remove(email); // Clear OTP after success

                // ✅ send confirmation email
                emailService.sendPasswordResetConfirmationEmail(email);

                return "Password reset successful! Confirmation email sent.";
            } else {
                return "User not found.";
            }
        } else {
            return "Invalid OTP.";
        }
    }



    // === CHANGE PASSWORD BY LOGGED IN USER ===
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        if (email == null || oldPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        Admin existing = adminService.getAdminByUsername(email);
        if (existing != null && existing.getPassword().equals(oldPassword)) {
            existing.setPassword(newPassword);
            adminService.saveAdmin(existing);
            return ResponseEntity.ok("Password changed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid current password");
        }
    }
}
