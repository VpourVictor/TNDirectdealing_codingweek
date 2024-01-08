package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class SignInController {

    private User user;

    @FXML
    private TextArea mailTextArea;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button validateSignInButton;

    @FXML
    void connexion(ActionEvent event) throws IOException {
        // TODO vérifier si le mail et le mdp sont bon
        // TODO si c'est bon changement de scene vers le menu
        // TODO sinon retour à l'acceuil
        SceneController sceneController = new SceneController();
        sceneController.goToAccueil(event); //TODO ne pas renvoyer vers l'acceuil
        // TODO user.setConnected(true);
    }
}
