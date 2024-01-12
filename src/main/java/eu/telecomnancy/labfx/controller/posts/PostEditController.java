package eu.telecomnancy.labfx.controller.posts;


import eu.telecomnancy.labfx.controller.HexaSuper;
import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.DateUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


@Getter
@Setter
public class PostEditController extends HexaSuper {
    @FXML Pane hexagonPane;
    @FXML Polygon hexagon;
    @FXML public ToggleGroup type_post;
    @FXML public Label name_page;
    @FXML public TextField title;
    @FXML public TextArea description;
    @FXML public TextField streetNumber;
    @FXML public TextField region;
    @FXML public TextField street;
    @FXML public TextField postalCode;
    @FXML public TextField city;
    @FXML public ComboBox countryList;
    @FXML public ImageView image;
    @FXML public DatePicker datesPicker;
    @FXML private TextArea descriptionService;
    @FXML private TextField firstNamePrest;
    @FXML private TextField lastNamePrest;
    @FXML private TextField emailPrest;
    @FXML private TableView<Person> listPrest;
    @FXML private TableColumn<Person, String> firstNameColumn;
    @FXML private TableColumn<Person, String> lastNameColumn;
    @FXML private TableColumn<Person, String> emailColumn;
    @FXML public TextArea stateTool;
    @FXML public Label mode;
    @FXML private VBox listPost;
    @FXML private ToggleGroup toggle_type_date;
    @FXML private RadioButton plage;
    @FXML private RadioButton ponctuelles;
    @FXML private RadioButton seeMyAppliedPosts;
    @FXML private RadioButton seeMyPosts;
    @FXML private RadioButton sortedByCity;
    @FXML private RadioButton sortedByCountry;
    @FXML private RadioButton sortedByNote;
    @FXML private RadioButton sortedByRegion;
    @FXML private RadioButton sortedByStateCurrent;
    @FXML private RadioButton sortedByStateEnded;
    @FXML private RadioButton sortedByStateFuture;
    @FXML private RadioButton sortedByUser;
    @FXML private RadioButton onlyServices;
    @FXML private RadioButton onlyTools;
    @FXML private RadioButton allPost;
    @FXML private ToggleGroup choiceLocation;
    @FXML private ToggleGroup choiceMyPostedApplied;
    @FXML private ToggleGroup choiceOthers;
    @FXML private ToggleGroup choicePost;
    @FXML private ToggleGroup choiceState;

    private Post post;
    private boolean part2 = false;
    private boolean modify;
    private final ObservableList<String> countries = FXCollections.observableArrayList();
    final FileChooser fileChooser = new FileChooser();
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ArrayList<Post> posts;
    private MyDatePicker myDatePicker;
    public final ObservableList<LocalDate> dates = FXCollections.observableArrayList();
    public ListView<LocalDate> listDate;
    private ArrayList<LocalDate> datesList = new ArrayList<>();
    private ArrayList<User> users = JsonUtil.jsonToUsers();
    public Polygon getHexagon() {
        return hexagon;
    }

    @FXML
    void initialize() {
        posts = JsonUtil.jsonToPosts();
        if (countryList != null){
            countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
            countryList.setItems(countries);
            countryList.setValue("France");
        }
        if (firstNameColumn != null && lastNameColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        }

        if (listPost != null){
            posts = JsonUtil.jsonToPosts();
            SceneController sceneController = new SceneController();
            sceneController.goToRowPost(posts, listPost);
        }
    }

