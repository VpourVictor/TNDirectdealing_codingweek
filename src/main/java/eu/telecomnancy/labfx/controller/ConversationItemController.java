package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter

public class ConversationItemController implements Initializable {
    @FXML
    private Text message;
    @Override
    public void initialize(URL location, ResourceBundle resources){}

    public void setData(Message message_obj) {
        message.setText(message_obj.getContent());
    }
}
