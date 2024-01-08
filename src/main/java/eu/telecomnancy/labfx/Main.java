package eu.telecomnancy.labfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("JavaFx Demo");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainController mainController = loader.getController();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Prevent the window from being maximized
        Screen screen = Screen.getPrimary();
        int width = (int) screen.getBounds().getWidth();
        int height = (int) screen.getBounds().getHeight();
        primaryStage.setWidth(width); // 1536   + 64 = 1600
        primaryStage.setHeight(height); // 864  + 36 = 900
        System.out.println(width);
        System.out.println(height);
        int offX = -1632;
        int offY = -918;
        root.translateXProperty().set(offX);
        root.translateYProperty().set(offY);
        mainController.setRoot(root);
        mainController.setOffX(offX);
        mainController.setOffY(offY);
        primaryStage.show();
    }
}
