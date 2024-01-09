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
    Polygon hexagon;
    @FXML
    public void mouseEnter(MouseEvent event) {

        hexagon.setStroke(Color.web("#F08A26"));

    }
    public void mouseExit(MouseEvent event) {

        hexagon.setStroke(Color.web("#6C2466"));

    }


    public void updateLabel(int pos, String loaderPos) {
        if (pos == 0 && loaderPos =="UR") {
            labelDownLeft.setText("Tableau de Bord");
            labelDownLeft.setVisible(true);
        }
        if (pos == 0 && loaderPos =="UL") {
            labelDownRight.setText("Profil");
            labelDownRight.setVisible(true);
        }

        if (pos == 0 && loaderPos =="DR") {
            labelUpLeft.setText("Se connecter");
            labelUpLeft.setVisible(true);
        }
        if (pos == 0 && loaderPos =="DL") {
            labelUpRight.setText("S'inscrire");
            labelUpRight.setVisible(true);
        }
        if(pos == 1 && loaderPos == "R"){
            labelLeft.setText("Tableau de Bord");
            labelLeft.setVisible(true);
        }
        if(pos == 1 && loaderPos == "DR"){
            labelUpLeft.setText("Home");
            labelUpLeft.setVisible(true);
        }
        if(pos == 2 && loaderPos == "L"){
            labelRight.setText("Profil");
            labelRight.setVisible(true);
        }
        if(pos == 2 && loaderPos == "DL"){
            labelUpRight.setText("Home");
            labelUpRight.setVisible(true);
        }


        if(pos == 4 && loaderPos == "L"){
            labelRight.setText("S'inscrire");
            labelRight.setVisible(true);
        }
        if(pos == 4 && loaderPos == "UL"){
            labelDownRight.setText("Home");
            labelDownRight.setVisible(true);
        }
        if(pos == 5 && loaderPos == "R"){
            labelLeft.setText("Se connecter");
            labelLeft.setVisible(true);
        }
        if(pos == 5 && loaderPos == "UR"){
            labelDownLeft.setText("Home");
            labelDownLeft.setVisible(true);
        }

    }

}
