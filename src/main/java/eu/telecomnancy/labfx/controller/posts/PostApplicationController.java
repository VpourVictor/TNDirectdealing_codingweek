package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.model.Post;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

public class PostApplicationController {
    @FXML
    private CheckBox check;

    @Getter
    private static ArrayList<LocalDate> datesAppli = new ArrayList<>();

    public void initData(LocalDate date) {
        datesAppli = new ArrayList<>();
        check.setText(date.toString());
        check.setOnAction(event -> {
            // System.out.println("check");
            if (check.isSelected()) {
                PostApplyController.getPost().getDatesOccupied().add(date);
                datesAppli.add(date);
            } else {
                PostApplyController.getPost().getDatesOccupied().remove(date);
                datesAppli.remove(date);
            }

            /*for (LocalDate dateOccupied : PostApplyController.getPost().getDatesOccupied()) {
                System.out.println(dateOccupied);
            }*/
        });
    }
}
