package eu.telecomnancy.labfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import static java.lang.String.valueOf;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/homepage.fxml")));
            Scene scene = new Scene(root,1280,800);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

