package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Service extends Post{
    private String descriptionService;
    // todo ajouter une liste de personnes qui vont exécuter le service
    public Service(String description, String title, User author, LocalDate start, LocalDate end, String adress, Image image, State state, String descriptionService){
        super(description, title, author, start, end, adress, image, state);
        this.descriptionService = descriptionService;
    }
}
