package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class Service extends Post{
    private String descriptionService;

    private List<Person> providers = new ArrayList<>();

    public Service(String description, String title, String authorEmail, ArrayList<LocalDate> dates, Type_Date type_date, Address adress, Image image, State state, String descriptionService){
        super(description, title, authorEmail, dates, type_date, adress, image, state);
        this.descriptionService = descriptionService;
        this.setIdPost(Post.id);
    }

    public Service(int id, String description, String title, String authorEmail, ArrayList<LocalDate> dates, Type_Date type_date, Address adress, Image image, State state, String descriptionService, List<Person> providers){
        super(description, title, authorEmail, dates, type_date, adress, image, state);
        this.descriptionService = descriptionService;
        this.providers = providers;
        this.setIdPost(id);
    }

    public Service(Post post, String descriptionService, List<Person> providers){
        super(post.getDescription(), post.getTitle(), post.getAuthorEmail(), (ArrayList<LocalDate>) post.getDates(), post.getType_date(), post.getAddress(), post.getImage(), post.getState());
        this.descriptionService = descriptionService;
        this.providers = providers;
        Post.id++;
        Post.listId.add(Post.id);
        this.setIdPost(Post.id);
    }

    @Override
    public String getStateTool() {
        return null;
    }

    @Override
    public void setStateTool(String text) {

    }
}
