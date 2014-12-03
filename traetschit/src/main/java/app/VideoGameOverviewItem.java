package app;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="videogameoverviewitem")
public class VideoGameOverviewItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Genre genre;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Developer developer;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private VideoGame videoGame;

    @NotNull
    @Column(columnDefinition = "text")
    private String description;


    public VideoGameOverviewItem(Genre pGenre, Developer pDeveloper, VideoGame pVideoGame, String pDescription) {
        genre = pGenre;
        developer = pDeveloper;
        videoGame = pVideoGame;
        description = pDescription;
    }

    public VideoGameOverviewItem() {
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre pGenre) {
        genre = pGenre;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer pDeveloper) {
        developer = pDeveloper;
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame pVideoGame) {
        videoGame = pVideoGame;
    }

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }
}


