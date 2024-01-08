package eu.telecomnancy.labfx.model;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Tool extends Post {
    private String stateTool;
    public Tool(String description, String title, User author, LocalDate start, LocalDate end, String adress, Image image, State state, String stateTool) {
        super(description, title, author, start, end, adress, image, state);
        this.stateTool = stateTool;
    }
}

