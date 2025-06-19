package roopa.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import roopa.com.entity.Media;
import roopa.com.repository.MediaRepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepo mediaRepo;

    public Media uploadMedia(MultipartFile[] files, String title) throws IOException {
        if (files.length > 20) {
            throw new RuntimeException("You can upload up to 20 photos only.");
        }

        Media media = new Media();
        media.setTitle(title);
        media.setPostedDate(LocalDateTime.now());

        setPhotos(media, files);

        return mediaRepo.save(media);
    }

    public Media updateMedia(Long id, MultipartFile[] files, String title) throws IOException {
        if (files.length > 20) {
            throw new RuntimeException("You can upload up to 20 photos only.");
        }

        Media existingMedia = mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        existingMedia.setTitle(title);
        existingMedia.setPostedDate(LocalDateTime.now());

        setPhotos(existingMedia, files);

        return mediaRepo.save(existingMedia);
    }

    public Media getMedia(Long id) {
        return mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }

    public List<Media> getAllMedia() {
        return mediaRepo.findAll();
    }

    public void deleteMedia(Long id) {
        mediaRepo.deleteById(id);
    }

    // Helper method for photos
    private void setPhotos(Media media, MultipartFile[] files) throws IOException {
        if (files.length >= 1) media.setPhoto1(files[0].getBytes());
        if (files.length >= 2) media.setPhoto2(files[1].getBytes());
        if (files.length >= 3) media.setPhoto3(files[2].getBytes());
        if (files.length >= 4) media.setPhoto4(files[3].getBytes());
        if (files.length >= 5) media.setPhoto5(files[4].getBytes());
        if (files.length >= 6) media.setPhoto6(files[5].getBytes());
        if (files.length >= 7) media.setPhoto7(files[6].getBytes());
        if (files.length >= 8) media.setPhoto8(files[7].getBytes());
        if (files.length >= 9) media.setPhoto9(files[8].getBytes());
        if (files.length >= 10) media.setPhoto10(files[9].getBytes());
        if (files.length >= 11) media.setPhoto11(files[10].getBytes());
        if (files.length >= 12) media.setPhoto12(files[11].getBytes());
        if (files.length >= 13) media.setPhoto13(files[12].getBytes());
        if (files.length >= 14) media.setPhoto14(files[13].getBytes());
        if (files.length >= 15) media.setPhoto15(files[14].getBytes());
        if (files.length >= 16) media.setPhoto16(files[15].getBytes());
        if (files.length >= 17) media.setPhoto17(files[16].getBytes());
        if (files.length >= 18) media.setPhoto18(files[17].getBytes());
        if (files.length >= 19) media.setPhoto19(files[18].getBytes());
        if (files.length >= 20) media.setPhoto20(files[19].getBytes());
    }

    // 🟢 NEW METHOD: Format Posted Date
    public String formatPostedDate(LocalDateTime postedDate) {
        if (postedDate == null) {
            return "Date unknown";
        }

        LocalDate postDate = postedDate.toLocalDate();
        LocalDate today = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(postDate, today);

        if (daysBetween <= 3) {
            return "✨ NEW";
        } else if (daysBetween <= 10) {
            return "Posted " + daysBetween + " day" + (daysBetween > 1 ? "s" : "") + " ago";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return "Posted on " + postDate.format(formatter);
        }
    }
}
