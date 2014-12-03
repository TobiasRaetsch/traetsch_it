package app;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="video_games")

public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    @OneToOne
    @Fetch(FetchMode.JOIN)
    private Image image;


    public VideoGame() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPrefId() {
        return "VG" + id;
    }

    public String getIdPrefix(){
        return "VG";
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image pImage) {
        image = pImage;
    }
}
