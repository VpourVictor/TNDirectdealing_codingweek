package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class MessageItemController implements Initializable {
    private boolean delete;
    private User user;
    private Conversation conversation;
    @FXML
    private ImageView photo;
    @FXML
    private Label nomText;
    @FXML
    private Label mailText;
    @Override
    public void initialize(URL location, ResourceBundle resources){}

    public void setData(Conversation conversation) {
        this.conversation = conversation;
        if (conversation.getUser1().getEmail().equals(user.getEmail())) {
            photo.setImage(conversation.getUser2().getProfilePicture());
            nomText.setText(conversation.getUser2().getPseudo());
            mailText.setText(conversation.getUser2().getEmail());
        }
        else{
            photo.setImage(conversation.getUser1().getProfilePicture());
            nomText.setText(conversation.getUser1().getPseudo());
            mailText.setText(conversation.getUser1().getEmail());
        }
    }

    public void openConvo(ActionEvent event) throws IOException {
        if (!delete) {
            SceneController sc = new SceneController();
            sc.openConvHexa(user, conversation, event);
        }
        else{
            user.delConv(conversation);
            JsonUtil.delConv(conversation);
            SceneController sc = new SceneController();
            sc.goBackMessagerieHexa(user, event);
        }
    }
}
