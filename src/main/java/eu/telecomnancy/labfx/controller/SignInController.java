package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable{

    private User user;

    @FXML
    private TextArea mailTextArea;

    @FXML
    private PasswordField passwordHidden;

    @FXML
    private TextField passwordText;

    @FXML
    private Button validateSignInButton;

    @FXML
    private CheckBox passToggle;

    @FXML
    void connexion(ActionEvent event) throws IOException {
        String emailEntered = mailTextArea.getText();
        String password = passwordValue();
        // TODO vérifier si le mail et le mdp sont bon
        // TODO si c'est bon changement de scene vers le menu
        // TODO sinon retour à l'acceuil
        SceneController sceneController = new SceneController();
        sceneController.goToAccueil(event); //TODO ne pas renvoyer vers l'acceuil
        // TODO user.setConnected(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.toggleVisiblePassword(null);
    }

    //method to get the password
    private String passwordValue() {
        return passToggle.isSelected()?
                passwordText.getText(): passwordHidden.getText();
    }

    //to hide or not the password
    @FXML
    public void toggleVisiblePassword(ActionEvent event) {
        if (passToggle.isSelected()){
            passwordText.setText(passwordHidden.getText());
            passwordText.setVisible(true);
            passwordHidden.setVisible(false);
        }
        else{
            passwordHidden.setText(passwordText.getText());
            passwordHidden.setVisible(true);
            passwordText.setVisible(false);
        }
    }
}
