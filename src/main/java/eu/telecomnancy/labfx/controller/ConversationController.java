package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConversationController implements Initializable {
    private User user;
    private Conversation conv;
    private VBox listmessages;

    @Override
    public void initialize(URL Location, ResourceBundle resources){
        load();
    }

    private void load(){
        if (user != null) {
            List<Message> messages = conv.getMessages();
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getSender() == user){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/conversation_item_send.fxml"));
                    try {
                        HBox hbox = fxmlLoader.load();
                        ConversationItemController cic = fxmlLoader.getController();
                        cic.setSender(user);
                        cic.setData(messages.get(i));
                        listmessages.getChildren().add(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/conversation_tiem_receive.fxml"));  //a check

                    try {
                        Button button = fxmlLoader.load();
                        ConversationItemController cic = fxmlLoader.getController();
                        cic.setUser(user);
                        cic.setData(messages.get(i));
                        listmessages.getChildren().add(button);
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
}
