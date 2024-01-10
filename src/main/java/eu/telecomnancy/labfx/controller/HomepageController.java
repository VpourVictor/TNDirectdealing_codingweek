package eu.telecomnancy.labfx.controller;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;

public class HomepageController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;
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
    void goToCreatePost(ActionEvent event){
        SceneController s = new SceneController();
        s.goToCreatePost(event);
    }
    @FXML
    void goMessagerie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/messagerie.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        User user = new User("test", "test","test@gmail.com", "Rezko");
        User user2 = new User("test2", "test2","test@gmail.com", "Rezko2");
        Message message = new Message(user,user2,"Salut", "now");
        Conversation conversation = new Conversation(user,user2);
        Conversation conversation2 = new Conversation(user,user2);
        Conversation conversation3 = new Conversation(user,user2);


        conversation.addMessage(message);
        conversation2.addMessage(message);
        conversation3.addMessage(message);
        user.getConvs().add(conversation);
        user.getConvs().add(conversation2);
        user.getConvs().add(conversation3);
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
/*        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));
        Node pane = loader.load();
        MainController controller = loader.getController();
        controller.moveDownLeft(event);*/
    }
}
