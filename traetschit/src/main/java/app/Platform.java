package app;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="platforms")

public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    //@JoinTable(name = "platforms_video_games", joinColumns = { @JoinColumn(name = "platforms_id") }, inverseJoinColumns = { @JoinColumn(name = "videoGame_id") })

    public Platform() {
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
    /*
    public List<VideoGame> getVideoGame() {
        return videoGame;
    }
    */
}
