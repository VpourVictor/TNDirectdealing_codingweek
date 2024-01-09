package eu.telecomnancy.labfx.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
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

    private int position;

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
    Polygon testPane1;
    @FXML
    Polygon testPane2;
    @FXML
    Polygon testPane3;

    private HexagonBoardController boardController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moving = false;
        position = 0;
        try {
            loadHexagon();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
/*        updateBorder(paneUpRight);
        updateBorder(paneUpLeft);
        updateBorder(paneLeft);
        updateBorder(paneRight);
        updateBorder(paneDownLeft);
        updateBorder(paneDownRight);*/
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Pane getPaneCenter() {
        return paneCenter;
    }


    public void setOffX(int offX) {
        this.offX = offX;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

    @FXML
    public void moveUpLeft(MouseEvent event) throws IOException {
        if (!moving) {
            root.translateXProperty().set(offX-435);
            root.translateYProperty().set(offY-693);
            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(435);
            transition.setByY(693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneDownRight);
            if(position == 0){
                position = 1;
            }
            else if(position == 3){
                position = 2;
            }
            else if(position == 5){
                position = 6;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();

            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    @FXML
    private void moveUpRight(MouseEvent event) throws InterruptedException, IOException {
        if (!moving) {
            root.translateXProperty().set(offX+435);
            root.translateYProperty().set(offY-693);

            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-435);
            transition.setByY(693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneDownLeft);
            if(position == 0){
                position = 2;
            }
            else if(position == 6){
                position = 1;
            }
            else if(position == 4){
                position = 3;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();

            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }

    }

    @FXML
    public void moveDownLeft(MouseEvent event) throws InterruptedException, IOException {
        if (!moving) {
            root.translateXProperty().set(offX-435);
            root.translateYProperty().set(offY+693);

            moving = true;
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(435);
            transition.setByY(-693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneUpRight);
            if(position == 0){
                position = 5;
            }
            else if(position == 3){
                position = 4;
            }
            else if(position == 1){
                position = 6;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    public Parent getRoot() {
        return root;
    }

    @FXML
    private void moveDownRight(MouseEvent event) throws InterruptedException, IOException {
        if (!moving) {
            moving = true;
            root.translateXProperty().set(offX+435);
            root.translateYProperty().set(offY+693);
            updateOldHexa(paneUpLeft);

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-435);
            transition.setByY(-693);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneUpLeft);
            if(position == 0){
                position = 4;
            }
            else if(position == 2){
                position = 3;
            }
            else if(position == 6){
                position = 5;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }
    }

    @FXML
    private void moveLeft(MouseEvent event) throws InterruptedException, IOException {
        if (!moving) {
            moving = true;
            root.translateXProperty().set(offX-870);
            updateOldHexa(paneRight);

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(870);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneRight);

            if(position == 0){
                position = 6;
            }
            else if(position == 2){
                position = 1;
            }
            else if(position == 4){
                position = 5;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();
            transition.setOnFinished(event1 -> {
                moving = false;
            });

        }


    }

    @FXML
    private void moveRight(MouseEvent event) throws InterruptedException, IOException {
        if (!moving) {
            moving = true;
            root.translateXProperty().set(offX+870);
            updateOldHexa(paneLeft);

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(root);
            transition.setByX(-870);
            transition.setDuration(Duration.millis(1000));
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();

            updateNewHexa(paneCenter);
            updateOldHexa(paneLeft);
            if(position == 0){
                position = 3;
            }
            else if(position == 1){
                position = 2;
            }
            else if(position == 5){
                position = 4;
            }
            else{
                position = 0;
            }
            updateHexagon();
            loadPosition();
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
        FXMLLoader loaderL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderUL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderUR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderDL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderDR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        Pane paneL = loaderL.load();
        Pane paneR = loaderR.load();
        Pane paneUL = loaderUL.load();
        Pane paneUR = loaderUR.load();
        Pane paneDL = loaderDL.load();
        Pane paneDR = loaderDR.load();

        HexagonController controllerUR = loaderUR.getController();
        HexagonController controllerUL = loaderUL.getController();
        HexagonController controllerDR = loaderDR.getController();
        HexagonController controllerDL = loaderDL.getController();
        HexagonController controllerR = loaderR.getController();
        HexagonController controllerL = loaderL.getController();

        controllerUR.updateLabel(position, "UR");
        controllerUL.updateLabel(position,"UL");
        controllerDR.updateLabel(position,"DR");
        controllerDL.updateLabel(position,"DL");
        controllerR.updateLabel(position,"R");
        controllerL.updateLabel(position,"L");
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/HexagonHomepage.fxml"));
        Pane paneC = loader2.load();
        HomepageController controller = loader2.getController();
        colorizeHexagon(controller.getHexagon(), true);
        updateNewHexa(paneCenter);

        paneCenter.getChildren().add(paneC);
        paneLeft.getChildren().setAll(paneL);
        paneRight.getChildren().setAll(paneR);
        paneUpLeft.getChildren().setAll(paneUL);
        paneUpRight.getChildren().setAll(paneUR);
        paneDownLeft.getChildren().setAll(paneDL);
        paneDownRight.getChildren().setAll(paneDR);
        updateHexagon();
        /*updateBorder(paneUpRight);
        updateBorder(paneUpLeft);
        updateBorder(paneDownRight);
        updateBorder(paneDownLeft);
        updateBorder(paneRight);
        updateBorder(paneLeft);*/

    }

    public void updateHexagon() throws IOException {

        FXMLLoader loaderL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderUL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderUR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderDL = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        FXMLLoader loaderDR = new FXMLLoader(getClass().getResource("/Hexagon.fxml"));
        Pane paneL = loaderL.load();
        Pane paneR = loaderR.load();
        Pane paneUL = loaderUL.load();
        Pane paneUR = loaderUR.load();
        Pane paneDR = loaderDR.load();
        Pane paneDL = loaderDL.load();

        HexagonController controllerUR = loaderUR.getController();
        HexagonController controllerUL = loaderUL.getController();
        HexagonController controllerDR = loaderDR.getController();
        HexagonController controllerDL = loaderDL.getController();
        HexagonController controllerR = loaderR.getController();
        HexagonController controllerL = loaderL.getController();

        controllerUR.updateLabel(position, "UR");
        controllerUL.updateLabel(position,"UL");
        controllerDR.updateLabel(position,"DR");
        controllerDL.updateLabel(position,"DL");
        controllerR.updateLabel(position,"R");
        controllerL.updateLabel(position,"L");

        paneLeft.getChildren().setAll(paneL);
        paneRight.getChildren().setAll(paneR);
        paneUpLeft.getChildren().setAll(paneUL);
        paneUpRight.getChildren().setAll(paneUR);
        paneDownRight.getChildren().setAll(paneDR);
        paneDownLeft.getChildren().setAll(paneDL);

    }

    public void loadPosition() throws IOException {
        System.out.println(position);
        Pane paneUR = FXMLLoader.load(getClass().getResource("/Hexagon.fxml"));

        if (position == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonHomepage.fxml"));
            Pane pane = loader.load();
            HomepageController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            paneCenter.getChildren().setAll(pane);
        }
        else if (position == 1){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaProfile.fxml"));
            Pane paneProfile = loader.load();
            ProfileController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            paneCenter.getChildren().setAll(paneProfile);
        }
        else if(position == 2){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonBoard.fxml"));
            Pane pane = loader.load();
            HexagonBoardController controller = loader.getController();
            colorizeHexagon(controller.getHexagonCenter(),true);
            paneCenter.getChildren().setAll(pane);


        }
        if (position == 4){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonSignIn.fxml"));
            Pane pane = loader.load();
            SignInController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            paneCenter.getChildren().setAll(pane);
        }
        else if (position == 5){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonSignUp.fxml"));
            Pane pane = loader.load();
            SignUpController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            paneCenter.getChildren().setAll(pane);
        }

    }

    public void updateOldHexa(Node node){
        ScaleTransition s = new ScaleTransition();
        s.setNode(node);
        s.setFromX(1.2);
        s.setFromY(1.2);
        s.setToX(1.0);
        s.setToY(1.0);
        s.setDuration(Duration.millis(1000));
        s.play();
    }

    public void updateNewHexa(Node node) {
        ScaleTransition s = new ScaleTransition();
        s.setNode(node);
        s.setFromX(1.0);
        s.setFromY(1.0);
        s.setToX(1.2);
        s.setToY(1.2);
        s.setDuration(Duration.millis(1000));
        s.play();

    }

    public void updateBorder(Node node){
        ScaleTransition s = new ScaleTransition();
        s.setNode(node);
        s.setFromX(1.0);
        s.setFromY(1.0);
        s.setToX(0.9);
        s.setToY(0.9);
        s.setDuration(Duration.millis(1000));
        s.play();
    }

    public void colorizeHexagon(Polygon h, boolean t){
        if(t){
            h.setStroke(Color.web("#F08A26"));
        }
        else {
            h.setStroke(Color.web("#6C2466"));
        }
    }

}
