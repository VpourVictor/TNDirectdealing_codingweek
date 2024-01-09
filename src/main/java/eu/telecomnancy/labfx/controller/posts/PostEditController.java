package eu.telecomnancy.labfx.controller.posts;


import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Material;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


@Getter
@Setter
public class PostEditController {
    private Post post;
    private boolean part2 = false;
    private boolean modify;
    @FXML
    public ToggleGroup type_post;

    public Label name_page;

    public TextField title;

    public TextArea description;

    public TextField streetNumber;

    public TextField region;

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
    private TextArea descriptionService;

    @FXML
    private TextField firstNamePrest;

    @FXML
    private TextField lastNamePrest;

    @FXML
    private TableView<Person> listPrest;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    public TextArea stateTool;

    @FXML
    void initialize() {
        if (countryList != null){
            countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
            countryList.setItems(countries);
        }
        if (firstNameColumn != null && lastNameColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        }

        if (name_page != null) {
            System.out.println(modify);
            if(!isModify())
                name_page.setText("Création d'un nouveau post");
            else
                name_page.setText("Modification d'un post");
        }
    }

    public void initData(Post post) {
        this.post = post;
        if (post != null && !part2) {
            title.setText(post.getTitle());
            description.setText(post.getDescription());
            image.setImage(post.getImage());
            dateStart.setValue(post.getDateCouple().getDateStart());
            dateEnd.setValue(post.getDateCouple().getDateEnd());
            streetNumber.setText(String.valueOf(post.getAdress().getStreetNumber()));
            street.setText(post.getAdress().getStreetName());
            postalCode.setText(String.valueOf(post.getAdress().getPostalCode()));
            city.setText(post.getAdress().getCity());
            region.setText(post.getAdress().getRegion());
            countryList.setValue(post.getAdress().getCountry());
        }
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
        LocalDate start, end = null;
        if (title.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide").showAndWait();
        }
        else if (description.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        }
        else if (image.getImage() == null) {
            new Alert(Alert.AlertType.ERROR, "L'image ne peut pas être vide").showAndWait();
        }
        else if (dateStart.getValue() != null && dateEnd.getValue() != null && dateStart.getValue().isAfter(dateEnd.getValue())){
            new Alert(Alert.AlertType.ERROR, "La date de début ne peut pas être après la date de fin").showAndWait();
        }
        else if (streetNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le numéro de rue ne peut pas être vide").showAndWait();
        }
        else if (!RegexUtils.isNumeric(streetNumber.getText())){
            new Alert(Alert.AlertType.ERROR, "Le numéro de rue doit être un nombre").showAndWait();
        }
        else if (street.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le nom de rue ne peut pas être vide").showAndWait();
        }
        else if (postalCode.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le code postal ne peut pas être vide").showAndWait();
        }
        else if (!RegexUtils.isNumeric(postalCode.getText())){
            new Alert(Alert.AlertType.ERROR, "Le code postal doit être un nombre").showAndWait();
        }
        else if (city.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "La ville ne peut pas être vide").showAndWait();
        }
        else if (region.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "La région ne peut pas être vide").showAndWait();
        }
        else if (countryList.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "Le pays ne peut pas être vide").showAndWait();
        }
        else {
            // todo créer le user en fonction de la personne connectée
            User user = new User("test", "test", "test");
            user.getPostedPosts().add(post);

            SceneController sceneController = new SceneController();

            RadioButton selected = (RadioButton) type_post.getSelectedToggle();

            Address address = new Address(Integer.parseInt(streetNumber.getText()), street.getText(),
                    Integer.parseInt(postalCode.getText()), city.getText(), region.getText(), countryList.getValue().toString());

            if (dateStart.getValue() == null)
                start = LocalDate.now();
            else
                start = dateStart.getValue();

            State state = null;
            if (dateEnd.getValue() != null) {
                end = dateEnd.getValue();
                if (start.isAfter(LocalDate.now()))
                    state = State.FUTUR;

                if (start.isAfter(LocalDate.now()) && end.isBefore(LocalDate.now()))
                    state = State.EN_COURS;

                if (end.isBefore(LocalDate.now()))
                    state = State.TERMINE;
            }

            if (selected.getText().equals("Service")){
                post = new Service(description.getText(), title.getText(), user, start, end, address, image.getImage(), state, null);
                sceneController.goToEditService(event, post);
            }
            else {
                post = new Tool(description.getText(), title.getText(), user, start, end, address, image.getImage(), state, null);
                sceneController.goToEditTool(event, post);
            }
        }
    }

    public void add() {
        // todo récupérer la liste des personnes inscrites dans le JSON
        // si personne dont nom et prénom sont ceux rentrés dans les champs
        // personData.add(new User(firstNamePrest.getText(), lastNamePrest.getText()));
        // sinon
        personData.add(new ExternalActor(firstNamePrest.getText(), lastNamePrest.getText()));
        listPrest.setItems(personData);
        lastNamePrest.clear();
        firstNamePrest.clear();
    }

    public void validateServicePost(ActionEvent event) {
        if (personData.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Vous devez ajouter au moins un prestataire").showAndWait();
        }
        else if (descriptionService.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        }
        else {
            SceneController sceneController = new SceneController();
            post = new Service(post, descriptionService.getText(), personData);
            sceneController.goToOverviewServicePost(event, post);
        }
    }

    public void validateToolPost(ActionEvent event) {
        if (stateTool.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "L'état ne peut pas être vide").showAndWait();
        }
        else {
            SceneController sceneController = new SceneController();
            post = new Tool(post, stateTool.getText());
            sceneController.goToOverviewToolPost(event, post);
        }
    }

    public void back(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToEditPost(event, post, false);
    }
}
