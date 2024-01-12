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

//        ArrayList<Post> posts = new ArrayList<>();
//        ArrayList<User> users = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            Address address = new Address(i, "rue"+i, i, "Nancy", "Lorraine", "France");
//            Post post = new Tool("La description"+i, "Le beau titre"+i, "user"+i+"@test.com", null, null, address, null, null, null);
//            User user = new User("Prenom"+i, "Nom" + i, "user"+i+"@test.com");
//            ArrayList<Double> testEval = AlgoUtil.createRandomDoubleArrayList(9);
//            user.setEvaluationList(testEval);
//            post.setIdPost(i);
//
//            post.setState(State.EN_COURS);
//            users.add(user);
//            posts.add(post);
//        }
//
//        posts.get(1).getAddress().setCity("Paris");
//        posts.get(4).getAddress().setCity("Paris");
//        ArrayList<Integer> listInt = new ArrayList<>();
//        listInt.add(1);
//        listInt.add(2);
//        listInt.add(3);
//        posts.get(1).setAuthorEmail("user2@test.com");
//        posts.get(2).setAuthorEmail("user2@test.com");
//        posts.get(3).setAuthorEmail("user2@test.com");
//        users.get(2).setPostedPosts(listInt);
//
//        ArrayList<Integer> listIntb = new ArrayList<>();
//        listIntb.add(0);
//        listIntb.add(4);
//        listIntb.add(5);
//        posts.get(0).setAuthorEmail("user4@test.com");
//        posts.get(4).setAuthorEmail("user4@test.com");
//        posts.get(5).setAuthorEmail("user4@test.com");
//        users.get(4).setPostedPosts(listIntb);
//        posts.get(3).setState(State.MASQUE);
//
//        //users.sort(Comparator.comparingDouble(User::getEvaluation).reversed());
//
//        AlgoUtil algoUtil = new AlgoUtil(users, posts);
//        for (User user : users) {
//            //System.out.println(user.getEvaluation());
//        }
//        System.out.println("Taille de posts : " + posts.size() );
//        ArrayList<Post> cityPost = algoUtil.postInState(State.EN_COURS);
//        for (Post post : cityPost) {
//            System.out.println("User : " + algoUtil.getUserFromMail(post.getAuthorEmail()).getFirstName());
//            System.out.println(algoUtil.getUserFromMail(post.getAuthorEmail()).getEvaluation());
//        }





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

