package roopa.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    public ResponseEntity<Media> uploadFile(@RequestParam("file") MultipartFile file,
                                            @RequestParam("title") String title) {
        try {
            return ResponseEntity.ok(mediaService.uploadFile(file, title));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Media> updateFile(@PathVariable Long id,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam("title") String title) {
        try {
            return ResponseEntity.ok(mediaService.updateFile(id, file, title));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Media media = mediaService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(media.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getFileName() + "\"")
                .body(media.getData());
    }

    @GetMapping
    public List<Media> getAllFiles() {
        return mediaService.getAllFiles();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        mediaService.deleteFile(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
