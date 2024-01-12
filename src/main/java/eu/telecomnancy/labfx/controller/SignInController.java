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
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignInController extends HexaSuper implements Initializable {

    private User user;

    private ArrayList<User> users = new ArrayList<>();

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField passwordHidden;

    @FXML
    private TextField passwordText;

    @FXML
    private Button validateSignInButton;

    @FXML
    private CheckBox passToggle;
    @Getter
    @FXML
    Polygon hexagon;

    @FXML
    public void mouseEnter(MouseEvent event) {
        hexagon.setStroke(Color.web("#F08A26"));
    }

    public void mouseExit(MouseEvent event) {
        hexagon.setStroke(Color.web("#6C2466"));
    }

    @FXML
    void connexion(ActionEvent event) throws IOException {
         if (!RegexUtils.isValidMail(mailTextField.getText())){
            new Alert(Alert.AlertType.ERROR, "L'addresse mail n'est pas valide").showAndWait();
        } else if (passwordValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Le mot de passe ne peut pas être vide").showAndWait();
        } else {
            users = JsonUtil.jsonToUsers();
            if (users.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter").showAndWait();
            } else {
                int index = 0;
                while (index < User.getNbUsers() && !users.get(index).getEmail().equals(mailTextField.getText())) {
                    index++;
                }
                if (index == User.getNbUsers()) {
                    new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter ou entrer un mail Valide").showAndWait();
                } else if (!users.get(index).getPassword().equals(passwordValue())) {
                    new Alert(Alert.AlertType.ERROR, "Le mot de passe est incorrect").showAndWait();
                } else if (users.get(index).getPassword().equals(passwordValue())) {
                    users.get(index).setConnected(true);
                    JsonUtil.usersToJson(users);
                    new Alert(Alert.AlertType.CONFIRMATION, "Bon retour parmi nous").showAndWait();

                    SceneController sceneController = new SceneController();
                    sceneController.goToAllPostsHexa(event, JsonUtil.jsonToPosts(), null);

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
        return passToggle.isSelected() ?
                passwordText.getText() : passwordHidden.getText();
    }

    //to hide or not the password
    @FXML
    public void toggleVisiblePassword(ActionEvent event) {
        if (passToggle.isSelected()) {
            passwordText.setText(passwordHidden.getText());
            passwordText.setVisible(true);
            passwordHidden.setVisible(false);
        } else {
            passwordHidden.setText(passwordText.getText());
            passwordHidden.setVisible(true);
            passwordText.setVisible(false);
        }
    }

    @FXML
    public void validate(ActionEvent event) throws IOException {
        if (!RegexUtils.isValidMail(mailTextField.getText())) {
            new Alert(Alert.AlertType.ERROR, "L'addresse mail n'est pas valide").showAndWait();
        } else if (passwordValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Le mot de passe ne peut pas être vide").showAndWait();
        } else {
            users = JsonUtil.jsonToUsers();
            if (users.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter").showAndWait();
            } else {
                int index = 0;
                while (index < User.getNbUsers() && !users.get(index).getEmail().equals(mailTextField.getText())) {
                    index++;
                }
                if (index == User.getNbUsers()) {
                    new Alert(Alert.AlertType.ERROR, "Veuillez vous inscrire avant de vous connecter ou entrer un mail Valide").showAndWait();
                } else if (!users.get(index).getPassword().equals(passwordValue())) {
                    new Alert(Alert.AlertType.ERROR, "Le mot de passe est incorrect").showAndWait();
                } else if (users.get(index).getPassword().equals(passwordValue())) {
                    users.get(index).setConnected(true);
                    JsonUtil.usersToJson(users);
                    users = JsonUtil.jsonToUsers();
                    for (User user1 : users){
                        if(user1.isConnected()){
                            user = user1;
                        }
                    }
                    SceneController sceneController = new SceneController();
                    System.out.println(user.getEmail());
                    sceneController.goToMainUser(event, 14, user);
                }
            }

        }

    }
}
