package eu.telecomnancy.labfx.controller.posts;


import eu.telecomnancy.labfx.controller.HexaSuper;
import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Getter
@Setter
public class PostEditController extends HexaSuper {
    private Post post;

    private boolean part2 = false;
    private boolean modify;

    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;


    public Polygon getHexagon() {
        return hexagon;
    }

    @FXML
    public ToggleGroup type_post;

    @FXML
    public Label name_page;

    @FXML
    public TextField title;

    @FXML
    public TextArea description;

    @FXML
    public TextField streetNumber;

    @FXML
    public TextField region;

    @FXML
    public TextField street;

    @FXML
    public TextField postalCode;

    @FXML
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
    public Label mode;

    private ArrayList<Post> posts;

    @FXML private GridPane grid;

    @FXML
    void initialize() {
        if (countryList != null){
            countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
            countryList.setItems(countries);
            countryList.setValue("France");
        }
        if (firstNameColumn != null && lastNameColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        }
    }

    public void initData(Post post) {
        posts = JsonUtil.jsonToPosts();
        if (posts == null)
            posts = new ArrayList<>();

        if (name_page != null) {
            if(!modify)
                name_page.setText("Création d'un nouveau post");
            else
                name_page.setText("Modification d'un post");
        }

        this.post = post;
        if (post != null && !part2) {
            title.setText(post.getTitle());
            description.setText(post.getDescription());
            image.setImage(post.getImage());
            dateStart.setValue(post.getDateCouple().getDateStart());
            dateEnd.setValue(post.getDateCouple().getDateEnd());
            streetNumber.setText(String.valueOf(post.getAddress().getStreetNumber()));
            street.setText(post.getAddress().getStreetName());
            postalCode.setText(String.valueOf(post.getAddress().getPostalCode()));
            city.setText(post.getAddress().getCity());
            region.setText(post.getAddress().getRegion());
            countryList.setValue(post.getAddress().getCountry());
            type_post.selectToggle(post instanceof Service ? type_post.getToggles().get(0) : type_post.getToggles().get(1));
        }

        if (post != null && part2) {
            if (post instanceof Service) {
                if (modify && descriptionService != null){
                    descriptionService.setText(post.getDescriptionService());
                    personData.addAll(post.getProviders());
                    listPrest.setItems(personData);
                }

            } else if (post instanceof Tool) {
                if (modify && stateTool != null)
                    stateTool.setText(post.getStateTool());
            }

            if(!modify)
                mode.setText("Création d'un nouveau post");
            else
                mode.setText("Modification d'un post");
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

    public void validatePart1(ActionEvent event) {
        LocalDate start, end = null;
        if (title.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide").showAndWait();
        }
        else if (description.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
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
            User user = new User("test", "test");
            Address address = new Address(Integer.parseInt(streetNumber.getText()), street.getText(), Integer.parseInt(postalCode.getText()), city.getText(), region.getText(), countryList.getValue().toString());
            user.getPostedPosts().add(post);
            user.setAddress(address);
            user.setPseudo("test");
            user.setPassword("test");
            user.setEmail("dsldmlsk@dksjfslk.fr");


            SceneController sceneController = new SceneController();

            RadioButton selected = (RadioButton) type_post.getSelectedToggle();


            if (dateStart.getValue() == null)
                start = LocalDate.now();
            else
                start = dateStart.getValue();

            State state = State.EN_COURS;
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
                if (!modify){
                    post = new Service(description.getText(), title.getText(), user.getEmail(), start, end, address, image.getImage(), state, null);
                }
                else {
                    modifierPost(start, end, user, address, state);
                }
                sceneController.goToEditService(event, post, modify);
            }
            else {
                if (!modify){
                    post = new Tool(description.getText(), title.getText(), user.getEmail(), start, end, address, image.getImage(), state, null);
                }
                else {
                    modifierPost(start, end, user, address, state);
                }
                sceneController.goToEditTool(event, post, modify);
            }
        }
    }

    private void modifierPost(LocalDate start, LocalDate end, User user, Address address, State state) {
        post.setDescription(description.getText());
        post.setTitle(title.getText());
        post.setAuthorEmail(user.getEmail());
        post.setDateCouple(new DateCouple(start, end));
        post.setAddress(address);
        post.setImage(image.getImage());
        post.setState(state);
    }

    public void add() {
        // todo récupérer la liste des personnes inscrites dans le JSON
        // si personne dont nom et prénom sont ceux rentrés dans les champs
        // personData.add(new User(firstNamePrest.getText(), lastNamePrest.getText()));
        personData.add(new ExternalActor(firstNamePrest.getText(), lastNamePrest.getText()));
        listPrest.setItems(personData);
        lastNamePrest.clear();
        firstNamePrest.clear();
    }

    public void validateServicePost(ActionEvent event) throws IOException {
        if (personData.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Vous devez ajouter au moins un prestataire").showAndWait();
        }
        else if (descriptionService.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        }
        else {
            SceneController sceneController = new SceneController();
            if (!modify){
                post = new Service(post, descriptionService.getText(), personData);
            }
            else{
                if (post.getClass().equals(Tool.class)){
                    post = new Service(post, descriptionService.getText(), personData);
                    post.setIdPost(post.getIdPost() - 1);
                    Post.setNbPosts(Post.getNbPosts() - 1);
                }
                else {
                    post.setDescriptionService(descriptionService.getText());
                    post.setProviders(personData);
                }
            }
            posts.add(post);
            JsonUtil.postsToJson(posts);
            sceneController.goToOverviewServicePost(event, post);
        }
    }

    public void validateToolPost(ActionEvent event) throws IOException {
        if (stateTool.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "L'état ne peut pas être vide").showAndWait();
        }
        else {
            SceneController sceneController = new SceneController();
            if (!modify)
                post = new Tool(post, stateTool.getText());
            else{
                if (post.getClass().equals(Service.class)){
                    post = new Tool(post, stateTool.getText());
                    post.setIdPost(post.getIdPost() - 1);
                    Post.setNbPosts(Post.getNbPosts() - 1);
                }
                else {
                    post.setStateTool(stateTool.getText());
                }
            }
            posts.add(post);
            JsonUtil.postsToJson(posts);
            sceneController.goToOverviewToolPost(event, post);
        }
    }

    public void back(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToEditPost(event, post, modify);
    }

    public void back_to_view(ActionEvent event) {
        SceneController sceneController = new SceneController();
        if (post != null && (post.getDescriptionService() != null || post.getStateTool() != null))
            sceneController.goToOverviewServicePost(event, post);
        else
            sceneController.goToAllPosts(event, posts);
    }

    public void newPost(ActionEvent event) {
        SceneController sceneController = new SceneController();
        // Post.setNbPosts(Post.getNbPosts() + 1);
        sceneController.goToCreatePost(event);
    }
}
