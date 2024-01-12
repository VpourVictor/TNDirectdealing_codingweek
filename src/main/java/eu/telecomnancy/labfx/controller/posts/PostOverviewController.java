package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.HexaSuper;
import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.DateUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PostOverviewController extends HexaSuper {
    private Post post;
    private User user;
    @FXML
    public Text descriptionService;
    @FXML
    public Label streetNumber;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;

    @FXML
    private Label country;

    @FXML
    private Text description;

    @FXML
    private Label firstName;

    @FXML
    private ImageView image;

    @FXML
    private Label lastName;

    @FXML Label email;

    @FXML
    private Label postalCode;

    @FXML
    private Label region;

    @FXML
    private Text stateTool;

    @FXML
    private Label streetName;

    @FXML
    private Label title;

    private ArrayList<Post> posts;

    public ListView<LocalDate> listDate;

    @FXML private  Label type_date;
    private final ObservableList<LocalDate> dates = FXCollections.observableArrayList();
    @FXML
    Pane hexagonPane;
    @Getter
    @FXML
    Polygon hexagon;

    @FXML
    private Button masquer;

    @FXML Button demasquer;

    @FXML Button postuler;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @FXML
    private Button candidatures;

    private ArrayList<User> users = JsonUtil.jsonToUsers();

    private ArrayList<ApplicationToPost> applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
    @FXML
    public Button open_candidature;

    private ApplicationToPost applicationToPost;

    @FXML
    void initialize() {
        posts = JsonUtil.jsonToPosts();
        if (firstNameColumn != null && lastNameColumn != null && emailColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        }
    }

    public void initData(Post post) {
        posts = JsonUtil.jsonToPosts();
        users = JsonUtil.jsonToUsers();
        for (User user : users){
            if (user.isConnected()){
                this.user = user;
                break;
            }
        }

        AlgoUtil algoUtil = new AlgoUtil(posts);
        ArrayList<Post> applied = algoUtil.postAppliedToByUser(this.user);

        this.post = post;
        if (applied != null){
            if (applied.size() == 1){
                postuler.setVisible(false);
            }
            for (Post post1 : applied){
                if (post1.getIdPost() == post.getIdPost()){
                    open_candidature.setVisible(true);
                    for (ApplicationToPost application : applications){
                        if (application.getApplicantEmail().equals(this.user.getEmail())){
                            applicationToPost = application;
                            break;
                        }
                    }
                }
            }
        }

        if (post instanceof Service) {
            descriptionService.setText(post.getDescriptionService());
            personData.addAll(post.getProviders());
            personTable.setItems(personData);
        } else if (post instanceof Tool) {
            stateTool.setText(post.getStateTool());
        }
        description.setText(post.getDescription());
        User author = null;
        for (User user : users){
            if (user.getEmail().equals(post.getAuthorEmail())) {
                author = user;
                if (user.isConnected()) {
                    modifier.setVisible(true);
                    supprimer.setVisible(true);
                    masquer.setVisible(true);
                    demasquer.setVisible(true);
                    candidatures.setVisible(true);
                    if (post.getState() == State.FUTUR || post.getState() == State.EN_COURS) {
                        masquer.setDisable(false);
                        demasquer.setDisable(true);
                    }

                    if (post.getState() == State.MASQUE) {
                        masquer.setDisable(true);
                        demasquer.setDisable(false);
                    }
                }
                else{
                    if (applied != null){
                        if (applied.size() == 1){
                            postuler.setVisible(false);
                        }
                    }
                    else {
                        postuler.setVisible(true);
                    }
                }
            }
        }

        if (applications != null) {
            if (post.getDatesOccupied().equals(post.getDates())){
                postuler.setVisible(false);
            }
        }

        firstName.setText(author.getFirstName());
        lastName.setText(author.getLastName());
        email.setText(author.getEmail());
        dates.addAll(post.getDates());
        listDate.setItems(dates);
        listDate.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LocalDate> call(ListView<LocalDate> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(DateUtil.format(item));
                            if (post.getDatesOccupied().contains(item)) {
                                setStyle("-fx-background-color: #ff0000");
                            } else {
                                setStyle("-fx-background-color: #00ff00");
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        if (post.getType_date() == Type_Date.PONCTUELLES) {
            type_date.setText("Ponctuelle");
        } else if (post.getType_date() == Type_Date.PLAGE) {
            type_date.setText("Plage");
        } else if (post.getType_date() == Type_Date.PONCTUELLE_REC) {
            type_date.setText("Ponctuelle récurrente");
        }
        title.setText(post.getTitle());
        country.setText(post.getAddress().getCountry());
        postalCode.setText(String.valueOf(post.getAddress().getPostalCode()));
        region.setText(post.getAddress().getRegion());
        streetName.setText(post.getAddress().getStreetName());
        image.setImage(post.getImage());
    }

    public void modify(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToEditPost(event, post, true);
    }

    public void viewAll(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, posts);
    }

    public void delete(ActionEvent event) {
        users = JsonUtil.jsonToUsers();
        posts = JsonUtil.jsonToPosts();
        applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
        for (int i = 0; i < posts.size(); i++){
            if (posts.get(i).getIdPost() == this.post.getIdPost()){
                int id = posts.get(i).getIdPost();
                ArrayList<Integer> myAppli = (ArrayList<Integer>) posts.get(i).getApplications();
                for (Integer integer : myAppli) {
                    for (int k = 0; k < Objects.requireNonNull(applications).size(); k++) {
                        if (applications.get(k).getIdAppli() == integer) {
                            ApplicationToPost.getListId().remove((Integer) applications.get(k).getIdAppli());
                            ApplicationToPost.setNbAppli(ApplicationToPost.getNbAppli() - 1);
                            applications.remove(applications.get(k));
                        }
                    }
                }

                for (User user : users){
                    user.getAppliedToPosts().remove((Integer) id);
                }

                posts.remove(posts.get(i));
                Post.getListId().remove((Integer) id);
                Post.setNbPosts(Post.getNbPosts() - 1);
            }
        }

        JsonUtil.usersToJson(users);
        JsonUtil.postsToJson(posts);
        JsonUtil.applicationsToJson(applications);

        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, posts);
    }

    public void viewService(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToOverviewServicePost(event, post);
    }

    public void viewTool(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToOverviewToolPost(event, post);
    }
    public void deleteHexa(ActionEvent event) throws IOException {
        posts = JsonUtil.jsonToPosts();
        posts.removeIf(postR -> postR.getIdPost() == this.post.getIdPost());
        Post.setNbPosts(Post.getNbPosts() - 1);
        JsonUtil.postsToJson(posts);

        SceneController sceneController = new SceneController();
        sceneController.goToMain(event,17);
        //sceneController.goToAllPosts(event, posts);
    }
    public void viewAllHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMain(event,17);
        //sceneController.goToAllPosts(event, posts);
    }

    public void hide(ActionEvent event){
        for (Post value : posts) {
            if (value.getIdPost() == this.post.getIdPost()) {
                if (value.getState().equals(State.EN_COURS) || value.getState().equals(State.FUTUR))
                    value.setState(State.MASQUE);
            }
        }
        JsonUtil.postsToJson(posts);
        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, posts);
    }

    public void show(ActionEvent event){
        for (Post value : posts) {
            if (value.getIdPost() == this.post.getIdPost()) {
                if (value.getState().equals(State.MASQUE)){
                    LocalDate start = null;
                    LocalDate end = null;
                    for (LocalDate date : value.getDates()){
                        if (end == null || date.isAfter(end)) {
                            end = date;
                        }

                        if (start == null || date.isBefore(start)){
                            start = date;
                        }
                    }

                    if (start.equals(LocalDate.now()))
                        value.setState(State.EN_COURS);
                    else
                        value.setState(State.FUTUR);
                }
            }
        }

        JsonUtil.postsToJson(posts);
        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, posts);
    }

    public void apply(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToApplyPost(event, post);
    }

    // todo random
    // si il fait supprimer voir ce que les candidatures deviennent


    public void show_applications(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToApplications(event, post);
    }

    public void open(ActionEvent event) {
        SceneController sceneController = new SceneController();
        sceneController.goToMyApplication(event, applicationToPost);
    }

    public void show_applicationsHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainValidate(event, 27, post);
    }

    public void applyHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainValidate(event, 28, post);
    }

    public void viewServiceHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainValidate(event, 25, post);
    }
    public void openHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainApplication(event,29,applicationToPost);
    }

    public void showHexa(ActionEvent event) throws IOException {
        for (Post value : posts) {
            if (value.getIdPost() == this.post.getIdPost()) {
                if (value.getState().equals(State.MASQUE)){
                    LocalDate start = null;
                    LocalDate end = null;
                    for (LocalDate date : value.getDates()){
                        if (end == null || date.isAfter(end)) {
                            end = date;
                        }

                        if (start == null || date.isBefore(start)){
                            start = date;
                        }
                    }

                    if (start.equals(LocalDate.now()))
                        value.setState(State.EN_COURS);
                    else
                        value.setState(State.FUTUR);
                }
            }
        }

        JsonUtil.postsToJson(posts);
        SceneController sceneController = new SceneController();
        sceneController.goToMain(event,30);
    }
    public void hideHexa(ActionEvent event) throws IOException {
        for (Post value : posts) {
            if (value.getIdPost() == this.post.getIdPost()) {
                if (value.getState().equals(State.EN_COURS) || value.getState().equals(State.FUTUR))
                    value.setState(State.MASQUE);
            }
        }
        JsonUtil.postsToJson(posts);
        SceneController sceneController = new SceneController();
        sceneController.goToMain(event,30);

    }
    public void viewToolHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainValidate(event, 23, post);
    }

    public void modifyHexa(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.goToMainEdit(event,22,post, true);
    }
}
