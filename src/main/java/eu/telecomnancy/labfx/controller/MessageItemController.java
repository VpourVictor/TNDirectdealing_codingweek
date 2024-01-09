package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class MessageItemController implements Initializable {
    private User user;
    @FXML
    private ImageView photo;
    @FXML
    private Label nomText;
    @FXML
    private Label mailText;
    @Override
    public void initialize(URL location, ResourceBundle resources){}

    public void setData(Conversation conversation) {
        if (conversation.getUser1() == user) {
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

    public void openConvo(){}   //TODO ouvrir une conversation existante.
}
