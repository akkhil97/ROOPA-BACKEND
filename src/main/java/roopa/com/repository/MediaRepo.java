package roopa.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roopa.com.entity.Media;

public interface MediaRepo extends JpaRepository<Media, Long> {
}
