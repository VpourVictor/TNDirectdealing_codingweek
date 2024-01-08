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

public class ProfileController {
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
    private Button boutonAccueil;
    @FXML
    private Button boutonRec;
    @FXML
    private Pane panePhoto;
    @FXML
    private Button boutonParcours;
    @FXML
    private TextField cheminPhoto;
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
            boutonRec.setVisible(false);
            boutonAccueil.setVisible(false);
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

            if (!cheminPhoto.getText().isEmpty()){
                try {
                    Image image = new Image(cheminPhoto.getText());
                    photoprofil.setFill(new ImagePattern(image));
                    fig1.setVisible(false);
                    fig2.setVisible(false);
                }
                catch (Exception e){e.printStackTrace();}

            }
            boutonEdit.textProperty().set("Editer");
            boutonRec.setVisible(true);
            boutonAccueil.setVisible(true);
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

    public void uploadPhoto(ActionEvent event) throws IOException {
        fc.setTitle("My file chooser");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fc.showOpenDialog(null);
        if(file != null){
            cheminPhoto.setText(file.toURI().toString());
        }
    }

    public void updateBorder(){
        hexagon.setStroke(Color.web("#F08A26"));
    }
}
