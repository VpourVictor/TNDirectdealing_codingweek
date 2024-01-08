package eu.telecomnancy.labfx.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Parent root;

    private int offX;

    private int offY;

    private boolean moving;

    @FXML
    Pane centerHexagon;

    @FXML
    Pane paneUpLeft;
    @FXML
    Pane paneUpRight;
    @FXML
    Pane paneLeft;
    @FXML
    Pane paneRight;
    @FXML
    Pane paneDownLeft;
    @FXML
    Pane paneDownRight;
    @FXML
    Pane paneCenter;
    @FXML
    Polygon hexagonCenter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moving = false;
        try {
            loadHexagon();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Pane getPaneCenter() {
        return paneCenter;
    }

    public Polygon getHexagonCenter() {
        return hexagonCenter;
    }

    public void setOffX(int offX) {
        this.offX = offX;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

    @FXML
    private void moveUpLeft(ActionEvent event) throws InterruptedException {
        if (!moving) {
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(435);
            transition.setByY(693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX + 435;
            offY = offY + 693;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }

    }

    @FXML
    private void moveUpRight(ActionEvent event) throws InterruptedException, IOException {
        if (!moving) {

            paneUpRight.getChildren().getLast().setVisible(true);
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-435);
            transition.setByY(693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX - 435;
            offY = offY + 693;
            ScaleTransition s = new ScaleTransition();
            s.setNode(paneUpRight.getChildren().getLast());
            s.setFromX(1.0);
            s.setFromY(1.0);
            s.setToX(1.1);
            s.setToY(1.1);
            s.setDuration(Duration.millis(1000));
            s.play();
            Pane paneC = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
            paneCenter.getChildren().setAll(paneC);
            updateOldHexa(paneCenter);
            hexagonCenter.setStroke(Color.web("#6C2466"));

            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }

    }

    @FXML
    private void moveDownLeft(ActionEvent event) throws InterruptedException {
        if (!moving) {
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(435);
            transition.setByY(-693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX + 435;
            offY = offY - 693;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    @FXML
    private void moveDownRight(ActionEvent event) throws InterruptedException {
        if (!moving) {
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-435);
            transition.setByY(-693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX - 435;
            offY = offY - 693;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    @FXML
    private void moveLeft(ActionEvent event) throws InterruptedException {
        if (!moving) {
            moving = true;

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(870);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX + 870;
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }


    }

    @FXML
    private void moveRight(ActionEvent event) throws InterruptedException {
        if (!moving) {
            moving = true;

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-870);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
            offX = offX - 870;
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

    public void loadHexagon() throws IOException {
        Pane paneL = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        Pane paneR = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        Pane paneUL = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        Pane paneUR = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        Pane paneDL = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        Pane paneDR = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaProfile.fxml"));
        Pane paneProfile = loader.load();
        paneProfile.setVisible(false);
        ProfileController controllerP = loader.getController();
        controllerP.updateBorder();
        paneLeft.getChildren().setAll(paneL);
        paneRight.getChildren().setAll(paneR);
        paneUpLeft.getChildren().setAll(paneUL);
        paneUpRight.getChildren().setAll(paneUR, paneProfile);
        paneDownLeft.getChildren().setAll(paneDL);
        paneDownRight.getChildren().setAll(paneDR);
    }

    public void updateOldHexa(Node node){
        ScaleTransition s = new ScaleTransition();
        s.setNode(node);
        s.setFromX(1.1);
        s.setFromY(1.1);
        s.setToX(1.0);
        s.setToY(1.0);
        s.setDuration(Duration.millis(1000));
        s.play();

    }

}
