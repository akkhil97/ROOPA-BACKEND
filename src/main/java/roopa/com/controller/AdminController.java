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

        // ✅ Check if user exists before sending OTP
        Admin existing = adminService.getAdminByUsername(email);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store OTP
        otpStorage.put(email, otp);

        try {
            emailService.sendOtpEmail(email, otp);
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
    public ResponseEntity<String> changePasswordWithOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        String storedOtp = otpStorage.get(email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        Admin existing = adminService.getAdminByUsername(email);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (existing.getPassword().equals(newPassword)) {
            return ResponseEntity.badRequest().body("New password must be different from the current password");
        }

        // Save new password
        existing.setPassword(newPassword);
        adminService.saveAdmin(existing);

        // Clear OTP
        otpStorage.remove(email);

        // Optionally: Send confirmation
        emailService.sendPasswordResetConfirmationEmail(email);

        return ResponseEntity.ok("Password changed successfully");
    }
}
