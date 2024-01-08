package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.MainController;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("JavaFx Demo");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaMain.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainController mainController = loader.getController();
        ScaleTransition s = new ScaleTransition();
        s.setNode(mainController.getPaneCenter());
        s.setFromX(1.0);
        s.setFromY(1.0);
        s.setToX(1.1);
        s.setToY(1.1);
        s.setDuration(Duration.millis(1000));
        s.play();
        mainController.getHexagonCenter().setStroke(Color.web("#F08A26"));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        Screen screen = Screen.getPrimary();
        int width = (int) screen.getBounds().getWidth();
        int height = (int) screen.getBounds().getHeight();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        System.out.println(width);
        System.out.println(height);


        mainController.setRoot(root);
        double[] coord = mainController.getLayout();
        int offX =(int) -coord[0];
        int offY =(int) -coord[1];
        root.translateXProperty().set(offX);
        root.translateYProperty().set(offY);
        mainController.setOffX(offX);
        mainController.setOffY(offY);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
