package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
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
import java.util.List;

public class PostApplicationController {
    @FXML
    public Label emailApplicant;
    @FXML
    public Text dates;
    public Button supprimer;
    public Button modifier;
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

    @FXML
    private ArrayList<User> users;

    public void initData(LocalDate date, ArrayList<LocalDate> checkedDates) {
        datesAppli = new ArrayList<>();
        check.setText(date.toString());
        check.setSelected(checkedDates.contains(date));
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
        if (applicationToPost != null){
            for (Post post : posts) {
                ArrayList<Integer> app = (ArrayList<Integer>) post.getApplications();
                for (Integer id : app) {
                    if (id == applicationToPost.getIdAppli()) {
                        this.post = post;
                        break;
                    }
                }
            }
        }

        if (this.applicationToPost.isAccepted()) {
            supprimer.setVisible(false);
            modifier.setVisible(false);
        }
        comment.setText(applicationToPost.getComment());
        emailApplicant.setText(applicationToPost.getApplicantEmail());
        dates.setText(applicationToPost.getDates().toString());
        users = JsonUtil.jsonToUsers();
        for (User user : users) {
            if (user.isConnected() && user.getEmail().equals(post.getAuthorEmail())){
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

    public void delete(ActionEvent event) {
        users = JsonUtil.jsonToUsers();
        for (User user : users) {
            if (user.isConnected()){
                user.getAppliedToPosts().remove((Integer) applicationToPost.getIdAppli());
            }
        }

        JsonUtil.usersToJson(users);

        applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
        for (ApplicationToPost application : applications) {
            if (application.getIdAppli() == applicationToPost.getIdAppli()) {
                applications.remove(application);
                System.out.println("id remove");
                System.out.println(application.getIdAppli());
                ApplicationToPost.getListId().remove((Integer) application.getIdAppli());
                ApplicationToPost.setNbAppli(ApplicationToPost.getNbAppli() - 1);
                System.out.println("list id");
                System.out.println(ApplicationToPost.getListId());
                System.out.println(ApplicationToPost.getNbAppli());

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
                post.getApplications().remove((Integer) application.getIdAppli());
                break;
            }
        }

        JsonUtil.postsToJson(posts);
        for(ApplicationToPost applicationToPost : applications){
            System.out.println(applicationToPost.getIdAppli());
        }
        JsonUtil.applicationsToJson(applications);
        SceneController sceneController = new SceneController();
        if (post instanceof Service)
            sceneController.goToOverviewServicePost(event, post);

        if (post instanceof Tool)
            sceneController.goToOverviewToolPost(event, post);

    }

    public void modify(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToModifApplication(event, applicationToPost);
    }
}
