package eu.telecomnancy.labfx.model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Tool extends Post {
    private String stateTool;
    public Tool(String description, String title, User author, LocalDate start, LocalDate end, Address adress, Image image, State state, String stateTool) {
        super(description, title, author, start, end, adress, image, state);
        this.stateTool = stateTool;
    }

    public Tool (Post post, String stateTool) {
        super(post.getDescription(), post.getTitle(), post.getAuthor(), post.getDateCouple().getDateStart(), post.getDateCouple().getDateEnd(), post.getAdress(), post.getImage(), post.getState());
        this.stateTool = stateTool;
    }
}

