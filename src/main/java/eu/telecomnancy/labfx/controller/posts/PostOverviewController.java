package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.DateUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class PostOverviewController {
    private Post post;
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
    private Label dateEnd;

    @FXML
    private Label dateStart;

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
    private final ObservableList<LocalDate> dates = FXCollections.observableArrayList();

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
        this.post = post;
        if (post instanceof Service) {
            descriptionService.setText(post.getDescriptionService());
            personData.addAll(post.getProviders());
            personTable.setItems(personData);
        } else if (post instanceof Tool) {
            stateTool.setText(post.getStateTool());
        }
        description.setText(post.getDescription());

        // todo changer en fonction de la base
        User author = new User("test", "test", "test@test.com");

        firstName.setText(author.getFirstName());
        lastName.setText(author.getLastName());
        email.setText(author.getEmail());
        dates.addAll(post.getDates());
        listDate.setItems(dates);
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
        posts = JsonUtil.jsonToPosts();
        posts.removeIf(postR -> postR.getIdPost() == this.post.getIdPost());
        Post.setNbPosts(Post.getNbPosts() - 1);
        JsonUtil.postsToJson(posts);

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
}
