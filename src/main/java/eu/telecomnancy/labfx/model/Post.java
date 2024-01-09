package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public abstract class Post {
    protected static int id;
    private int idPost;
    private String description;
    private String title;
    private String authorEmail;
    private DateCouple dateCouple;

    private List<DateCouple> occupiedDates;

    private Address address;
    private Image image;
    private State state;

    public Post(String description, String title, String authorEmail, LocalDate start, LocalDate end, Address adress, Image image, State state) {
        this.description = description;
        this.title = title;
        this.authorEmail = authorEmail;
        if (start != null && end != null) {
            this.dateCouple = new DateCouple(start, end);
        } else if (start == null && end != null) {
            this.dateCouple = new DateCouple(LocalDate.now(), end);
        } else this.dateCouple = new DateCouple(Objects.requireNonNullElseGet(start, LocalDate::now), null);
        this.address = adress;
        this.image = image;
        this.state = state;
    }

    public abstract String getDescriptionService();
    public abstract String getStateTool();
    public abstract List<Person> getProviders();

    public static int getNbPosts() {
        return id;
    }

    public static void setNbPosts(int nbPosts) {
        Post.id = nbPosts;
    }

    public abstract void setDescriptionService(String text);

    public abstract void setStateTool(String text);

    public abstract void setProviders(List<Person> providers);
}
