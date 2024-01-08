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
    private static int id;
    private String description;
    private String title;
    private User author;
    private DateCouple dateCouple;

    private List<DateCouple> occupiedDates;

    private Address adress;
    private Image image;
    private State state;

    public Post(String description, String title, User author, LocalDate start, LocalDate end, Address adress, Image image, State state) {
        id++;
        this.description = description;
        this.title = title;
        this.author = author;
        if (start != null && end != null) {
            this.dateCouple = new DateCouple(start, end);
        } else if (start == null && end != null) {
            this.dateCouple = new DateCouple(LocalDate.now(), end);
        } else this.dateCouple = new DateCouple(Objects.requireNonNullElseGet(start, LocalDate::now), null);
        this.adress = adress;
        this.image = image;
        this.state = state;
    }
}
