package roopa.com.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "noticeboard")
public class Notice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime postedDateTime;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getPostedDateTime() { return postedDateTime; }
    public void setPostedDateTime(LocalDateTime postedDateTime) { this.postedDateTime = postedDateTime; }
}
