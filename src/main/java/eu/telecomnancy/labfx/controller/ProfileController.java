package eu.telecomnancy.labfx.controller;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.User;
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
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class ProfileController extends HexaSuper{
    private boolean show;

    private User user;
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
    private Text argent;
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
    private Text mailText;
    @FXML
    private Pane panePhoto = new Pane();
    @FXML
    private Button boutonParcours;
    @FXML
    private Circle cercle1 = new Circle();
    @FXML
    private Circle cercle2 = new Circle();
    @FXML
    private Circle cercle3 = new Circle();
    @FXML
    private Circle cercle4 = new Circle();
    @FXML
    private Circle cercle5 = new Circle();
    @FXML
    private Text nb_eval;

    @FXML
    private Circle photoprofil;
    @FXML
    private TextField cheminPhoto = new TextField();
    public void switchToRecompense(){};

    public void showMdp(){
        show = !show;
        if (show){
            mdpText.setText(user.getPassword());
        }
        else{
            mdpText.setText("*".repeat(user.getPassword().length()));
        }
    };

    public void load(){
        show = false;
        nomText.setText(user.getLastName());
        argent.setText("" + user.getCoins());
        prenomText.setText(user.getFirstName());
        mdpText.setText("*".repeat(user.getPassword().length()));
        pseudoText.setText((user.getPseudo()));
        mailText.setText((user.getEmail()));
        photoprofil.setFill(new ImagePattern(user.getProfilePicture()));
        panePhoto.setVisible(false);
        long note = user.getEvaluation();
        int nb_notes = user.getNumberOfEvaluations();
        nb_eval.setText("(" + nb_notes + ")");
        if (note >= 1) {
            cercle1.getStyleClass().add("valide");
        }
        else{
            cercle1.getStyleClass().add("non-valide");
        }
        if (note >= 2) {
            cercle2.getStyleClass().add("valide");
        }
        else{
            cercle2.getStyleClass().add("non-valide");
        }
        if (note >= 3) {
            cercle3.getStyleClass().add("valide");
        }
        else{
            cercle3.getStyleClass().add("non-valide");
        }
        if (note >= 4) {
            cercle4.getStyleClass().add("valide");
        }
        else{
            cercle4.getStyleClass().add("non-valide");
        }
        if (note >= 5) {
            cercle5.getStyleClass().add("valide");
        }
        else{
            cercle5.getStyleClass().add("non-valide");
        }

    }
    public void switchEditMode(){
        String current = boutonEdit.textProperty().get();
        if (current.intern() == "Editer"){
            pseudoEdit.setText(pseudoText.getText());
            mdpEdit.setText(user.getPassword());
            prenomEdit.setText(prenomText.getText());
            nomEdit.setText(nomText.getText());
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

            mdpText.textProperty().set("*".repeat(mdpEdit.getText().length()));
            pseudoText.textProperty().set(pseudoEdit.getText());
            nomText.textProperty().set(nomEdit.getText());
            prenomText.textProperty().set(prenomEdit.getText());
            if (!cheminPhoto.getText().isEmpty() || !cheminPhoto.getText().isBlank()) {
                user.setProfilePicture(new Image(cheminPhoto.getText()));
            }

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
            photoprofil.setFill(new ImagePattern(user.getProfilePicture()));

            user.setPassword(mdpEdit.getText());
            user.setPseudo(pseudoEdit.getText());
            user.setFirstName(prenomEdit.getText());
            user.setLastName(nomEdit.getText());
            JsonUtil.changeUserInJson(user);
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

    public Polygon getHexagon() {
        return hexagon;
    }
}
