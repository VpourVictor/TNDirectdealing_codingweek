package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Setter
public class ConversationController implements Initializable {
    private User user;
    private Conversation conv;
    @FXML
    private VBox listMessages;
    @FXML
    private TextArea msgText;
    @FXML
    private Label pseudoText;
    @FXML
    private Label mailText;
    @FXML
    private Label ratingText;
    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL Location, ResourceBundle resources){
        load();
    }

    private void load(){
        if (user != null) {
            pseudoText.setText(conv.getOther(user).getPseudo());
            mailText.setText(conv.getOther(user).getEmail());
            List<Message> messages = conv.getMessages();
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getSender() == user){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/conversation_item_send.fxml"));
                    try {
                        HBox hbox = fxmlLoader.load();
                        ConversationItemController cic = fxmlLoader.getController();
                        cic.setData(messages.get(i));
                        listMessages.getChildren().add(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/conversation_item_receive.fxml"));  //a check

                    try {
                        HBox hbox = fxmlLoader.load();
                        ConversationItemController cic = fxmlLoader.getController();
                        cic.setData(messages.get(i));
                        listMessages.getChildren().add(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void setAndLoad(User user){
        this.user = user;
        load();

    }

    public void goMenu(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        sc.goBackMessagerie(user, event);
    }

    public void delConv(ActionEvent event) throws IOException {
        user.delConv(conv);
        SceneController sc = new SceneController();
        sc.goBackMessagerie(user, event);
    }

    public void sendMsg(ActionEvent event) throws IOException {
        if (!msgText.getText().isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();
            String msg = msgText.getText();
            String date = dtf.format(now);
            msgText.setText("");
            Message message = new Message(user, conv.getOther(user), msg, date);
            conv.addMessage(message);
            //SceneController sc = new SceneController();
            //sc.openConv(user, conv, event);
            reload();
        }
    }

    public void reload(){
        listMessages.getChildren().clear();
        load();
        scroll.setVvalue(scroll.getVmax());
    }
}