    public void initData(Post post) {
        posts = JsonUtil.jsonToPosts();
        if (posts == null)
            posts = new ArrayList<>();

        if (toggle_type_date != null){
            if (post != null){
                dates.addAll(post.getDates());
            }
            myDatePicker = new MyDatePicker(datesPicker, this);
        }

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
            streetNumber.setText(String.valueOf(post.getAddress().getStreetNumber()));
            street.setText(post.getAddress().getStreetName());
            postalCode.setText(String.valueOf(post.getAddress().getPostalCode()));
            city.setText(post.getAddress().getCity());
            region.setText(post.getAddress().getRegion());
            countryList.setValue(post.getAddress().getCountry());
            type_post.selectToggle(post instanceof Service ? type_post.getToggles().get(0) : type_post.getToggles().get(1));
            listDate.setItems(dates);
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
        if (title.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide").showAndWait();
        }
        else if (description.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        }
        else if (dates.size() != 2 && !ponctuelles.isSelected()){
            new Alert(Alert.AlertType.ERROR, "Pour une plage, il faut 2 dates").showAndWait();
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
            User author = null;
            for (User user : users){
                if (user.isConnected()){
                    author = user;
                    break;
                }
            }

            Address address = new Address(Integer.parseInt(streetNumber.getText()), street.getText(), Integer.parseInt(postalCode.getText()), city.getText(), region.getText(), countryList.getValue().toString());
            SceneController sceneController = new SceneController();
                State state = State.EN_COURS;
                if (myDatePicker.getStart().isAfter(LocalDate.now()))
                    state = State.FUTUR;

                if (myDatePicker.getStart().isAfter(LocalDate.now()) && myDatePicker.getEnd().isBefore(LocalDate.now()))
                    state = State.EN_COURS;

                RadioButton date = (RadioButton) toggle_type_date.getSelectedToggle();

                Type_Date type_date;

                if (date.getId().equals("plage")) {
                    type_date = Type_Date.PLAGE;
                    LocalDate boucle = myDatePicker.getStart();
                    dates.add(boucle);
                    datesList.add(boucle);
                    while (boucle.isBefore(myDatePicker.getEnd())) {
                        boucle = boucle.plusDays(1);
                        dates.add(boucle);
                        datesList.add(boucle);
                    }
                } else if (date.getId().equals("ponctuelles")) {
                    type_date = Type_Date.PONCTUELLES;
                    dates.addAll(myDatePicker.getSelectedDates());
                    datesList.addAll(myDatePicker.getSelectedDates());
                } else {
                    type_date = Type_Date.PONCTUELLE_REC;
                }

            RadioButton selected = (RadioButton) type_post.getSelectedToggle();
            if (selected.getText().equals("Service")){
                if (!modify) {
                    post = new Service(description.getText(), title.getText(), author.getEmail(), datesList, new ArrayList<>(), type_date, address, image.getImage(), state, null, null);
                    author.getPostedPosts().add(post.getIdPost());
                }
                else {
                    modifierPost(datesList, author, address, state);
                }
                sceneController.goToEditService(event, post, modify);
            }
            else {
                if (!modify){
                    post = new Tool(description.getText(), title.getText(), author.getEmail(), datesList, new ArrayList<>(), type_date, address, image.getImage(), state, null, null);
                    author.getPostedPosts().add(post.getIdPost());
                }
                else {
                    modifierPost(datesList, author, address, state);
                }
                sceneController.goToEditTool(event, post, modify);
            }
        }
    }

    private void modifierPost(ArrayList<LocalDate> dates, User user, Address address, State state) {
        post.setDescription(description.getText());
        post.setTitle(title.getText());
        post.setAuthorEmail(user.getEmail());
        post.setDates(dates);
        post.setAddress(address);
        post.setImage(image.getImage());
        post.setState(state);
    }

