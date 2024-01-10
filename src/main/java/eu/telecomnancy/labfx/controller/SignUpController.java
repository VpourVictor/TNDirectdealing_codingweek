package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.Address;
import eu.telecomnancy.labfx.model.RegexUtils;
import eu.telecomnancy.labfx.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignUpController {

    private User user;

    @FXML
    private Button browseButton;

    @FXML
    private TextField cityTF;


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
    private TextField regionTF;

    @FXML
    private ComboBox countryList;

    @FXML
    private Button validateButton;

    final FileChooser fileChooser = new FileChooser();
    private final ObservableList<String> countries = FXCollections.observableArrayList();

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
    void initialize() {
        if (countryList != null) {
            countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
        }
        this.toggleVisiblePassword(null);
        countryList.setItems(countries);
    }

    @FXML
    void browse(ActionEvent event) {
        fileChooser.setTitle("Choisir une image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            imageProfile.setImage(new Image(file.toURI().toString()));
        }
    }


    @FXML
    void createAccount(ActionEvent event) throws IOException {

        if (firstnameTextField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le nom ne peut pas être vide").showAndWait();
        }
        else if (nameTextField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le nom ne peut pas être vide").showAndWait();
        }
        else if ( pseudoTextField.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Le pseudo ne peut pas être vide").showAndWait();
        }
        else if (!RegexUtils.isValidMail(mailTextField.getText())){
            new Alert(Alert.AlertType.ERROR, "L'addresse mail n'est pas valide").showAndWait();
        }
        else if ( !RegexUtils.isNumeric(streetNumberTF.getText())) {
            new Alert(Alert.AlertType.ERROR, "Le numéro de la rue ne peut pas être vide et doit être un entier").showAndWait();
        }
        else if ( streetNameTF.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Le nom de rue ne peut pas être vide").showAndWait();
        }
        else if ( !RegexUtils.isNumeric(postalCodeTF.getText())) {
            new Alert(Alert.AlertType.ERROR, "Le code postal ne peut pas être vide et doit être un entier").showAndWait();
        }
        else if ( cityTF.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Le nom de la ville ne peut pas être vide").showAndWait();
        }
        else if (countryList.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner un pays").showAndWait();
        }
        else if ( regionTF.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Le nom de la région ne peut pas être vide").showAndWait();
        }
        else if ( passwordValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Le mot de passe ne peut pas être vide").showAndWait();
        }
        else if ( passwordHiddenConfirmation.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Vérifiez votre mot de passe").showAndWait();
        }
        else if (imageProfile.getImage() == null) {
            new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner une image").showAndWait();
        }
        else if (!( passwordHiddenConfirmation.getText()).equals(passwordValue())){
            new Alert(Alert.AlertType.ERROR, "Le mot de passe n'est pas le même").showAndWait();
        }
        else {

            ArrayList<User> users = JsonUtil.jsonToUserList("src/main/resources/json/users.json");

            User user = new User(firstnameTextField.getText(), nameTextField.getText(), mailTextField.getText());

            Address address = new Address(Integer.parseInt(streetNumberTF.getText()),
                    streetNameTF.getText(),
                    Integer.parseInt(postalCodeTF.getText()),
                    cityTF.getText(),
                    regionTF.getText(),
                    countryList.getValue().toString());
            user.setAddress(address);

            user.setPseudo(pseudoTextField.getText());
            user.setCoins(50);
            user.setProfilePicture(imageProfile.getImage());
            user.setPassword(passwordValue());
            user.setConnected(true);
            users.add(user);
            JsonUtil.userListToJson( users, "src/main/resources/json/users.json");
            SceneController sceneController = new SceneController();
            sceneController.goToAccueil(event); //TODO ne pas renvoyer vers l'acceuil

        }


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

