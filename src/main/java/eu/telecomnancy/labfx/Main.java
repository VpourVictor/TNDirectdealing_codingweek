package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.HexagonBoardController;
import eu.telecomnancy.labfx.controller.MainController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

import eu.telecomnancy.labfx.controller.MessagerieController;
import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONObject;

import static java.lang.String.valueOf;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        JsonUtil.getNbApplications();
        JsonUtil.getNbPosts();
        JsonUtil.getNbUsers();

        ArrayList<User> users = JsonUtil.jsonToUsers();
        for (User user : users) {
            if (user.isConnected())
                user.setConnected(false);
        }
        JsonUtil.usersToJson(users);

        primaryStage.setTitle("JavaFx Demo");
        primaryStage.setTitle("DirectDealing");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainController mainController = loader.getController();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        Screen screen = Screen.getPrimary();
        int width = (int) screen.getBounds().getWidth();
        int height = (int) screen.getBounds().getHeight();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        double[] coord = mainController.getLayout();
        int offX = (int) -coord[0];
        int offY = (int) -coord[1];
        root.translateXProperty().set(offX);
        root.translateYProperty().set(offY);
        mainController.setOffX(offX);
        mainController.setOffY(offY);
        //primaryStage.setFullScreen(true);
        MainController.setRoot(root);
        mainController.setPosition(7);
        //mainController.getPaneTest().setVisible(false);
        mainController.teleportation(7);
        mainController.updateHexagon();

        primaryStage.show();
    }
}

