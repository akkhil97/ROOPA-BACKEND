package roopa.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import roopa.com.entity.Media;
import roopa.com.repository.MediaRepo;

import java.io.IOException;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepo mediaRepo;

    public Media uploadFile(MultipartFile file, String title) throws IOException {
        if (file.getSize() > 30 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 30MB limit");
        }

        Media media = new Media();
        media.setTitle(title);
        media.setFileName(file.getOriginalFilename());
        media.setFileType(file.getContentType());
        media.setFileSize(file.getSize());
        media.setData(file.getBytes());

        return mediaRepo.save(media);
    }

    public Media updateFile(Long id, MultipartFile file, String title) throws IOException {
        Media media = mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        media.setTitle(title);
        media.setFileName(file.getOriginalFilename());
        media.setFileType(file.getContentType());
        media.setFileSize(file.getSize());
        media.setData(file.getBytes());

        return mediaRepo.save(media);
    }

    public Media getFile(Long id) {
        return mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }

    public List<Media> getAllFiles() {
        return mediaRepo.findAll();
    }

    public void deleteFile(Long id) {
        mediaRepo.deleteById(id);
    }
}
