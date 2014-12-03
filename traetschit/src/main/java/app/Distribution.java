package app;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="distributions")

public class Distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public Distribution() {
    }

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        this.id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        this.name = pName;
    }
}