    public void add() {
        if (lastNamePrest.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le nom ne peut pas être vide").showAndWait();
        }
        else if (firstNamePrest.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le prénom ne peut pas être vide").showAndWait();
        }
        else if (emailPrest.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Le mail ne peut pas être vide").showAndWait();
        }
        else {
            for (User user : users){
                if (user.getEmail().equals(emailPrest.getText())){
                    personData.add(user);
                    listPrest.setItems(personData);
                    lastNamePrest.clear();
                    firstNamePrest.clear();
                    emailPrest.clear();
                    return;
                }
            }
            personData.add(new ExternalActor(firstNamePrest.getText(), lastNamePrest.getText(), emailPrest.getText()));
            listPrest.setItems(personData);
            lastNamePrest.clear();
            firstNamePrest.clear();
            emailPrest.clear();
        }
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
            users = JsonUtil.jsonToUsers();
            for (User user : users){
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
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
            users = JsonUtil.jsonToUsers();
            for (User user : users){
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
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
        sceneController.goToCreatePost(event);
    }

    public void validateNew(ActionEvent event) throws IOException {if (title.getText().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide").showAndWait();
    }
    else if (description.getText().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
    }
    else if (dates.size() != 2 && !ponctuelles.isSelected()){
        new Alert(Alert.AlertType.ERROR, "Pour une plage, il faut 2 dates").showAndWait();
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
        User author = null;
        for (User user : users){
            if (user.isConnected()){
                author = user;
                break;
            }
        }

        Address address = new Address(Integer.parseInt(streetNumber.getText()), street.getText(), Integer.parseInt(postalCode.getText()), city.getText(), region.getText(), countryList.getValue().toString());
        SceneController sceneController = new SceneController();

        State state = State.EN_COURS;
        if (myDatePicker.getStart().isAfter(LocalDate.now()))
            state = State.FUTUR;

        if (myDatePicker.getStart().isAfter(LocalDate.now()) && myDatePicker.getEnd().isBefore(LocalDate.now()))
            state = State.EN_COURS;

        RadioButton date = (RadioButton) toggle_type_date.getSelectedToggle();

        Type_Date type_date;

        if (date.getId().equals("plage")){
            type_date = Type_Date.PLAGE;
            LocalDate boucle = myDatePicker.getStart();
            dates.add(boucle);
            datesList.add(boucle);
            while (boucle.isBefore(myDatePicker.getEnd())){
                boucle = boucle.plusDays(1);
                dates.add(boucle);
                datesList.add(boucle);
            }
        } else if (date.getId().equals("ponctuelles")){
            type_date = Type_Date.PONCTUELLES;
            dates.addAll(myDatePicker.getSelectedDates());
            datesList.addAll(myDatePicker.getSelectedDates());
        }
        else {
            type_date = Type_Date.PONCTUELLE_REC;
        }

        RadioButton selected = (RadioButton) type_post.getSelectedToggle();
        if (selected.getText().equals("Service")){
            if (!modify){
                post = new Service(description.getText(), title.getText(), author.getEmail(), datesList, new ArrayList<>(), type_date, address, image.getImage(), state, null, null);
                author.getPostedPosts().add(post.getIdPost());
            }
            else {
                modifierPost(datesList, author, address, state);
            }
            sceneController.goToMainEdit(event,24, post, modify);

        }
        else {
            if (!modify){
                post = new Tool(description.getText(), title.getText(), author.getEmail(), datesList, new ArrayList<>(), type_date, address, image.getImage(), state, null, null);
                author.getPostedPosts().add(post.getIdPost());
            }
            else {
                modifierPost(datesList, author, address, state);
            }
            sceneController.goToMainEdit(event,21, post, modify);
        }
    }

    }
    public void validateToolPostHexa(ActionEvent event) throws IOException {
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
            users = JsonUtil.jsonToUsers();
            for (User user : users){
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
            posts.add(post);
            JsonUtil.postsToJson(posts);
            sceneController.goToMainValidate(event,23,post);
        }
    }
    public void validateServicePostHexa(ActionEvent event) throws IOException {
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
            users = JsonUtil.jsonToUsers();
            for (User user : users){
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
            posts.add(post);
            JsonUtil.postsToJson(posts);
            sceneController.goToMainValidate(event,25,post);
        }
    }



    public void logout(ActionEvent event) {
        for (User user : users){
            if (user.isConnected()){
                user.setConnected(false);
            }
        }

        JsonUtil.usersToJson(users);
        SceneController sceneController = new SceneController();
        sceneController.goToAccueil(event);
    }
}
