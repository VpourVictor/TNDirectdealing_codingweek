package eu.telecomnancy.labfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomepageController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToSignIn(event);

    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToSignUp(event);
    }
}
