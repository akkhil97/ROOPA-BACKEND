package roopa.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roopa.com.entity.Notice;
import roopa.com.repository.NoticeRepo;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepo noticeRepo;

    public Notice createNotice(Notice notice) {
        return noticeRepo.save(notice);
    }

    public List<Notice> getAllNotices() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);

        return noticeRepo.findAll().stream()
                .filter(notice -> notice.getPostedDateTime() != null
                        && notice.getPostedDateTime().isAfter(cutoffTime))
                .collect(Collectors.toList());
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepo.findById(id);
    }

    public Notice updateNotice(Long id, Notice updatedNotice) {
        return noticeRepo.findById(id).map(notice -> {
            notice.setTitle(updatedNotice.getTitle());
            notice.setDescription(updatedNotice.getDescription());
            notice.setPostedDateTime(updatedNotice.getPostedDateTime());
            return noticeRepo.save(notice);
        }).orElse(null);
    }

    public void deleteNotice(Long id) {
        noticeRepo.deleteById(id);
    }
}
