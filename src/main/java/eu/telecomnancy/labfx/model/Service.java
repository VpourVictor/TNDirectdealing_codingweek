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
    private Enum<SensService> sensService;

    public Service(String description, String title, String authorEmail, ArrayList<LocalDate> dates, ArrayList<LocalDate> datesOccupied, Type_Date type_date, Address adress, Image image, State state, String descriptionService, List<Integer> applications){
        super(description, title, authorEmail, dates, datesOccupied, type_date, adress, image, state, applications);
        this.descriptionService = descriptionService;
        this.setIdPost(Post.id);
    }

    public Service(int id, String description, String title, String authorEmail, ArrayList<LocalDate> dates, ArrayList<LocalDate> datesOccupied, Type_Date type_date, Address adress, Image image, State state, String descriptionService, List<Person> providers, List<Integer> applications, Enum<SensService> sensService){
        super(description, title, authorEmail, dates, datesOccupied, type_date, adress, image, state, applications);
        this.descriptionService = descriptionService;
        this.providers = providers;
        this.sensService = sensService;
        this.setIdPost(id);
    }

    public Service(Post post, String descriptionService, List<Person> providers, Enum<SensService> sensService){
        super(post.getDescription(), post.getTitle(), post.getAuthorEmail(), (ArrayList<LocalDate>) post.getDates(), (ArrayList<LocalDate>) post.getDatesOccupied(), post.getType_date(), post.getAddress(), post.getImage(), post.getState(), post.getApplications());
        this.descriptionService = descriptionService;
        this.providers = providers;
        this.sensService = sensService;
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

    @Override
    public Enum<SensTool> getSensTool() {
        return null;
    }
}
