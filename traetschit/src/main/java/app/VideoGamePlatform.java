package app;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="videogameplatform")
public class VideoGamePlatform {
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private VideoGame videoGame;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Platform platform;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public VideoGamePlatform(VideoGame pVideoGame, Platform pPlatform) {
        videoGame = pVideoGame;
        platform = pPlatform;
    }

    public VideoGamePlatform() {
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame videoGame) {
        this.videoGame = videoGame;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
