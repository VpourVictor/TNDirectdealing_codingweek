package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
@Getter
@Setter

public class ConversationItemController implements Initializable {
    @FXML
    private Text message;
    @FXML
    private Label dateText;
    @Override
    public void initialize(URL location, ResourceBundle resources){}

    public void setData(Message message_obj) {
        message.setText(message_obj.getContent());
        dateText.setText(message_obj.getDate());
    }
}
