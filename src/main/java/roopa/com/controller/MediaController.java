package roopa.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import roopa.com.entity.Media;
import roopa.com.service.MediaService;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<Media> uploadMedia(@RequestParam("files") MultipartFile[] files,
                                             @RequestParam("title") String title) {
        try {
            Media media = mediaService.uploadMedia(files, title);
            return ResponseEntity.ok(media);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable Long id,
                                             @RequestParam("files") MultipartFile[] files,
                                             @RequestParam("title") String title) {
        try {
            Media updatedMedia = mediaService.updateMedia(id, files, title);
            return ResponseEntity.ok(updatedMedia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMedia(@PathVariable Long id) {
        Media media = mediaService.getMedia(id);
        return ResponseEntity.ok(media);
    }

    @GetMapping
    public List<Media> getAllMedia() {
        return mediaService.getAllMedia();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}