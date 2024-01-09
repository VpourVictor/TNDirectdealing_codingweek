package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Address;
import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    private User user;

    @FXML
    private Button browseButton;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField countryTF;

    @FXML
    private Label firstnameSignUp;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private ImageView imageProfile;

    @FXML
    private Label nameSignUp;

    @FXML
    private TextField nameTextField;

    @FXML
    private CheckBox passToggle;

    @FXML
    private PasswordField passwordHidden;

    @FXML
    private PasswordField passwordHiddenConfirmation;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField mailTextField;

    @FXML
    private TextField postalCodeTF;

    @FXML
    private Label pseudoSignUp;

    @FXML
    private TextField pseudoTextField;

    @FXML
    private TextField streetNameTF;

    @FXML
    private Label streetNumber;

    @FXML
    private TextField streetNumberTF;

    @FXML
    void browse(ActionEvent event) {

    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {

        //TODO verif que tous les champs sont remplis
        User user = new User(firstnameTextField.getText(), nameTextField.getText(), mailTextField.getText());

        Address address = new Address(Integer.parseInt(streetNumberTF.getText()),
                streetNameTF.getText(),
                Integer.parseInt(postalCodeTF.getText()),
                cityTF.getText(),
                null, //TODO add region
                countryTF.getText());


        user.setAddress(address);

        user.setPseudo(pseudoTextField.getText());

        //TODO sauvegarder l'image

        // TODO traiter le cas où le mdp n'est pas le même lors de la confirmation
        if (passwordValue() == passwordHidden.getText()){
            user.setPassword(passwordValue());
        }

        user.setConnected(true);
        // TODO ajouter le user à la BD
        SceneController sceneController = new SceneController();
        sceneController.goToAccueil(event); //TODO ne pas renvoyer vers l'acceuil
        user.setConnected(true);
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
}

