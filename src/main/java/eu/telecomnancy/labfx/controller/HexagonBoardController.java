package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.ApplicationToPost;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.User;
import eu.telecomnancy.labfx.view.FullCalendarView;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HexagonBoardController extends HexaSuper{
    @FXML
    Pane hexagonPane;

    @FXML
    Polygon hexagon;

    @FXML
    Label message;

    private ArrayList<User> users;

    @FXML Pane calendarPane;

    @FXML
    void initialize() {
        users = JsonUtil.jsonToUsers();
        for (User user : users) {
            if (user.isConnected()){
                message.setText("Welcome " + user.getPseudo());
            }
        }
        if (calendarPane != null)
            calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
    }

    public Polygon getHexagon() {
        return hexagon;
    }

    public Pane getHexagonPane() {
        return hexagonPane;
    }
}
