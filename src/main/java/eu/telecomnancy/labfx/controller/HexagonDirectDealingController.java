package eu.telecomnancy.labfx.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public class HexagonDirectDealingController extends HexaSuper{
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
