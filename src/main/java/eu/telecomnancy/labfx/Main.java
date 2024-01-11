package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.HexagonBoardController;
import eu.telecomnancy.labfx.controller.MainController;
import java.util.ArrayList;
import java.util.Objects;

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

        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Address address = new Address(i, "rue"+i, i, "Nancy", "Lorraine", "France");
            Post post = new Tool("La description"+i, "Le beau titre"+i, "user"+i+"@test.com", null, null, address, null, null, null);
            User user = new User("Prenom"+i, "Nom" + i, "user"+i+"@test.com");
            users.add(user);
            posts.add(post);
        }

        posts.get(1).getAddress().setCity("Paris");
        posts.get(4).getAddress().setCity("Paris");

        AlgoUtil algoUtil = new AlgoUtil(posts);
        ArrayList<Post> cityPost = algoUtil.postSortedByCity ("Paris");
        for (Post post : cityPost) {
            System.out.println("CityParis : " + post.getAddress().getStreetName());
        }



        primaryStage.setTitle("JavaFx Demo");

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
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/HexagonBoard.fxml"));
        Pane paneBoard = loader2.load();
        HexagonBoardController c = loader2.getController();
        double[] coord = mainController.getLayout();
        int offX = (int) -coord[0];
        int offY = (int) -coord[1];
        root.translateXProperty().set(offX);
        root.translateYProperty().set(offY);
        mainController.setOffX(offX);
        mainController.setOffY(offY);
        //primaryStage.setFullScreen(true);
        MainController.setRoot(root);
        mainController.setPosition(0);

        primaryStage.show();
    }
}

