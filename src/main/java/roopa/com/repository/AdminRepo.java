package roopa.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roopa.com.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM admin WHERE username = :username AND password = :password", nativeQuery = true)
    Admin findByUsernameByPassword(@Param("username") String username, @Param("password") String password);

    Admin findByUsername(String username);
}
