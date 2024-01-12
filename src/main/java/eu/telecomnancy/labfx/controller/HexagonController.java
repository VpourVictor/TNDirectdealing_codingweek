package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.Main;
import javafx.animation.*;
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
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import lombok.Setter;

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
            case 7:
                if (loaderPos.equals("L")) {
                    labelRight.setText("S'inscrire");
                    labelRight.setVisible(true);
                    animation();
                } else if (loaderPos.equals("R")) {
                    labelLeft.setText("Se connecter");
                    labelLeft.setVisible(true);
                    animation();
                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 10:
                if (loaderPos.equals("L")) {
                    labelRight.setText("Home");
                    labelRight.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }

                break;
            case 13:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Home");
                    labelLeft.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 14:
                if (loaderPos.equals("UL")) {
                    labelDownRight.setText("Profil");
                    labelDownRight.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Template");
                    labelDownLeft.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("L")) {
                    labelRight.setText("Messagerie");
                    labelRight.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("R")) {
                    labelLeft.setText("Annonces");
                    labelLeft.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 15:
                if (loaderPos.equals("DL")) {
                    labelUpRight.setText("Messagerie");
                    labelUpRight.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("DR")) {
                    labelUpLeft.setText("Accueil");
                    labelUpLeft.setVisible(true);
                    animation();

                }

                else {
                    updateHexagon(hexagon);
                }
                break;
            case 17:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Créer une annonce");
                    labelLeft.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("L")) {
                    labelRight.setText("Accueil");
                    labelRight.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;

            case 20:
                if (loaderPos.equals("UR")) {
                    labelDownLeft.setText("Profil");
                    labelDownLeft.setVisible(true);
                    animation();

                }
                else if (loaderPos.equals("R")) {
                    labelLeft.setText("Accueil");
                    labelLeft.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 21, 24:
                if (loaderPos.equals("R")) {
                    labelLeft.setText("Modifier");
                    labelLeft.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 22:
                 if (loaderPos.equals("L")) {
                    labelRight.setText("Annonces");
                    labelRight.setVisible(true);
                    animation();

                }
                else {
                    updateHexagon(hexagon);
                }
                break;
            case 23, 25:
                if (loaderPos.equals("L")) {
                    labelRight.setText("Annonces");
                    labelRight.setVisible(true);
                    animation();

                }
                else if(loaderPos.equals("R")) {
                labelLeft.setText("Modifier");
                labelLeft.setVisible(true);
                animation();

            }

                else {
                    updateHexagon(hexagon);
                }
                break;


        }
    }
    public void animation(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(800), hexagonPane);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(800), hexagonPane);
        scaleTransition2.setToX(1.0);
        scaleTransition2.setToY(1.0);
        ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(800), hexagonPane);
        scaleTransition3.setToX(1.05);
        scaleTransition3.setToY(1.05);
        ScaleTransition scaleTransition4 = new ScaleTransition(Duration.millis(800), hexagonPane);
        scaleTransition4.setToX(1.0);
        scaleTransition4.setToY(1.0);
        SequentialTransition transition = new SequentialTransition(scaleTransition, scaleTransition2, scaleTransition3, scaleTransition4);
        transition.setCycleCount(-1);

        transition.play();

    }

    public void updateHexagon(Polygon hexagon){
        hexagon.setOnMouseEntered(null);
        hexagon.setOnMouseExited(null);
        KeyValue startValue = new KeyValue(hexagon.fillProperty(), Color.web("#6C2466"));
        KeyFrame kf = new KeyFrame(Duration.millis(500), startValue);
        Timeline t = new Timeline(kf);
        t.play();
    }



}
