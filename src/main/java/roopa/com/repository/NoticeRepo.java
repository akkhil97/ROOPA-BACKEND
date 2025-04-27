package roopa.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roopa.com.entity.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Long> {
}
