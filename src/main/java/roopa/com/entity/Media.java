package roopa.com.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime postedDate;

    @Lob @Column(length = 314572800) // 300MB for 4K photo
    private byte[] photo1;

    @Lob @Column(length = 314572800)
    private byte[] photo2;

    @Lob @Column(length = 314572800)
    private byte[] photo3;

    @Lob @Column(length = 314572800)
    private byte[] photo4;

    @Lob @Column(length = 314572800)
    private byte[] photo5;

    @Lob @Column(length = 314572800)
    private byte[] photo6;

    @Lob @Column(length = 314572800)
    private byte[] photo7;

    @Lob @Column(length = 314572800)
    private byte[] photo8;

    @Lob @Column(length = 314572800)
    private byte[] photo9;

    @Lob @Column(length = 314572800)
    private byte[] photo10;

    @Lob @Column(length = 314572800)
    private byte[] photo11;

    @Lob @Column(length = 314572800)
    private byte[] photo12;

    @Lob @Column(length = 314572800)
    private byte[] photo13;

    @Lob @Column(length = 314572800)
    private byte[] photo14;

    @Lob @Column(length = 314572800)
    private byte[] photo15;

    @Lob @Column(length = 314572800)
    private byte[] photo16;

    @Lob @Column(length = 314572800)
    private byte[] photo17;

    @Lob @Column(length = 314572800)
    private byte[] photo18;

    @Lob @Column(length = 314572800)
    private byte[] photo19;

    @Lob @Column(length = 314572800)
    private byte[] photo20;

    // Getters and setters (generate using your IDE)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }

    public byte[] getPhoto1() { return photo1; }
    public void setPhoto1(byte[] photo1) { this.photo1 = photo1; }

    public byte[] getPhoto2() { return photo2; }
    public void setPhoto2(byte[] photo2) { this.photo2 = photo2; }

    public byte[] getPhoto3() { return photo3; }
    public void setPhoto3(byte[] photo3) { this.photo3 = photo3; }

    public byte[] getPhoto4() { return photo4; }
    public void setPhoto4(byte[] photo4) { this.photo4 = photo4; }

    public byte[] getPhoto5() { return photo5; }
    public void setPhoto5(byte[] photo5) { this.photo5 = photo5; }

    public byte[] getPhoto6() { return photo6; }
    public void setPhoto6(byte[] photo6) { this.photo6 = photo6; }

    public byte[] getPhoto7() { return photo7; }
    public void setPhoto7(byte[] photo7) { this.photo7 = photo7; }

    public byte[] getPhoto8() { return photo8; }
    public void setPhoto8(byte[] photo8) { this.photo8 = photo8; }

    public byte[] getPhoto9() { return photo9; }
    public void setPhoto9(byte[] photo9) { this.photo9 = photo9; }

    public byte[] getPhoto10() { return photo10; }
    public void setPhoto10(byte[] photo10) { this.photo10 = photo10; }

    public byte[] getPhoto11() { return photo11; }
    public void setPhoto11(byte[] photo11) { this.photo11 = photo11; }

    public byte[] getPhoto12() { return photo12; }
    public void setPhoto12(byte[] photo12) { this.photo12 = photo12; }

    public byte[] getPhoto13() { return photo13; }
    public void setPhoto13(byte[] photo13) { this.photo13 = photo13; }

    public byte[] getPhoto14() { return photo14; }
    public void setPhoto14(byte[] photo14) { this.photo14 = photo14; }

    public byte[] getPhoto15() { return photo15; }
    public void setPhoto15(byte[] photo15) { this.photo15 = photo15; }

    public byte[] getPhoto16() { return photo16; }
    public void setPhoto16(byte[] photo16) { this.photo16 = photo16; }

    public byte[] getPhoto17() { return photo17; }
    public void setPhoto17(byte[] photo17) { this.photo17 = photo17; }

    public byte[] getPhoto18() { return photo18; }
    public void setPhoto18(byte[] photo18) { this.photo18 = photo18; }

    public byte[] getPhoto19() { return photo19; }
    public void setPhoto19(byte[] photo19) { this.photo19 = photo19; }

    public byte[] getPhoto20() { return photo20; }
    public void setPhoto20(byte[] photo20) { this.photo20 = photo20; }
}
