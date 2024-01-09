package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Service extends Post{
    private String descriptionService;

    private List<Person> providers = new ArrayList<>();

    public Service(String description, String title, String authorEmail, LocalDate start, LocalDate end, Address adress, Image image, State state, String descriptionService){
        super(description, title, authorEmail, start, end, adress, image, state);
        this.descriptionService = descriptionService;
        this.setIdPost(Post.id);
    }

    public Service(int id, String description, String title, String authorEmail, LocalDate start, LocalDate end, Address adress, Image image, State state, String descriptionService){
        super(description, title, authorEmail, start, end, adress, image, state);
        this.descriptionService = descriptionService;
        this.setIdPost(id);
    }

    public Service(Post post, String descriptionService, List<Person> providers){
        super(post.getDescription(), post.getTitle(), post.getAuthorEmail(), post.getDateCouple().getDateStart(), post.getDateCouple().getDateEnd(), post.getAddress(), post.getImage(), post.getState());
        this.descriptionService = descriptionService;
        this.providers = providers;
        Post.id++;
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
