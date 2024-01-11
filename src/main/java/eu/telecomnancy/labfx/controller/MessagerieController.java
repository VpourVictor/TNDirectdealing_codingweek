package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Getter
@Setter
public class MessagerieController extends HexaSuper implements Initializable {
    private Stage primaryStage;
    private boolean delete;
    private boolean create;
    private ArrayList<Button> list_boutons = new ArrayList<>();
    private User user;
    private ArrayList<User> users;
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
    @FXML
    private Button boutonAccueil;
    @FXML
    private Button boutonMessage;
    @FXML
    private Button boutonConv;
    @FXML
    private Button boutonInfo;
    @FXML
    private Polygon polygon;
    @FXML
    private Button boutonAnnuler;
    @FXML
    private Button boutonContact;
    @FXML
    private TextField mailChose;
    @FXML
    private Label prompt1;
    @FXML
    private Label prompt2;
    @Override
    public void initialize(URL Location, ResourceBundle resources){
        load();
    }

    public void setAndLoad(User user){
        this.user = user;
        this.pseudoText.setText(user.getPseudo());
        this.mailText.setText(user.getEmail());
        delete = false;
        create = false;
        prompt2.setVisible(false);
        polygon.setVisible(create);
        prompt1.setVisible(create);
        mailChose.setVisible(create);
        boutonContact.setVisible(create);
        boutonAnnuler.setVisible(create);
        load();

    }

    private void load(){
        if (user != null) {
            List<Conversation> convs = user.getConvs();
            listcontacts.getChildren().clear();
            for (int i = 0; i < convs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/message_item.fxml"));  //a check
                try {
                    Button button = fxmlLoader.load();
                    if (delete) {
                        button.getStyleClass().add("suppr-boutons");
                    }
                    MessageItemController mic = fxmlLoader.getController();
                    mic.setUser(user);
                    mic.setData(convs.get(i));
                    mic.setDelete(delete);
                    listcontacts.getChildren().add(button);
                    list_boutons.add(button);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void goToAccueil(){}     //  TODO BOUTONS

    public void supprMessage(ActionEvent event){
        if (!listcontacts.getChildren().isEmpty()) {
            delete = !delete;
            boutonAccueil.setDisable(delete);
            boutonConv.setDisable(delete);
            boutonInfo.setDisable(delete);
            load();
        }
    }

    public void newConvo(){
        create = !create;
        boutonAccueil.setDisable(create);
        boutonConv.setDisable(create);
        boutonInfo.setDisable(create);
        boutonMessage.setDisable(create);
        for (Button button : list_boutons){
            button.setDisable(create);
        }

        polygon.setVisible(create);
        prompt1.setVisible(create);
        prompt2.setVisible(false);
        mailChose.setVisible(create);
        boutonContact.setVisible(create);
        boutonAnnuler.setVisible(create);
    }
    public void changeInfo(){}

    public void contact(ActionEvent event) throws IOException {
        String mail = mailChose.getText();
        users = JsonUtil.jsonToUsers("src/main/resources/json/users.json");
        int i = 0;
        int nb_user = User.getNbUsers();
        while ((i < nb_user) && (!users.get(i).getEmail().equals(mail))) {
            i++;
        }
        if (i == nb_user){
            prompt2.setVisible(true);
        }
        else {
            User receiver = users.get(i);
            Conversation conversation = new Conversation(user, receiver);       //TODO ajouter conversation au json
            user.addConv(conversation);     //TODO avant de add, verifier si la conversation existe déjà
            receiver.addConv(conversation);
            SceneController sc = new SceneController();
            sc.openConv(user, conversation, event);
        }
    }
}
