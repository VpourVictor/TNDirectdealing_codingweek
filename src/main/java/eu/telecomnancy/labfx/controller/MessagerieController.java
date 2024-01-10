package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@Getter
@Setter
public class MessagerieController extends HexaSuper implements Initializable {
    private Stage primaryStage;
    private User user;
    @FXML
    private VBox listcontacts;
    @FXML
    private Label pseudoText;
    @FXML
    private Label mailText;

    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;

    public Polygon getHexagon() {
        return hexagon;
    }
    @Override
    public void initialize(URL Location, ResourceBundle resources){
        load();
    }

    public void setAndLoad(User user){
        this.user = user;
        this.pseudoText.setText(user.getPseudo());
        this.mailText.setText(user.getEmail());

        load();

    }

    private void load(){
        if (user != null) {
            List<Conversation> convs = user.getConvs();
            for (int i = 0; i < convs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/message_item.fxml"));  //a check

                try {
                    Button button = fxmlLoader.load();
                    MessageItemController mic = fxmlLoader.getController();
                    mic.setUser(user);
                    mic.setPrimaryStage(primaryStage);
                    mic.setData(convs.get(i));
                    listcontacts.getChildren().add(button);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void goToAccueil(){}     //  TODO BOUTONS

    public void supprMessage(){}

    public void newConvo(){
    }
    public void changeInfo(){}
}
