package app;

        import javax.persistence.*;
        import javax.validation.constraints.NotNull;

@Entity
@Table(name="genres")

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public Genre() {
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
}
