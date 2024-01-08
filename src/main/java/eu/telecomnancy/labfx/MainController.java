package eu.telecomnancy.labfx;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Parent root;

    private int offX;

    private int offY;

    private boolean moving;

    @FXML
    Pane centerHexagon;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moving = false;
    }

    public void setRoot(Parent root){
        this.root = root;
    }


    public void setOffX(int offX) {
        this.offX = offX;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }
    @FXML
    private void moveUp(ActionEvent event) throws InterruptedException {
        if(!moving) {
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByY(918);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offY = offY + 918;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }

    }
    @FXML
    private void moveDown(ActionEvent event) throws InterruptedException {
        if(!moving) {
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByY(-918);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offY = offY - 918;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    @FXML
    private void moveLeft(ActionEvent event) throws InterruptedException {
            if(!moving) {
                moving = true;

                TranslateTransition transition = new TranslateTransition();
                transition.setNode(root);
                transition.setByX(1632);
                transition.setDuration(Duration.millis(1000));
                transition.setInterpolator(Interpolator.LINEAR);
                transition.play();
                offX = offX + 1632;
                transition.setOnFinished(event1 -> {
                    moving = false;
                });

            }


    }

    @FXML
    private void moveRight(ActionEvent event) throws InterruptedException {
            if(!moving) {
                moving = true;

                TranslateTransition transition = new TranslateTransition();
                transition.setNode(root);
                transition.setByX(-1632);
                transition.setDuration(Duration.millis(1000));
                transition.setInterpolator(Interpolator.LINEAR);
                transition.play();
                offX = offX - 1632;
                transition.setOnFinished(event1 -> {
                    moving = false;
                });

            }

    }

    public double[] getLayout() {
        double x = centerHexagon.getLayoutX();
        double y = centerHexagon.getLayoutY();
        double[] tab = new double[2];
        tab[0] = x;
        tab[1] = y;
        return tab;
    }


}
