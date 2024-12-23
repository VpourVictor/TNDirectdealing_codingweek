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
import javafx.geometry.Pos;
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
    @FXML private TextField localisationTF;
    @FXML
    private ToggleGroup sensTool;
    @FXML
    private ToggleGroup sensService;


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
    void typeSelected(ActionEvent event) throws IOException {
        posts = JsonUtil.jsonToPosts();
        ArrayList<String> radioBtnState = new ArrayList<>(5);
        //0-> allPosts, 1-> choiceLocation, 2->choiceState, 3-> choiceOthers, 4-> choiceMyPostedApplied, 5-> Valeur du TFLocation
        RadioButton selected0 = (RadioButton) choicePost.getSelectedToggle();
        RadioButton selected1 = (RadioButton) choiceLocation.getSelectedToggle();
        RadioButton selected2 = (RadioButton) choiceState.getSelectedToggle();
        RadioButton selected3 = (RadioButton) choiceOthers.getSelectedToggle();
        RadioButton selected4 = (RadioButton) choiceMyPostedApplied.getSelectedToggle();

        if (selected0 != null){
            radioBtnState.add(selected0.getText());
        }
        else if (selected0 == null){
            radioBtnState.add(null);
        }
        if (selected1 != null){
            radioBtnState.add(selected1.getText());
        }
        else if (selected1 == null){
            radioBtnState.add(null);
        }
        if (selected2 != null){
            radioBtnState.add(selected2.getText());
        }
        else if (selected2 == null){
            radioBtnState.add(null);
        }
        if (selected3 != null){
            radioBtnState.add(selected3.getText());
        }
        else if (selected3 == null){
            radioBtnState.add(null);
        }
        if (selected4 != null){
            radioBtnState.add(selected4.getText());
        }
        else if (selected4 == null){
            radioBtnState.add(null);
        }
        radioBtnState.add(localisationTF.getText());
        SceneController sceneController = new SceneController();
        sceneController.goToAllPostsHexa(event, posts, radioBtnState);
    }

    @FXML
    public void clearSearch(ActionEvent event) throws IOException {
        sortedByCity.setSelected(false);
        sortedByCountry.setSelected(false);
        sortedByRegion.setSelected(false);
        sortedByStateCurrent.setSelected(false);
        sortedByStateEnded.setSelected(false);
        sortedByStateFuture.setSelected(false);
        sortedByNote.setSelected(false);
        sortedByUser.setSelected(false);
        allPost.setSelected(true);
        onlyServices.setSelected(false);
        onlyTools.setSelected(false);
        seeMyAppliedPosts.setSelected(false);
        seeMyPosts.setSelected(false);
        typeSelected(event);
    }

    public ArrayList<Post> stateChoice(ArrayList<String> stateRadioBtn, ArrayList<Post> results) {
        if (stateRadioBtn.get(2) == null  ){
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            sortedByStateCurrent.setSelected(false);
            sortedByStateEnded.setSelected(false);
            sortedByStateFuture.setSelected(false);
            return results;
        }
        else{
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            switch (stateRadioBtn.get(2)) {
                case "Futures":
                    sortedByStateCurrent.setSelected(false);
                    sortedByStateEnded.setSelected(false);
                    sortedByStateFuture.setSelected(true);
                    results = algoUtil.postInState(State.FUTUR);
                    return results;
                case "En cours":
                    sortedByStateCurrent.setSelected(true);
                    sortedByStateEnded.setSelected(false);
                    sortedByStateFuture.setSelected(false);
                    results = algoUtil.postInState(State.EN_COURS);
                    return results;
                case "Finies":
                    sortedByStateCurrent.setSelected(false);
                    sortedByStateEnded.setSelected(true);
                    sortedByStateFuture.setSelected(false);
                    results = algoUtil.postInState(State.TERMINE);
                    return results;
                default:
                    sortedByStateCurrent.setSelected(false);
                    sortedByStateEnded.setSelected(false);
                    sortedByStateFuture.setSelected(false);
                    return results;

            }
        }

    }

    public ArrayList<Post> locationChoice(ArrayList<String> stateRadioBtn, ArrayList<Post> results) {
        if (stateRadioBtn.get(1) == null  ){
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            sortedByCity.setSelected(false);
            sortedByCountry.setSelected(false);
            sortedByRegion.setSelected(false);
            return results;
        }
        else{
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            switch (stateRadioBtn.get(1)) {
                case "Ville":
                    sortedByCity.setSelected(true);
                    sortedByCountry.setSelected(false);
                    sortedByRegion.setSelected(false);
                    results = algoUtil.postSortedByCity(stateRadioBtn.get(5));
                    algoUtil = new AlgoUtil(users, results);
                    results = algoUtil.allExceptMasque();
                    return results;
                case "Pays":
                    sortedByCity.setSelected(false);
                    sortedByCountry.setSelected(true);
                    sortedByRegion.setSelected(false);
                    results = algoUtil.postSortedByCountry(stateRadioBtn.get(5));
                    algoUtil = new AlgoUtil(users, results);
                    results = algoUtil.allExceptMasque();
                    return results;
                case "Région":
                    sortedByCity.setSelected(false);
                    sortedByCountry.setSelected(false);
                    sortedByRegion.setSelected(true);
                    results = algoUtil.postSortedByRegion(stateRadioBtn.get(5));
                    algoUtil = new AlgoUtil(users, results);
                    results = algoUtil.allExceptMasque();
                    return results;
                default:
                    sortedByCity.setSelected(false);
                    sortedByCountry.setSelected(false);
                    sortedByRegion.setSelected(true);
                    results = algoUtil.postSortedByRegion(stateRadioBtn.get(5));
                    return results;

            }
        }

    }

    public ArrayList<Post> otherChoice(ArrayList<String> stateRadioBtn, ArrayList<Post> results) {
        if (stateRadioBtn.get(3) == null){
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            sortedByNote.setSelected(false);
            sortedByUser.setSelected(false);
            return results;
        }
        else {
            AlgoUtil algoUtil = new AlgoUtil(users, posts);
            switch (stateRadioBtn.get(3)) {
                case "Note":
                    sortedByNote.setSelected(true);
                    sortedByUser.setSelected(false);
                    results = algoUtil.postSortedByEvaluation();
                    return results;
                case "Utilisateur":
                    sortedByNote.setSelected(false);
                    sortedByUser.setSelected(true);
                    results = algoUtil.postSortedByUsers();
                    return results;
                default:
                    sortedByNote.setSelected(false);
                    sortedByUser.setSelected(false);
                    return results;
            }
        }
    }

    public ArrayList<Post> typeChoice(ArrayList<String> stateRadioBtn, ArrayList<Post> results) {
        if (stateRadioBtn.get(0) == null){
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            allPost.setSelected(true);
            onlyServices.setSelected(false);
            onlyTools.setSelected(false);
            return results;
        }
        else{
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            switch (stateRadioBtn.get(0)) {
                case "Toutes les annonces":
                    allPost.setSelected(true);
                    onlyServices.setSelected(false);
                    onlyTools.setSelected(false);
                    return results;
                case "Services":
                    allPost.setSelected(false);
                    onlyServices.setSelected(true);
                    onlyTools.setSelected(false);
                    results = algoUtil.postSortedByType("Service");
                    return results;
                case "Outils":
                    allPost.setSelected(false);
                    onlyServices.setSelected(false);
                    onlyTools.setSelected(true);
                    results = algoUtil.postSortedByType("tool");
                    return results;
                default:
                    allPost.setSelected(true);
                    onlyServices.setSelected(false);
                    onlyTools.setSelected(false);
                    return results;
            }
        }
    }

    public ArrayList<Post> choiceMyPostedApplied(ArrayList<String> stateRadioBtn, ArrayList<Post> results) {
        if (stateRadioBtn.get(4) == null){
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            seeMyAppliedPosts.setSelected(false);
            seeMyPosts.setSelected(false);
            return results;
        }
        else {
            AlgoUtil algoUtil = new AlgoUtil(users, results);
            User currentUser = algoUtil.userConnected(users);
            switch (stateRadioBtn.get(4)) {
                case "Voir mes annonces":
                    seeMyAppliedPosts.setSelected(false);
                    seeMyPosts.setSelected(true);
                    results = algoUtil.postFromUser(currentUser);
                    return results;
                case "Voir mes candidatures":
                    seeMyAppliedPosts.setSelected(true);
                    seeMyPosts.setSelected(false);
                    results = algoUtil.postAppliedToByUser(currentUser);
                    return results;
                default:
                    seeMyAppliedPosts.setSelected(false);
                    seeMyPosts.setSelected(false);
                    return results;
            }
        }

    }


    @FXML
    void initialize() {
        posts = JsonUtil.jsonToPosts();
        if (countryList != null) {
            countries.addAll("France", "Allemagne", "Espagne", "Italie", "Royaume-Uni");
            countryList.setItems(countries);
            countryList.setValue("France");
        }
        if (firstNameColumn != null && lastNameColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        }
        if (listPost != null) {
            posts = JsonUtil.jsonToPosts();
        }

        if (choicePost != null){
            allPost.setSelected(true);
        }

        if (toggle_type_date != null) {
            if (post != null) {
                dates.addAll(post.getDates());
            }
            myDatePicker = new MyDatePicker(datesPicker, this);
        }

        if (name_page != null) {
            if (!modify)
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
            myDatePicker.setSelectedDates(dates);
        }

        if (modify && type_post != null) {
            RadioButton one = (RadioButton) type_post.getToggles().get(0);
            RadioButton two = (RadioButton) type_post.getToggles().get(1);
            one.setDisable(true);
            two.setDisable(true);
        }

        if (post != null && part2) {
            if (post instanceof Service) {
                if (modify && descriptionService != null) {
                    descriptionService.setText(post.getDescriptionService());
                    personData.addAll(post.getProviders());
                    listPrest.setItems(personData);
                }

            } else if (post instanceof Tool) {
                if (modify && stateTool != null)
                    stateTool.setText(post.getStateTool());
            }

            if (!modify)
                mode.setText("Création d'un nouveau post");
            else
                mode.setText("Modification d'un post");

            if (sensService != null) {
                RadioButton one = (RadioButton) sensService.getToggles().get(0);
                RadioButton two = (RadioButton) sensService.getToggles().get(1);
                if (modify /*&& post instanceof Service*/) {
                    one.setDisable(true);
                    two.setDisable(true);
                } else {
                    one.setDisable(false);
                    two.setDisable(false);
                }

                if (post.getSensService() == SensService.DEMANDE || post.getSensTool() == null)
                    one.setSelected(true);
                if (post.getSensService() == SensService.PROPOSITION)
                    two.setSelected(true);
            }

            if (sensTool != null) {
                RadioButton one = (RadioButton) sensTool.getToggles().get(0);
                RadioButton two = (RadioButton) sensTool.getToggles().get(1);
                one.setDisable(true);
                two.setDisable(true);
                if (modify /*&& post instanceof Tool*/) {
                    one.setDisable(true);
                    two.setDisable(true);
                } else {
                    one.setDisable(false);
                    two.setDisable(false);
                }

                if (post.getSensTool() == SensTool.PRET || post.getSensTool() == null)
                    one.setSelected(true);
                if (post.getSensTool() == SensTool.EMPRUNT)
                    two.setSelected(true);
            }
        }
    }

    public void initData(Post post, ArrayList<String> stateRadioBtn) {
        if (stateRadioBtn == null){
            ArrayList<String> stateRadioBtnBis = new ArrayList<>(5);
            stateRadioBtnBis.add("Toutes les annonces");
            stateRadioBtnBis.add(null);
            stateRadioBtnBis.add(null);
            stateRadioBtnBis.add(null);
            stateRadioBtnBis.add(null);
            stateRadioBtnBis.add(null);
            stateRadioBtnBis.add("");
            stateRadioBtn = stateRadioBtnBis;

        }
        posts = JsonUtil.jsonToPosts();

        if (posts == null)
            posts = new ArrayList<>();


        if (choicePost != null){
            posts = choiceMyPostedApplied(stateRadioBtn, stateChoice(stateRadioBtn, typeChoice(stateRadioBtn, otherChoice(stateRadioBtn, locationChoice(stateRadioBtn, posts)))));
            SceneController sceneController = new SceneController();
            sceneController.goToRowPost(posts, listPost);

        }

        if (toggle_type_date != null) {
            if (post != null) {
                dates.addAll(post.getDates());
            }
            myDatePicker = new MyDatePicker(datesPicker, this);
        }

        if (name_page != null) {
            if (!modify)
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
            myDatePicker.setSelectedDates(dates);
        }

        if (modify && type_post != null) {
            RadioButton one = (RadioButton) type_post.getToggles().get(0);
            RadioButton two = (RadioButton) type_post.getToggles().get(1);
            one.setDisable(true);
            two.setDisable(true);
        }

        if (post != null && part2) {
            if (post instanceof Service) {
                if (modify && descriptionService != null) {
                    descriptionService.setText(post.getDescriptionService());
                    personData.addAll(post.getProviders());
                    listPrest.setItems(personData);
                }

            } else if (post instanceof Tool) {
                if (modify && stateTool != null)
                    stateTool.setText(post.getStateTool());
            }

            if (!modify)
                mode.setText("Création d'un nouveau post");
            else
                mode.setText("Modification d'un post");

            if (sensService != null) {
                RadioButton one = (RadioButton) sensService.getToggles().get(0);
                RadioButton two = (RadioButton) sensService.getToggles().get(1);
                if (modify /*&& post instanceof Service*/) {
                    one.setDisable(true);
                    two.setDisable(true);
                } else {
                    one.setDisable(false);
                    two.setDisable(false);
                }

                if (post.getSensService() == SensService.DEMANDE || post.getSensTool() == null)
                    one.setSelected(true);
                if (post.getSensService() == SensService.PROPOSITION)
                    two.setSelected(true);
            }

            if (sensTool != null) {
                RadioButton one = (RadioButton) sensTool.getToggles().get(0);
                RadioButton two = (RadioButton) sensTool.getToggles().get(1);
                one.setDisable(true);
                two.setDisable(true);
                if (modify /*&& post instanceof Tool*/) {
                    one.setDisable(true);
                    two.setDisable(true);
                } else {
                    one.setDisable(false);
                    two.setDisable(false);
                }

                if (post.getSensTool() == SensTool.PRET || post.getSensTool() == null)
                    one.setSelected(true);
                if (post.getSensTool() == SensTool.EMPRUNT)
                    two.setSelected(true);
            }
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
            LocalDate start = null;
            LocalDate end = null;
            System.out.println(post);
            if (post != null){
                if (myDatePicker.getStart() == null || myDatePicker.getEnd() == null){
                    start = DateUtil.start((ArrayList<LocalDate>) post.getDates());
                    end = DateUtil.end((ArrayList<LocalDate>) post.getDates());
                }
                else {
                    start = myDatePicker.getStart();
                    end = myDatePicker.getEnd();
                }
            }
            if (myDatePicker.getStart() != null && myDatePicker.getEnd() != null){
                start = myDatePicker.getStart();
                end = myDatePicker.getEnd();
            }
            else {
                RadioButton selectedType = (RadioButton) toggle_type_date.getSelectedToggle();
                if (selectedType.getId().equals("ponctuelles")) {
                    ArrayList<LocalDate> d = new ArrayList<>(dates);
                    start = DateUtil.start(d);
                    end = DateUtil.end(d);
                }
            }

            State state = State.EN_COURS;
            if (start.isAfter(LocalDate.now()))
                state = State.FUTUR;

            if (start.isAfter(LocalDate.now()) && end.isBefore(LocalDate.now()))
                state = State.EN_COURS;

            RadioButton date = (RadioButton) toggle_type_date.getSelectedToggle();

            Type_Date type_date;

            if (date.getId().equals("plage")) {
                type_date = Type_Date.PLAGE;
                LocalDate boucle = start;
                dates.add(boucle);
                datesList.add(boucle);
                while (boucle.isBefore(end)) {
                    boucle = boucle.plusDays(1);
                    dates.add(boucle);
                    datesList.add(boucle);
                }
            } else if (date.getId().equals("ponctuelles")) {
                type_date = Type_Date.PONCTUELLES;
                // todo
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
                sceneController.goToEditServiceHexa(event, post, modify);
            }
            else {
                if (!modify){
                    post = new Tool(description.getText(), title.getText(), author.getEmail(), datesList, new ArrayList<>(), type_date, address, image.getImage(), state, null, null);
                    author.getPostedPosts().add(post.getIdPost());
                }
                else {
                    modifierPost(datesList, author, address, state);
                }
                sceneController.goToEditToolHexa(event, post, modify);
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
        if (lastNamePrest.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le nom ne peut pas être vide").showAndWait();
        } else if (firstNamePrest.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le prénom ne peut pas être vide").showAndWait();
        } else if (emailPrest.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Le mail ne peut pas être vide").showAndWait();
        } else {
            for (User user : users) {
                if (user.getEmail().equals(emailPrest.getText())) {
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
        if (personData.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Vous devez ajouter au moins un prestataire").showAndWait();
        } else if (descriptionService.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "La description ne peut pas être vide").showAndWait();
        } else {
            SceneController sceneController = new SceneController();
            if (!modify) {
                RadioButton selectedToggleSens = (RadioButton) sensService.getSelectedToggle();
                Enum<SensService> sensService;
                if (selectedToggleSens.getText().equals("Proposition"))
                    sensService = SensService.PROPOSITION;
                else
                    sensService = SensService.DEMANDE;
                post = new Service(post, descriptionService.getText(), personData, sensService);
            } else {
                /*if (post.getClass().equals(Tool.class)){
                    RadioButton selectedToggleSens = (RadioButton) sensService.getSelectedToggle();
                    Enum<SensService> sensService;
                    if (selectedToggleSens.getText().equals("Proposition"))
                        sensService = SensService.PROPOSITION;
                    else
                        sensService = SensService.DEMANDE;
                    post = new Service(post, descriptionService.getText(), personData, sensService);
                    post.setIdPost(post.getIdPost() - 1);
                    Post.setNbPosts(Post.getNbPosts() - 1);
                    Post.getListId().remove(Post.getListId().size() - 1);
                }
                else {*/
                post.setDescriptionService(descriptionService.getText());
                post.setProviders(personData);

            }
            users = JsonUtil.jsonToUsers();
            for (User user : users) {
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
            posts.add(post);
            JsonUtil.postsToJson(posts);
            sceneController.goToOverviewServicePostHexa(event, post);
        }
    }

    public void validateToolPost(ActionEvent event) throws IOException {
        if (stateTool.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "L'état ne peut pas être vide").showAndWait();
        } else {
            SceneController sceneController = new SceneController();
            if (!modify) {
                RadioButton selectedToggleSens = (RadioButton) sensTool.getSelectedToggle();
                Enum<SensTool> sensTool;
                if (selectedToggleSens.getText().equals("Emprunt"))
                    sensTool = SensTool.EMPRUNT;
                else
                    sensTool = SensTool.PRET;
                post = new Tool(post, stateTool.getText(), sensTool);
            } else {
                /*if (post.getClass().equals(Service.class)){
                    RadioButton selectedToggleSens = (RadioButton) sensTool.getSelectedToggle();
                    Enum<SensTool> sensTool;
                    if (selectedToggleSens.getText().equals("Emprunt"))
                        sensTool = SensTool.EMPRUNT;
                    else
                        sensTool = SensTool.PRET;
                    post = new Tool(post, stateTool.getText(), sensTool);
                    post.setIdPost(post.getIdPost() - 1);
                    Post.setNbPosts(Post.getNbPosts() - 1);
                    Post.getListId().remove(Post.getListId().size() - 1);
                }
                else {*/
                post.setStateTool(stateTool.getText());

            }
            posts.add(post);
            JsonUtil.postsToJson(posts);
            users = JsonUtil.jsonToUsers();
            for (User user : users) {
                if (user.isConnected())
                    user.getPostedPosts().add(post.getIdPost());
            }
            JsonUtil.usersToJson(users);
            sceneController.goToOverviewToolPostHexa(event, post);
        }
    }

    public void back(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToEditPostHexa(event, post, modify);
    }

    public void back_to_view(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        if (post != null && (post.getDescriptionService() != null || post.getStateTool() != null))

            sceneController.goToOverviewServicePostHexa(event, post);
        else
            sceneController.goToAllPostsHexa(event, posts, null);
    }

    public void newPost(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToCreatePostHexa(event);
    }

    public void logout(ActionEvent event) throws IOException {
        for (User user : users) {
            if (user.isConnected()) {
                user.setConnected(false);
            }
        }

        JsonUtil.usersToJson(users);
        SceneController sceneController = new SceneController();
        sceneController.goToMain(event, 7);
    }
}
