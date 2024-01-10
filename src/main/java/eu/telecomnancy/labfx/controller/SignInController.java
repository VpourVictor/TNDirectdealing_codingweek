package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.RegexUtils;
import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    Polygon hexagon;
    @FXML
    public void mouseEnter(MouseEvent event) {

        hexagon.setStroke(Color.web("#F08A26"));

    }
    public void mouseExit(MouseEvent event) {

        hexagon.setStroke(Color.web("#6C2466"));

    }
    public Polygon getHexagon() {
        return hexagon;
    }

    @FXML
    void connexion(ActionEvent event) throws IOException {
         if (!RegexUtils.isValidMail(mailTextArea.getText())){
            new Alert(Alert.AlertType.ERROR, "L'addresse mail n'est pas valide").showAndWait();
        }
        else if ( passwordValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Le mot de passe ne peut pas être vide").showAndWait();
        }

        else {
             ArrayList<User> users = JsonUtil.jsonToUserList("src/main/resources/json/users.json");
             for (User user : users){
                 System.out.println(user.getEmail());
             }
             if (users.isEmpty()){
                 new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter").showAndWait();
             }
             else {

                 int index = 0;
                 while( index < User.getNbUsers() && !users.get(index).getEmail().equals(mailTextArea.getText())){
                     index++;
                 }
                 if (index == User.getNbUsers()){
                     new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter ou entrer un mail Valide").showAndWait();
                 }
                 else if ( !users.get(index).getPassword().equals(passwordValue())){
                     new Alert(Alert.AlertType.ERROR, "Le mot de passe est incorrect").showAndWait();
                 }
                 else if (users.get(index).getPassword().equals(passwordValue())){
                     new Alert(Alert.AlertType.CONFIRMATION, "Bon retour parmi nous").showAndWait();
                     SceneController sceneController = new SceneController();
                     sceneController.goToEditPost(event, null, false);
                 }
             }

         }

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
