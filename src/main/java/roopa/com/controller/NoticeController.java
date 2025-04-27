package roopa.com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import roopa.com.entity.Notice;
import roopa.com.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/api/notices")

public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/post")
    public Notice createNotice(@RequestBody Notice notice) {
        return noticeService.createNotice(notice);
    }

    @GetMapping("/getall")
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{id}")
    public Notice getNoticeById(@PathVariable Long id) {
        return noticeService.getNoticeById(id).orElse(null);
    }

    @PutMapping("/put/{id}")
    public Notice updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        return noticeService.updateNotice(id, notice);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
    }
}

