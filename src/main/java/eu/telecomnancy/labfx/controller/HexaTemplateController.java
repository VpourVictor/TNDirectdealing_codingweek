package eu.telecomnancy.labfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class HexaTemplateController extends HexaSuper{
    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;


    public Polygon getHexagon() {
        return hexagon;
    }
}
