package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.posts.PostApplyController;
import eu.telecomnancy.labfx.controller.posts.PostEditController;
import eu.telecomnancy.labfx.controller.posts.PostOverviewController;
import eu.telecomnancy.labfx.controller.posts.PostApplicationController;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goToAccueil(ActionEvent event)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage.fxml"));
        load(event, loader);
    }

    public void goToAllPosts(ActionEvent event, ArrayList<Post> posts, ArrayList<String> stateRadioBtn) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/all.fxml"));
        //System.out.println("WE ARE INSIDE THE GOTOALL");
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.initData(null, stateRadioBtn);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void goToOverviewToolPost(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/overview_tool_post.fxml"));
        try {
            root = loader.load();
            PostOverviewController controller = loader.getController();
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToOverviewServicePost(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/overview_service_post.fxml"));
        try {
            root = loader.load();
            PostOverviewController controller = loader.getController();
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load(ActionEvent event, FXMLLoader loader) {
        try {
            root = loader.load();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToEditService(ActionEvent event, Post post, boolean modify) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/edit_service.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setPart2(true);
            controller.setModify(modify);
            controller.initData(post,null);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToEditTool(ActionEvent event, Post post, boolean modify) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/edit_tool.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setModify(modify);
            controller.setPart2(true);
            controller.initData(post,null);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToSignIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signIn.fxml"));
        root = loader.load();
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signUp.fxml"));
        root = loader.load();
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToEditPost(ActionEvent event, Post post, boolean modify) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/new.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setModify(modify);
            controller.initData(post,null);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConv(User user, Conversation conversation, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/conversation.fxml"));
        try {
            root = fxmlLoader.load();
            ConversationController cc = fxmlLoader.getController();
            cc.setConv(conversation);
            cc.setAndLoad(user);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void goToCreatePost(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/new.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setPart2(false);
            controller.initData(null, null);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToRowPost(ArrayList<Post> posts, VBox listPost) {
        for (Post post1 : posts){
            FXMLLoader loader = new FXMLLoader();
            if (post1 instanceof Service)
                loader.setLocation(getClass().getResource("/posts/postService_row_overview.fxml"));
            else
                loader.setLocation(getClass().getResource("/posts/postTool_row_overview.fxml"));
            try {
                AnchorPane pane = loader.load();
                PostOverviewController controller = loader.getController();
                controller.initData(post1);
                listPost.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void goBackMessagerie(User user, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/messagerie.fxml"));
        root = fxmlLoader.load();
        MessagerieController mc = fxmlLoader.getController();
        mc.setAndLoad(user);
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void goToMain(ActionEvent event, int position) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));
        try {
            root = loader.load();
            Screen screen = Screen.getPrimary();
            int width = (int) screen.getBounds().getWidth();
            int height = (int) screen.getBounds().getHeight();
            MainController mainController = loader.getController();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            double[] coord = mainController.getLayout();
            int offX = (int) -coord[0];
            int offY = (int) -coord[1];
            root.translateXProperty().set(offX);
            root.translateYProperty().set(offY);
            mainController.setOffX(offX);
            mainController.setOffY(offY);
            mainController.setPosition(position);
            mainController.setRoot(root);
            //mainController.getPaneTest().setVisible(false);
            mainController.teleportation(position);
            mainController.updateHexagon();
            stage.setWidth(width);
            stage.setHeight(height);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void goToMainEdit(ActionEvent event, int position, Post post, boolean modify) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));
        try {
            root = loader.load();
            Screen screen = Screen.getPrimary();
            int width = (int) screen.getBounds().getWidth();
            int height = (int) screen.getBounds().getHeight();
            MainController mainController = loader.getController();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            double[] coord = mainController.getLayout();
            int offX = (int) -coord[0];
            int offY = (int) -coord[1];
            root.translateXProperty().set(offX);
            root.translateYProperty().set(offY);
            mainController.setOffX(offX);
            mainController.setOffY(offY);
            mainController.setPosition(position);
            mainController.setRoot(root);
            mainController.setPost(post);
            mainController.setModify(modify);
            //mainController.getPaneTest().setVisible(false);
            mainController.teleportation(position);
            mainController.updateHexagon();
            stage.setWidth(width);
            stage.setHeight(height);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void goToMainValidate(ActionEvent event, int position, Post post) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));
        try {
            root = loader.load();
            Screen screen = Screen.getPrimary();
            int width = (int) screen.getBounds().getWidth();
            int height = (int) screen.getBounds().getHeight();
            MainController mainController = loader.getController();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            double[] coord = mainController.getLayout();
            int offX = (int) -coord[0];
            int offY = (int) -coord[1];
            root.translateXProperty().set(offX);
            root.translateYProperty().set(offY);
            mainController.setOffX(offX);
            mainController.setOffY(offY);
            mainController.setPosition(position);
            mainController.setRoot(root);
            mainController.setPost(post);
            //mainController.getPaneTest().setVisible(false);
            mainController.teleportation(position);
            mainController.updateHexagon();
            stage.setWidth(width);
            stage.setHeight(height);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void goToMainMessagerie(ActionEvent event, int position, User user, Conversation conversation) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));
        try {
            root = loader.load();
            Screen screen = Screen.getPrimary();
            int width = (int) screen.getBounds().getWidth();
            int height = (int) screen.getBounds().getHeight();
            MainController mainController = loader.getController();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            double[] coord = mainController.getLayout();
            int offX = (int) -coord[0];
            int offY = (int) -coord[1];
            root.translateXProperty().set(offX);
            root.translateYProperty().set(offY);
            mainController.setOffX(offX);
            mainController.setOffY(offY);
            mainController.setPosition(position);
            mainController.setRoot(root);
            mainController.setUserMain(user);
            if(conversation != null) {
                mainController.setConversation(conversation);
            }
            mainController.teleportation(position);
            mainController.updateHexagon();
            stage.setWidth(width);
            stage.setHeight(height);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void goToApplyPost(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/apply_post.fxml"));
        try {
            root = loader.load();
            PostApplyController controller = loader.getController();
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void goToChekcDate(ArrayList<LocalDate> dates, VBox listDates, Post post, ArrayList<LocalDate> checkedDate) {
        for (LocalDate date : dates){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/posts/checkbox.fxml"));
            try {
                AnchorPane pane = loader.load();
                PostApplicationController controller = loader.getController();
                PostApplyController.setPost(post);
                controller.initData(date, checkedDate);
                listDates.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void goToApplications(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/all_applications.fxml"));
        try {
            root = loader.load();
            PostApplicationController controller = loader.getController();
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void goToApplicationOverview(List<Integer> applications, VBox listApplications){
        for (Integer application : applications){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/posts/overview_application.fxml"));
            try {
                AnchorPane pane = loader.load();
                PostApplicationController controller = loader.getController();
                if (JsonUtil.jsonToApplications() != null){
                    for (ApplicationToPost applicationToPost : Objects.requireNonNull(JsonUtil.jsonToApplications())){
                        if (applicationToPost.getIdAppli() == application){
                            controller.initData(applicationToPost);
                        }
                    }
                }
                listApplications.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void goToMyApplication(ActionEvent event, ApplicationToPost application) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/my_application.fxml"));
        try {
            root = loader.load();
            PostApplicationController controller = loader.getController();
            controller.initData(application);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void goToModifApplication(ActionEvent event, ApplicationToPost applicationToPost) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/apply_post.fxml"));
        try {
            root = loader.load();
            PostApplyController controller = loader.getController();
            controller.initData(applicationToPost);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void goToTB(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonBoard.fxml"));
        try {
            root = loader.load();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
