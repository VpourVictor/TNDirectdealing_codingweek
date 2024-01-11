package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.ApplicationToPost;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.Service;
import eu.telecomnancy.labfx.model.Tool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

public class PostApplicationController {
    @FXML
    public Label emailApplicant;
    @FXML
    public Text dates;
    @FXML
    private CheckBox check;

    @Getter
    private static ArrayList<LocalDate> datesAppli = new ArrayList<>();

    @FXML
    public VBox listApplications;

    @FXML
    private Text comment;

    private Post post;

    private ApplicationToPost applicationToPost;

    private ArrayList<ApplicationToPost> applications;

    private ArrayList<Post> posts;

    @FXML
    private Button accepter;

    @FXML
    private Button refuser;

    public void initData(LocalDate date) {
        datesAppli = new ArrayList<>();
        check.setText(date.toString());
        check.setOnAction(event -> {
            if (check.isSelected()) {
                PostApplyController.getPost().getDatesOccupied().add(date);
                datesAppli.add(date);
            } else {
                PostApplyController.getPost().getDatesOccupied().remove(date);
                datesAppli.remove(date);
            }
        });
    }

    public void initData(Post post) {
        this.post = post;
        SceneController sceneController = new SceneController();
        if (post.getApplications() != null)
            sceneController.goToApplicationOverview(post.getApplications(), listApplications);
        else
            listApplications.getChildren().add(new Text("No application"));
    }

    public void initData(ApplicationToPost applicationToPost) {
        this.applicationToPost = applicationToPost;
        posts = JsonUtil.jsonToPosts();
        for (Post post : posts) {
            ArrayList<Integer> app = (ArrayList<Integer>) post.getApplications();
            for (Integer id : app) {
                if (id == applicationToPost.getIdAppli()) {
                    this.post = post;
                    break;
                }
            }
        }

        comment.setText(applicationToPost.getComment());
        emailApplicant.setText(applicationToPost.getApplicantEmail());
        dates.setText(applicationToPost.getDates().toString());
        applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
        for (ApplicationToPost application : applications) {
            if (application.getIdAppli() == applicationToPost.getIdAppli()) {
                if (application.isAccepted()) {
                    accepter.setVisible(false);
                    refuser.setVisible(false);
                } else {
                    accepter.setVisible(true);
                    refuser.setVisible(true);
                }
            }
        }
    }

    public void back(ActionEvent event) {
        SceneController sceneController = new SceneController();
        if (post instanceof Service)
            sceneController.goToOverviewServicePost(event, post);

        if (post instanceof Tool)
            sceneController.goToOverviewToolPost(event, post);
    }

    public void accept(ActionEvent event) {
        applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
        for (ApplicationToPost application : applications) {

            if (application.getIdAppli() == applicationToPost.getIdAppli()) {
                application.setAccepted(true);
            }
        }

        JsonUtil.applicationsToJson(applications);
        SceneController sceneController = new SceneController();
        if (post instanceof Service)
            sceneController.goToOverviewServicePost(event, post);

        if (post instanceof Tool)
            sceneController.goToOverviewToolPost(event, post);
    }

    public void refuse(ActionEvent event) {
        applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
        for (ApplicationToPost application : applications) {
            if (application.getIdAppli() == applicationToPost.getIdAppli()) {
                application.setAccepted(false);

                int taille = post.getDatesOccupied().size();
                ArrayList<LocalDate> temp = new ArrayList<>();
                for (int i = 0; i < taille; i++) {
                    temp.add(post.getDatesOccupied().get(i));
                }

                for(int i = 0; i < taille; i++) {
                    if (application.getDates().contains(post.getDatesOccupied().get(i))){
                        LocalDate date = post.getDatesOccupied().get(i);
                        temp.remove(date);
                    }
                }

                post.setDatesOccupied(temp);
            }
        }

        JsonUtil.applicationsToJson(applications);
        JsonUtil.postsToJson(posts);
        SceneController sceneController = new SceneController();
        if (post instanceof Service)
            sceneController.goToOverviewServicePost(event, post);

        if (post instanceof Tool)
            sceneController.goToOverviewToolPost(event, post);
    }
}
