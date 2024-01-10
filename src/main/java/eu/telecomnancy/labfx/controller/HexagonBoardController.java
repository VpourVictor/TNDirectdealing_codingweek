package eu.telecomnancy.labfx.controller;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HexagonBoardController extends HexaSuper{
    @FXML
    Pane hexagonPane;

    @FXML
    Polygon hexagon;

    public Polygon getHexagon() {
        return hexagon;
    }

    public Pane getHexagonPane() {
        return hexagonPane;
    }
}
