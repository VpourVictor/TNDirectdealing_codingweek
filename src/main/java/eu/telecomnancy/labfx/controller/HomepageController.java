package eu.telecomnancy.labfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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
