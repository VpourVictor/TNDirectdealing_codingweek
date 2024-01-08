package eu.telecomnancy.labfx.controller.posts;


import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.model.Tool;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class PostEditController {
    private Post post;
    @FXML
    public ToggleGroup type_post;


    public TextField title;

    public TextArea description;

    public TextField streetNumber;


    public TextField street;


    public TextField postalCode;


    public TextField city;

    private final ObservableList<String> countries = FXCollections.observableArrayList();
    @FXML
    public ComboBox countryList;

    @FXML
    public ImageView image;
    final FileChooser fileChooser = new FileChooser();

    @FXML
    public DatePicker dateStart;

    @FXML
    public DatePicker dateEnd;

    @FXML
    void initialize() {
        countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
        countryList.setItems(countries);
    }

    public void browse(ActionEvent event) {
        fileChooser.setTitle("Choisir une image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            image.setImage(new Image(file.toURI().toString()));
        }
    }

    public void validatePart1(ActionEvent event) throws IOException {
        LocalDate start, end;
        if (title.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide").showAndWait();
        }
        else if (description.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        }
        else if (image.getImage() == null) {
            new Alert(Alert.AlertType.ERROR, "L'image ne peut pas être vide").showAndWait();
        }
        else if (dateStart.getValue() == null){
            start = LocalDate.now();
        }
        else if (dateEnd.getValue() == null){
            end = null;
        }
        else if (dateStart.getValue().isAfter(dateEnd.getValue())){
            new Alert(Alert.AlertType.ERROR, "La date de début ne peut pas être après la date de fin").showAndWait();
        }
        else if (streetNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le numéro de rue ne peut pas être vide").showAndWait();
        }
        else if (street.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le nom de rue ne peut pas être vide").showAndWait();
        }
        else if (postalCode.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le code postal ne peut pas être vide").showAndWait();
        }
        else if (city.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "La ville ne peut pas être vide").showAndWait();
        }
        else if (countryList.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Le pays ne peut pas être vide").showAndWait();
        }
        else {
            start = dateStart.getValue();
            end = dateEnd.getValue();

            // todo créer le user en fonction de la personne connectée

            SceneController sceneController = new SceneController();

            RadioButton selected = (RadioButton) type_post.getSelectedToggle();
            if (selected.getText().equals("Service")){
                post = new Service(description.getText(), title.getText(), null, start, end, streetNumber.getText() + " " + street.getText(), image.getImage(), null, null);
                sceneController.goToEditService(event, post);
            }
            else {
                post = new Tool(description.getText(), title.getText(), null, start, end, streetNumber.getText() + " " + street.getText(), image.getImage(), null, null);
                sceneController.goToEditTool(event, post);
            }
        }
    }

    public void initData(Post post) {
        if (post != null) {
            this.post = post;
            title.setText(post.getTitle());
            description.setText(post.getDescription());
            image.setImage(post.getImage());
            dateStart.setValue(post.getDateCouple().getDateStart());
            dateEnd.setValue(post.getDateCouple().getDateEnd());
            streetNumber.setText(post.getAdress().split(" ")[0]);
            street.setText(post.getAdress().split(" ")[1]);
            postalCode.setText(post.getAdress().split(" ")[2]);
            city.setText(post.getAdress().split(" ")[3]);
            countryList.setValue(post.getAdress().split(" ")[4]);
        }
    }
}
