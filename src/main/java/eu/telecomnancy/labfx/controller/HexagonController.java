package eu.telecomnancy.labfx.controller;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;

public class HexagonController {
    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;
    @FXML
    Label labelDownLeft;
    @FXML
    Label labelDownRight;
    @FXML
    Label labelUpLeft;
    @FXML
    Label labelUpRight;
    @FXML
    Label labelLeft;
    @FXML
    Label labelRight;
    @FXML
    public void mouseEnter(MouseEvent event) {

        hexagon.setStroke(Color.web("#F08A26"));

    }
    public void mouseExit(MouseEvent event) {

        hexagon.setStroke(Color.web("#6C2466"));

    }

    public void updateLabel(int pos, String loaderPos) {
        if (pos == 0 && loaderPos =="UR") {
            labelDownLeft.setText("Profil");
            labelDownLeft.setVisible(true);
        }
    }

}
