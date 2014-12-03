package app;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name="videogamerelease")
public class VideoGameRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private VideoGame videoGame;

    @NotNull
    private Date releaseDate;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Country country;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Publisher publisher;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Distribution distribution;

    public VideoGameRelease(VideoGame pVideoGame, Date pReleaseDate, Country pCountry, Publisher pPublisher, Distribution pDistribution) {
        videoGame = pVideoGame;
        releaseDate = pReleaseDate;
        country = pCountry;
        publisher = pPublisher;
        distribution = pDistribution;
    }

    public VideoGameRelease() {
    }

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame pVideoGame) {
        videoGame = pVideoGame;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date pReleaseDate) {
        releaseDate = pReleaseDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country pCountry) {
        country = pCountry;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher pPublisher) {
        publisher = pPublisher;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution pDistribution) {
        distribution = pDistribution;
    }
}
