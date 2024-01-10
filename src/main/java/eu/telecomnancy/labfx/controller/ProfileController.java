package eu.telecomnancy.labfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ProfileController extends HexaSuper{
    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;
    final FileChooser fc = new FileChooser();
    @FXML
    private Text pseudoText;
    @FXML
    private Text nomText;
    @FXML
    private Text prenomText;
    @FXML
    private Text mdpText;
    @FXML
    private TextField pseudoEdit;
    @FXML
    private TextField mdpEdit;
    @FXML
    private TextField nomEdit;
    @FXML
    private TextField prenomEdit;
    @FXML
    private Button boutonEdit;

    @FXML
    private Pane panePhoto;
    @FXML
    private Button boutonParcours;

    @FXML
    private Circle photoprofil;
    @FXML
    private QuadCurve fig2;
    @FXML
    private Circle fig1;

    public void switchToRecompense(){};

    public void goToAccueil(){};

    public void switchThemeMode(){};

    public void switchEditMode(){
        String current = boutonEdit.textProperty().get();
        if (current.intern() == "Editer"){
            boutonEdit.textProperty().set("Sauvegarder");
            pseudoText.setVisible(false);
            mdpText.setVisible(false);
            nomText.setVisible(false);
            prenomText.setVisible(false);
            pseudoEdit.setVisible(true);
            mdpEdit.setVisible(true);
            nomEdit.setVisible(true);
            prenomEdit.setVisible(true);
            panePhoto.setVisible(true);
        }
        else{   /*Sauvegarde des modifs */
            //TODO ajouter modif dans le json

            mdpText.textProperty().set(mdpEdit.getText());
            pseudoText.textProperty().set(pseudoEdit.getText());
            nomText.textProperty().set(nomEdit.getText());
            prenomText.textProperty().set(prenomEdit.getText());

            boutonEdit.textProperty().set("Editer");
            pseudoText.setVisible(true);
            mdpText.setVisible(true);
            prenomText.setVisible(true);
            nomText.setVisible(true);
            pseudoEdit.setVisible(false);
            mdpEdit.setVisible(false);
            nomEdit.setVisible(false);
            prenomEdit.setVisible(false);
            panePhoto.setVisible(false);

        }
    };


    public Polygon getHexagon() {
        return hexagon;
    }
}
