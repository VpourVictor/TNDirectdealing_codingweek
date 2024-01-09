package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.RegexUtils;
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


         if (!RegexUtils.isValidMail(mailTextArea.getText())){
            new Alert(Alert.AlertType.ERROR, "L'addresse mail n'est pas valide").showAndWait();
        }
        else if ( passwordValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Le mot de passe ne peut pas être vide").showAndWait();
        }
         // TODO vérifier si le mail est dans la BD
         // TODO vérifier dans la BD si le mail et le mdp sont bon (se correspondent)
        else {
             // TODO user.setConnected(true);
             SceneController sceneController = new SceneController();
             sceneController.goToEditPost(event, null); //TODO ne pas renvoyer vers l'acceuil

         }


        String emailEntered = mailTextArea.getText();
        String password = passwordValue();
        // TODO vérifier si le mail et le mdp sont bon
        // TODO si c'est bon changement de scene vers le menu
        // TODO sinon gérer l'erreur
        SceneController sceneController = new SceneController();
        sceneController.goToEditPost(event, null); //TODO ne pas renvoyer vers l'acceuil
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
