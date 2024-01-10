package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.Main;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;

public class HexagonController{
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
        switch (pos) {
/*            case 0:
                if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Tableau de Bord");
                    labelDownLeft.setVisible(true);
                } else if (loaderPos.equals("UL")) {
                    labelDownRight.setText("Profil");
                    labelDownRight.setVisible(true);
                } else if (loaderPos.equals("DR")) {
                    labelUpLeft.setText("Se connecter");
                    labelUpLeft.setVisible(true);
                } else if (loaderPos.equals("DL")) {
                    labelUpRight.setText("S'inscrire");
                    labelUpRight.setVisible(true);
                }
                break;*/
            /*case 1:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Tableau de Bord");
                    labelLeft.setVisible(true);
                } else if (loaderPos.equals("DR")) {
                    labelUpLeft.setText("Home");
                    labelUpLeft.setVisible(true);
                }
                break;
            case 2:
                if (loaderPos.equals("L")) {
                    labelRight.setText("Profil");
                    labelRight.setVisible(true);
                } else if (loaderPos.equals("DL")) {
                    labelUpRight.setText("Home");
                    labelUpRight.setVisible(true);
                }
                break;
            case 4:
                if (loaderPos.equals("L")) {
                    labelRight.setText("S'inscrire");
                    labelRight.setVisible(true);
                } else if (loaderPos.equals("UL")) {
                    labelDownRight.setText("Home");
                    labelDownRight.setVisible(true);
                }
                break;
            case 5:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Se connecter");
                    labelLeft.setVisible(true);
                } else if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Home");
                    labelDownLeft.setVisible(true);
                }
                break;*/
            case 7:
                if (loaderPos.equals("L")) {
                    labelRight.setText("S'inscrire");
                    labelRight.setVisible(true);
                } else if (loaderPos.equals("R")) {
                    labelLeft.setText("Se connecter");
                    labelLeft.setVisible(true);
                }
                break;
            case 10:
                if (loaderPos.equals("L")) {
                    labelRight.setText("Home");
                    labelRight.setVisible(true);
                }
                break;
            case 13:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Home");
                    labelLeft.setVisible(true);
                }
                break;
            case 14:
                if (loaderPos.equals("UL")) {
                    labelDownRight.setText("Profil");
                    labelDownRight.setVisible(true);
                }
                else if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Template");
                    labelDownLeft.setVisible(true);
                }
                else if (loaderPos.equals("L")) {
                    labelRight.setText("Messagerie");
                    labelRight.setVisible(true);
                }
                break;
            case 20:
                if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Profil");
                    labelDownLeft.setVisible(true);
                }

                break;
        }
    }




}
