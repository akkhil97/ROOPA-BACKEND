package roopa.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roopa.com.entity.Admin;
import roopa.com.repository.AdminRepo;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;

    public boolean validateAdmin(String username, String password) {
        Admin admin = adminRepo.findByUsernameByPassword(username, password);
        return admin != null && admin.getPassword().equals(password);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Admin getAdminByUsername(String username) {
        return adminRepo.findByUsername(username);
    }
}
