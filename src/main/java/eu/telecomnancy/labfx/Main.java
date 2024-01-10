package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.HexagonBoardController;
import eu.telecomnancy.labfx.controller.MainController;
import java.util.ArrayList;
import java.util.Objects;

import eu.telecomnancy.labfx.controller.MessagerieController;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
/*        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/messagerie.fxml"));
        Parent root2 = loader2.load();
        Scene scene2 = new Scene(root2);
        MessagerieController messagerieController = loader2.getController();
        messagerieController.setAndLoad(user);
        User user = new User("test", "test","test@gmail.com");
        User user2 = new User("test2", "test2","test@gmail.com");
        Message message = new Message(user,user2,"Salut");
        Conversation conversation = new Conversation(user,user2);
        conversation.addMessage(message);
        user.getConvs().add(conversation);*/
//        ArrayList<User> users =JsonUtil.jsonToUserList("src/main/resources/json/users.json");
//        System.out.println("Taille users :" + users.size());
//        for (User user : users ){
//            System.out.println("Prénom : " + user.getFirstName() + ", Nom : " + user.getLastName());
//            user.setPseudo("WAKANDA");
//        }
//        JsonUtil.userListToJson( users, "src/main/resources/json/users.json");

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
        System.out.println(width);
        System.out.println(height);
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/HexagonBoard.fxml"));
        Pane paneBoard = loader2.load();
        HexagonBoardController c = loader2.getController();
        mainController.setRoot(root);
        double[] coord = mainController.getLayout();
        int offX = (int) -coord[0];
        int offY = (int) -coord[1];
        root.translateXProperty().set(offX);
        root.translateYProperty().set(offY);
        mainController.setOffX(offX);
        mainController.setOffY(offY);
        // primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}

