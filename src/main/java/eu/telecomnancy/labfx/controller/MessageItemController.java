package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageItemController implements Initializable {
    @FXML
    private ImageView img;
    @FXML
    private Label nomText;
    @FXML
    private Label mailText;
    @Override
    public void initialize(URL location, ResourceBundle resources){}

    public void setData(User user){
        Image imgprofil = new Image(getClass().getResourceAsStream(user.))
    }
}
