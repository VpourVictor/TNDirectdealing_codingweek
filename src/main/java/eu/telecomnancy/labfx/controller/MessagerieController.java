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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView pdp = new ImageView();

    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;

    public Polygon getHexagon() {
        return hexagon;
    }
    @FXML
    private Button boutonMessage;
    @FXML
    private TextField mailChose;
    @FXML
    private Button boutonConv;
    @FXML
    private Button boutonInfo;
    @FXML
    private Button boutonAnnuler;
    @FXML
    private Button boutonContact;
    @FXML
    private Label prompt2;
    @FXML
    private Pane panecontact;
    @FXML
    private Label prompt3;
    @FXML
    private Label prompt4;
    @Override
    public void initialize(URL Location, ResourceBundle resources){
        load();
    }

    public void setAndLoad(User user){
        this.user = user;
        pseudoText.setText(user.getPseudo());
        mailText.setText(user.getEmail());
        Image image = user.getProfilePicture();
        pdp.setImage(image);
        delete = false;
        create = false;
        prompt2.setVisible(false);
        prompt3.setVisible(false);
        prompt4.setVisible(false);
        panecontact.setVisible(create);
        load();

    }

    private void load(){
        if (user != null) {
            List<Conversation> convs = user.getConvs();     //TODO les recups dans le json à la place
            listcontacts.getChildren().clear();
            for (int i = 0; i < convs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/message_item.fxml"));  //a check
                try {
                    Button button = fxmlLoader.load();
                    if (delete) {
                        button.getStyleClass().add("suppr-boutons");
                    }
                    MessageItemController mic = fxmlLoader.getController();     //TODO recuperer les messages dans le json
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

    public void supprMessage(ActionEvent event){
        if (!listcontacts.getChildren().isEmpty()) {
            delete = !delete;
            boutonConv.setDisable(delete);
            boutonInfo.setDisable(delete);
            load();
        }
    }
    public void newConvo(){
        create = !create;
        boutonConv.setDisable(create);
        boutonInfo.setDisable(create);
        boutonMessage.setDisable(create);
        for (Button button : list_boutons){
            button.setDisable(create);
        }

        prompt2.setVisible(false);
        prompt3.setVisible(false);
        prompt4.setVisible(false);
        panecontact.setVisible(create);
    }
    public void changeInfo(){}

    public void contact(ActionEvent event) throws IOException {
        String mail = mailChose.getText();
        users = JsonUtil.jsonToUsers();
        int i = 0;
        String user_email = user.getEmail();

        if (mail.equals(user_email)){
            prompt4.setVisible(true);
            prompt3.setVisible(false);
            prompt2.setVisible(false);
        }
        else {
            if (JsonUtil.conversationExiste(mail, user_email)) {
                prompt3.setVisible(true);
                prompt2.setVisible(false);
                prompt4.setVisible(false);
            } else {
                while ((i < User.getNbUsers()) && (!users.get(i).getEmail().equals(mail))) {
                    i++;
                }
                if (i == User.getNbUsers()) {
                    prompt2.setVisible(true);
                    prompt3.setVisible(false);
                    prompt4.setVisible(false);
                } else {
                    User receiver = users.get(i);
                    Conversation conversation = new Conversation(user, receiver);
                    JsonUtil.saveConvInJson(conversation);
                    user.addConv(conversation);
                    receiver.addConv(conversation);
                    SceneController sc = new SceneController();
                    sc.openConv(user, conversation, event);
                }
            }
        }
    }
    public void contactHexa(ActionEvent event) throws IOException {
        String mail = mailChose.getText();
        users = JsonUtil.jsonToUsers();
        int i = 0;
        String user_email = user.getEmail();

        if (mail.equals(user_email)){
            prompt4.setVisible(true);
            prompt3.setVisible(false);
            prompt2.setVisible(false);
        }
        else {
            if (JsonUtil.conversationExiste(mail, user_email)) {
                prompt3.setVisible(true);
                prompt2.setVisible(false);
                prompt4.setVisible(false);
            } else {
                while ((i < User.getNbUsers()) && (!users.get(i).getEmail().equals(mail))) {
                    i++;
                }
                if (i == User.getNbUsers()) {
                    prompt2.setVisible(true);
                    prompt3.setVisible(false);
                    prompt4.setVisible(false);
                } else {
                    User receiver = users.get(i);
                    Conversation conversation = new Conversation(user, receiver);
                    JsonUtil.saveConvInJson(conversation);
                    user.addConv(conversation);
                    receiver.addConv(conversation);
                    SceneController sc = new SceneController();
                    sc.goToMainMessagerie(event,26, user, conversation);
                }
            }
        }
    }
}
