package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private List<LocalDate> dates;
    private List<LocalDate> datesOccupied;
    private Address address;
    private Image image;
    private State state;
    private Type_Date type_date;
    @Getter
    protected static List<Integer> listId = new ArrayList<>();
    private List<Integer> applications;

    public Post(String description, String title, String authorEmail, ArrayList<LocalDate> dates, ArrayList<LocalDate> datesOccupied,Type_Date type_date, Address adress, Image image, State state, List<Integer> applications) {
        this.description = description;
        this.title = title;
        this.authorEmail = authorEmail;
        this.dates = dates;
        this.datesOccupied = datesOccupied;
        this.type_date = type_date;
        this.address = adress;
        this.image = image;
        this.state = state;
        this.applications = applications;
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

    public static void setListId(List<Integer> listId) {
        Post.listId = listId;
    }
}
