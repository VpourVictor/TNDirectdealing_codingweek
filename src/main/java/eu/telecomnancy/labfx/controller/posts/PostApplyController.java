package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.HexaSuper;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostApplyController extends HexaSuper {

    @FXML
    public TextArea comment;

    @FXML
    public VBox listDates;

    @Getter
    @Setter
    private static Post post;

    private List<User> users = JsonUtil.jsonToUsers();

    private List<ApplicationToPost> applications = new ArrayList<>();

    private List<Post> posts = JsonUtil.jsonToPosts();

    @Getter
    @Setter
    private static boolean isModification;

    @Getter
    @Setter
    private static ApplicationToPost applicationToPost;

    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;
    public Polygon getHexagon() {
        return hexagon;
    }

    public void initData(Post post) {
        if (listDates != null){
            SceneController sceneController = new SceneController();
            ArrayList<LocalDate> dates = new ArrayList<>();
            for (LocalDate date : post.getDates()) {
                if (!post.getDatesOccupied().contains(date)) {
                    dates.add(date);
                }
            }

            PostApplyController.setModification(false);
            sceneController.goToChekcDate(dates, listDates, post, new ArrayList<>());
        }
    }

    public void save_application(ActionEvent actionEvent) {
        if (!isModification){
            applications = JsonUtil.jsonToApplications();
            ApplicationToPost applicationToPost = new ApplicationToPost(comment.getText());
            for (User user : users) {
                if (user.isConnected()){
                    applicationToPost.setApplicantEmail(user.getEmail());
                    user.getAppliedToPosts().add(post.getIdPost());
                }
            }
            JsonUtil.usersToJson((ArrayList<User>) users);
            applicationToPost.setDates(PostApplicationController.getDatesAppli());
            applications.add(applicationToPost);
            JsonUtil.applicationsToJson(applications);

            for (Post post1 : posts) {
                if (post1.getIdPost() == post.getIdPost()) {
                    post1.getApplications().add(applicationToPost.getIdAppli());
                    for (LocalDate date : PostApplicationController.getDatesAppli()) {
                        post1.getDatesOccupied().add(date);
                    }
                }
            }
            SceneController sceneController = new SceneController();
            JsonUtil.postsToJson((ArrayList<Post>) posts);
            sceneController.goToAllPosts(actionEvent, (ArrayList<Post>) posts);
        }
        else {
            applications = JsonUtil.jsonToApplications();
            for (ApplicationToPost application : applications) {
                if (application.getIdAppli() == applicationToPost.getIdAppli()) {
                    application.setComment(comment.getText());
                    application.setDates(PostApplicationController.getDatesAppli());
                }
            }
            JsonUtil.applicationsToJson(applications);
            posts = JsonUtil.jsonToPosts();
            for (Post post1 : posts) {
                if (post1.getIdPost() == post.getIdPost()) {
                    post1.setDatesOccupied(new ArrayList<>());
                    for (LocalDate date : PostApplicationController.getDatesAppli()) {
                        post1.getDatesOccupied().add(date);
                    }
                }
            }
            JsonUtil.postsToJson((ArrayList<Post>) posts);
            SceneController sceneController = new SceneController();
            sceneController.goToAllPosts(actionEvent, (ArrayList<Post>) posts);
        }
    }

    public void back(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, (ArrayList<Post>) posts);
    }

    public void initData(ApplicationToPost applicationToPost) {
        comment.setText(applicationToPost.getComment());
        SceneController sceneController = new SceneController();
        ArrayList<LocalDate> checkedDates = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>(post.getDates());

        for (LocalDate date : dates) {
            if (applicationToPost.getDates().contains(date)) {
                checkedDates.add(date);
            }
        }

        PostApplyController.setModification(true);
        PostApplyController.setApplicationToPost(applicationToPost);
        sceneController.goToChekcDate(dates, listDates, post, checkedDates);
    }
}
