package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.Address;
import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HomepageController extends HexaSuper{


    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;
    @FXML
    Polygon hexagon;
    @FXML
    Pane hexagonPane;
    @FXML
    Pane paneTest;
    @FXML
    public void mouseEnter(MouseEvent event) {

        hexagon.setStroke(Color.web("#F08A26"));

    }
    public void mouseExit(MouseEvent event) {

        hexagon.setStroke(Color.web("#6C2466"));

    }
    @FXML
    public void runApp(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.goToMain(event,7);
    }


    public Polygon getHexagon() {
        return hexagon;
    }

    @FXML
    void goToCreatePost(ActionEvent event){
        SceneController s = new SceneController();
        s.goToCreatePost(event);
    }
    @FXML
    void goMessagerie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/messagerie.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Image image = new Image("file:/C:/Users/ggran/OneDrive/Bureau/Telecom%20Cours/2E%20ANNEE/PCD/PROJET/src/main/resources/pictures/defaultpfp.png");
        Address adresse = new Address(5,"d",6,"y","h", "s");
        User user = new User("test", "test", "test@gmail.com", "Rezko", "pas", adresse, image);
        User user2 = new User("test2", "test2","test2@gmail.com", "Rezko2", "pas", adresse, image);
        User user3 = new User("test3", "test2","aaa", "Rezko3", "pas", adresse, image);
        User user4 = new User("test4", "test2","bbb", "Rezko4", "pas", adresse, image);
        User.setNbUsers(1);
        ArrayList<User> users = JsonUtil.jsonToUsers();
        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        JsonUtil.usersToJson(users);
        User.setNbUsers(5);
        MessagerieController messagerieController = loader.getController();
        messagerieController.setAndLoad(user);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void goProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profil_Info.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void signIn(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToSignIn(event);


    }

    @FXML
    void signUp(ActionEvent event) throws IOException, InterruptedException {
        SceneController sceneController = new SceneController();
        sceneController.goToSignUp(event);
    }

    public Pane getPaneTest() {
        return paneTest;
    }
}
