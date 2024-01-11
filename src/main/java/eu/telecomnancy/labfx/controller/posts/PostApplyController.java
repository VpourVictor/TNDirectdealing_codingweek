package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.ApplicationToPost;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostApplyController {

    @FXML
    public TextArea comment;

    @FXML
    public VBox listDates;

    @Getter
    @Setter
    private static Post post;

    private List<User> users = JsonUtil.jsonToUsers();

    private List<ApplicationToPost> applications = JsonUtil.jsonToApplications();

    public void initData(Post post) {
        if (listDates != null){
            SceneController sceneController = new SceneController();
            sceneController.goToChekcDate((ArrayList<LocalDate>) post.getDates(), listDates, post);
        }
    }

    public void save_application(ActionEvent actionEvent) {
        ApplicationToPost applicationToPost = new ApplicationToPost(comment.getText());
        for (User user : users) {
            if (user.isConnected())
                applicationToPost.setApplicantEmail(user.getEmail());
        }
        // todo à vérifier
        applicationToPost.setDates(PostApplicationController.getDatesAppli());
        applications.add(applicationToPost);
        JsonUtil.applicationsToJson(applications);
        post.getApplications().add(applicationToPost.getIdAppli());
    }
}
