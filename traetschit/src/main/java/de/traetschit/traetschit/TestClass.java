package de.traetschit.traetschit;

/**
 * Created by Totz on 31.10.2014.
 */
public class TestClass {
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String write() {
        return this.getName() + " " + this.getLastName();
    }
}
