package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.posts.PostEditController;
import eu.telecomnancy.labfx.controller.posts.PostOverviewController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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



    public  static Parent root;

    public static int offX;

    public static int offY;

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

    Polygon currentHexagon;

    Pane currentPane;
    @FXML
    Polygon testPane2;
    @FXML
    Polygon testPane3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moving = false;
        //position = 0;
        try {
            //loadHexagon();
            updateNewHexa(paneCenter);
            updateHexagon();
            loadPosition();
            System.out.println(position);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRoot(Parent root) {
        MainController.root = root;
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
            updatePosition("UP_LEFT");
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
            updatePosition("UP_RIGHT");
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
            updatePosition("DOWN_LEFT");
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
            updatePosition("DOWN_RIGHT");
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
            updatePosition("LEFT");
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
            updatePosition("RIGHT");
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


        paneCenter.getChildren().setAll(paneC);
        paneLeft.getChildren().setAll(paneL);
        paneRight.getChildren().setAll(paneR);
        paneUpLeft.getChildren().setAll(paneUL);
        paneUpRight.getChildren().setAll(paneUR);
        paneDownLeft.getChildren().setAll(paneDL);
        paneDownRight.getChildren().setAll(paneDR);

        updateHexagon();
        currentHexagon = controller.getHexagon();

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


/*    public void loadPosition() throws IOException {
        if (position == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonHomepage.fxml"));
            Pane pane = loader.load();
            HomepageController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            currentPane = controller.getPaneTest();
            paneCenter.getChildren().setAll(pane);
        }
        else if (position == 1){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexaProfile.fxml"));
            Pane pane = loader.load();
            ProfileController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            paneCenter.getChildren().setAll(pane);
        }
        else if(position == 14){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonBoard.fxml"));
            Pane pane = loader.load();
            HexagonBoardController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            paneCenter.getChildren().setAll(pane);


        }
        if (position == 10){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonSignIn.fxml"));
            Pane pane = loader.load();
            SignInController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            paneCenter.getChildren().setAll(pane);
        }
        else if (position == 13){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonSignUp.fxml"));
            Pane pane = loader.load();
            SignUpController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            paneCenter.getChildren().setAll(pane);
        }
        else if (position == 7){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HexagonDirectDealing.fxml"));
            Pane pane = loader.load();
            HexagonDirectDealingController controller = loader.getController();
            colorizeHexagon(controller.getHexagon(),true);
            currentHexagon = controller.getHexagon();
            paneCenter.getChildren().setAll(pane);
        }



    }*/
    public void loadPosition() throws IOException {
        String fxmlPath = "";
        Class<?> controllerType = null;

        switch (position) {
            case 0:
                fxmlPath = "/HexagonHomepage.fxml";
                controllerType = HomepageController.class;
                break;
            case 15:
                fxmlPath = "/HexaProfile.fxml";
                controllerType = ProfileController.class;
                break;
            case 14:
                fxmlPath = "/HexagonBoard.fxml";
                controllerType = HexagonBoardController.class;
                break;
            case 10:
                fxmlPath = "/HexagonSignIn.fxml";
                controllerType = SignInController.class;
                break;
            case 13:
                fxmlPath = "/HexagonSignUp.fxml";
                controllerType = SignUpController.class;
                break;
            case 7:
                fxmlPath = "/HexagonDirectDealing.fxml";
                controllerType = HexagonDirectDealingController.class;
                break;
            case 16:
                fxmlPath = "/HexaTemplate.fxml";
                controllerType = HexaTemplateController.class;
                break;
            case 20:
                fxmlPath = "/HexagonMessagerie.fxml";
                controllerType = MessagerieController.class;
                break;
            case 23:
                fxmlPath = "/HexagonEditService.fxml";
                controllerType = PostEditController.class;
                break;
            case 21:
                fxmlPath = "/HexagonEditTool.fxml";
                controllerType = PostEditController.class;
                break;
            case 25:
                fxmlPath = "/HexagonOverviewServicePost.fxml";
                controllerType = PostOverviewController.class;
                break;
            case 24:
                fxmlPath = "/HexagonOverviewToolPost.fxml";
                controllerType = PostOverviewController.class;
                break;
            default:
                break;
        }

        if (!fxmlPath.isEmpty() && controllerType != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane pane = loader.load();
            HexaSuper controller = loader.getController();
            colorizeHexagon(controller.getHexagon(), true);
            if(controller instanceof HomepageController){
                currentPane = ((HomepageController) controller).getPaneTest();
            }
            currentHexagon = controller.getHexagon();
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





    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void teleportation(int position) throws IOException {
        switch (position) {
            case 0 -> loadFXML("/HexagonHomepage.fxml", HomepageController.class);
            case 15 -> loadFXML("/HexaProfile.fxml", ProfileController.class);
            case 14 -> loadFXML("/HexagonBoard.fxml", HexagonBoardController.class);
            case 10 -> loadFXML("/HexagonSignIn.fxml", SignInController.class);
            case 13 -> loadFXML("/HexagonSignUp.fxml", SignUpController.class);
            case 7 -> loadFXML("/HexagonDirectDealing.fxml", HexagonDirectDealingController.class);
            case 16 -> loadFXML("/HexaTemplate.fxml", HexaTemplateController.class);
            case 20 -> loadFXML("/HexagonMessagerie.fxml", MessagerieController.class);
            case 23 -> loadFXML("/HexagonEditService.fxml", PostEditController.class);
            case 21 -> loadFXML("/HexagonEditTool.fxml", PostEditController.class);
            case 25 -> loadFXML("/HexagonOverviewServicePost.fxml", PostOverviewController.class);
            case 24 -> loadFXML("/HexagonOverviewToolPost.fxml", PostOverviewController.class);
        }
    }



    private void loadFXML(String fxmlPath, Class<?> controllerType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Pane pane = loader.load();
        HexaSuper controller = loader.getController();

        if (controllerType.isInstance(controller)) {
            controller.getHexagon().setFill(Color.web("#F08A26"));
            colorizeHexagon(controller.getHexagon(),true);

            ParallelTransition pt = new ParallelTransition();

            ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(700), paneCenter);
            scaleTransition1.setToX(0.01);
            scaleTransition1.setToY(0.01);

            currentPane.setVisible(false);

            KeyValue keyValue = new KeyValue(currentHexagon.fillProperty(), Color.web("#F08A26"));
            KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);

            Timeline timeline = new Timeline(keyFrame);
            //timeline.setOnFinished(event -> paneCenter.getChildren().add(currentHexagon));
            scaleTransition1.setOnFinished(event -> paneCenter.getChildren().setAll(pane));

            pt.getChildren().setAll(timeline,scaleTransition1);

            ParallelTransition pt2 = new ParallelTransition();

            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(1100), paneCenter);
            scaleTransition2.setToX(1.2);
            scaleTransition2.setToY(1.2);


            KeyValue keyValue2 = new KeyValue(controller.getHexagon().fillProperty(), Color.WHITE);
            KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), keyValue2);

            Timeline timeline2 = new Timeline(keyFrame2);

            pt2.getChildren().setAll(timeline2,scaleTransition2);



            SequentialTransition colorTransition = new SequentialTransition(pt, pt2);
            colorTransition.play();
        }
    }

    public void setPaneCenter(Pane paneCenter) {
        this.paneCenter = paneCenter;
    }

    private void updatePosition(String direction) {
        if (position == 0) {
            if (direction.equals("UP_LEFT")) {
                position = 1;
            } else if (direction.equals("UP_RIGHT")) {
                position = 2;
            } else if (direction.equals("RIGHT")) {
                position = 3;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 4;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 5;
            } else if (direction.equals("LEFT")) {
                position = 6;
            }
            else {
                position = 0;
            }
        } else if (position == 1) {
            if (direction.equals("UP_LEFT")) {
                position = 0;
            } else if (direction.equals("UP_RIGHT")) {
                position = 0;
            } else if (direction.equals("RIGHT")) {
                position = 2;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 6;
            } else if (direction.equals("LEFT")) {
                position = 0;
            }
            else {
                position = 0;
            }
        } else if (position == 2) {
            if (direction.equals("UP_LEFT")) {
                position = 0;
            } else if (direction.equals("UP_RIGHT")) {
                position = 0;
            } else if (direction.equals("RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 3;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 0;
            } else if (direction.equals("LEFT")) {
                position = 1;
            }
            else {
                position = 0;
            }
        } else if (position == 3) {
            if (direction.equals("UP_LEFT")) {
                position = 2;
            } else if (direction.equals("UP_RIGHT")) {
                position = 0;
            } else if (direction.equals("RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 4;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 0;
            } else if (direction.equals("LEFT")) {
                position = 0;
            }
            else {
                position = 0;
            }
        } else if (position == 4) {
            if (direction.equals("UP_LEFT")) {
                position = 0;
            } else if (direction.equals("UP_RIGHT")) {
                position = 3;
            } else if (direction.equals("RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 0;
            } else if (direction.equals("LEFT")) {
                position = 5;
            }
            else {
                position = 0;
            }
        } else if (position == 5) {
            if (direction.equals("UP_LEFT")) {
                position = 0;
            } else if (direction.equals("UP_RIGHT")) {
                position = 6;
            } else if (direction.equals("RIGHT")) {
                position = 4;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 0;
            } else if (direction.equals("LEFT")) {
                position = 0;
            }
            else {
                position = 0;
            }
        } else if (position == 6) {
            if (direction.equals("UP_LEFT")) {
                position = 0;
            } else if (direction.equals("UP_RIGHT")) {
                position = 1;
            } else if (direction.equals("RIGHT")) {
                position = 0;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 5;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 0;
            } else if (direction.equals("LEFT")) {
                position = 0;
            }
            else {
                position = 0;
            }
        }
        else if (position == 7) {
            if (direction.equals("UP_LEFT")) {
                position = 8;
            } else if (direction.equals("UP_RIGHT")) {
                position = 9;
            } else if (direction.equals("RIGHT")) {
                position = 10;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 11;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 12;
            } else if (direction.equals("LEFT")) {
                position = 13;
            }
            else {
                position = 7;
            }
        } else if (position == 8) {
            if (direction.equals("UP_LEFT")) {
                position = 7;
            } else if (direction.equals("UP_RIGHT")) {
                position = 7;
            } else if (direction.equals("RIGHT")) {
                position = 9;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 13;
            } else if (direction.equals("LEFT")) {
                position = 7;
            }
            else {
                position = 7;
            }
        } else if (position == 9) {
            if (direction.equals("UP_LEFT")) {
                position = 7;
            } else if (direction.equals("UP_RIGHT")) {
                position = 7;
            } else if (direction.equals("RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 10;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 7;
            } else if (direction.equals("LEFT")) {
                position = 8;
            }
            else {
                position = 7;
            }
        } else if (position == 10) {
            if (direction.equals("UP_LEFT")) {
                position = 9;
            } else if (direction.equals("UP_RIGHT")) {
                position = 7;
            } else if (direction.equals("RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 11;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 7;
            } else if (direction.equals("LEFT")) {
                position = 7;
            }
            else {
                position = 7;
            }
        } else if (position == 11) {
            if (direction.equals("UP_LEFT")) {
                position = 7;
            } else if (direction.equals("UP_RIGHT")) {
                position = 10;
            } else if (direction.equals("RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 7;
            } else if (direction.equals("LEFT")) {
                position = 12;
            }
            else {
                position = 7;
            }
        } else if (position == 12) {
            if (direction.equals("UP_LEFT")) {
                position = 7;
            } else if (direction.equals("UP_RIGHT")) {
                position = 13;
            } else if (direction.equals("RIGHT")) {
                position = 11;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 7;
            } else if (direction.equals("LEFT")) {
                position = 7;
            }
            else {
                position = 7;
            }
        } else if (position == 13) {
            if (direction.equals("UP_LEFT")) {
                position = 7;
            } else if (direction.equals("UP_RIGHT")) {
                position = 8;
            } else if (direction.equals("RIGHT")) {
                position = 7;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 12;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 7;
            } else if (direction.equals("LEFT")) {
                position = 7;
            }
            else {
                position = 7;
            }
        }
        else if (position == 14) {
            if (direction.equals("UP_LEFT")) {
                position = 15;
            } else if (direction.equals("UP_RIGHT")) {
                position = 16;
            } else if (direction.equals("RIGHT")) {
                position = 17;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 18;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 19;
            } else if (direction.equals("LEFT")) {
                position = 20;
            }
            else {
                position = 14;
            }
        } else if (position == 15) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 14;
            } else if (direction.equals("RIGHT")) {
                position = 16;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 20;
            } else if (direction.equals("LEFT")) {
                position = 14;
            }
            else {
                position = 14;
            }
        } else if (position == 16) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 14;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 17;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 15;
            }
            else {
                position = 14;
            }
        } else if (position == 17) {
            if (direction.equals("UP_LEFT")) {
                position = 16;
            } else if (direction.equals("UP_RIGHT")) {
                position = 21;
            } else if (direction.equals("RIGHT")) {
                position = 22;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 23;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 14;
            }
            else {
                position = 14;
            }
        } else if (position == 18) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 17;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 19;
            }
            else {
                position = 14;
            }
        } else if (position == 19) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 20;
            } else if (direction.equals("RIGHT")) {
                position = 18;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 14;
            }
            else {
                position = 14;
            }
        } else if (position == 20) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 15;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 19;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 14;
            }
            else {
                position = 14;
            }
        } else if (position == 21) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 14;
            } else if (direction.equals("RIGHT")) {
                position = 24;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 22;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 17;
            } else if (direction.equals("LEFT")) {
                position = 14;
            }
            else {
                position = 14;
            }
        } else if (position == 22) {
            if (direction.equals("UP_LEFT")) {
                position = 21;
            } else if (direction.equals("UP_RIGHT")) {
                position = 24;
            } else if (direction.equals("RIGHT")) {
                position = 26;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 25;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 23;
            } else if (direction.equals("LEFT")) {
                position = 17;
            }
            else {
                position = 14;
            }
        } else if (position == 23) {
            if (direction.equals("UP_LEFT")) {
                position = 17;
            } else if (direction.equals("UP_RIGHT")) {
                position = 22;
            } else if (direction.equals("RIGHT")) {
                position = 25;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 18;
            }
            else {
                position = 14;
            }
        } else if (position == 24) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 14;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 26;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 22;
            } else if (direction.equals("LEFT")) {
                position = 21;
            }
            else {
                position = 14;
            }
        } else if (position == 25) {
            if (direction.equals("UP_LEFT")) {
                position = 22;
            } else if (direction.equals("UP_RIGHT")) {
                position = 26;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 23;
            }
            else {
                position = 14;
            }

        }else if (position == 26) {
            if (direction.equals("UP_LEFT")) {
                position = 14;
            } else if (direction.equals("UP_RIGHT")) {
                position = 14;
            } else if (direction.equals("RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_RIGHT")) {
                position = 14;
            } else if (direction.equals("DOWN_LEFT")) {
                position = 14;
            } else if (direction.equals("LEFT")) {
                position = 14;
            } else {
                position = 14;
            }

        }
    }

}
