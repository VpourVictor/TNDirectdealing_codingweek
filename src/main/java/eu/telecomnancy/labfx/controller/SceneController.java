package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.posts.PostEditController;
import eu.telecomnancy.labfx.controller.posts.PostOverviewController;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goToAccueil(ActionEvent event)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage.fxml"));
        load(event, loader);
    }

    public void goToAllPosts(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/all.fxml"));
        load(event, loader);
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

    public void goToEditService(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/edit_service.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setPart2(true);
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToEditTool(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/edit_tool.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setPart2(true);
            controller.initData(post);
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

    public void goToEditPost(ActionEvent event, Post post) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/create.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.initData(post);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToCreatePost(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/posts/create.fxml"));
        try {
            root = loader.load();
            PostEditController controller = loader.getController();
            controller.setPart2(false);
            controller.initData(null);
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
