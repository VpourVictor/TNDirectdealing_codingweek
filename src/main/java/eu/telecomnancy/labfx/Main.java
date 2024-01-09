package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.ProfilController;
import eu.telecomnancy.labfx.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        try {
            User user = new User("granjon", "dezdzed","efkezjf@gmail.com", "Gabin", "Granjon");
            //FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation(getClass().getResource("/eu/telecomnancy/labfx/profil_Info.fxml"));
            //fxmlLoader.setControllerFactory(iC -> user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/profil_Info.fxml"));
            Parent root = fxmlLoader.load();
            ProfilController pc = fxmlLoader.getController();
            pc.setUser(user);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
}
