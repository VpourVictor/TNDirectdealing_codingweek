package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.HexaSuper;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class PostOverviewController extends HexaSuper {
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

    private ArrayList<User> users = JsonUtil.jsonToUsers();

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

        User author = null;
        for (User user : users){
            if (user.getEmail().equals(post.getAuthorEmail()))
                author = user;
        }

        firstName.setText(author.getFirstName());
        lastName.setText(author.getLastName());
        email.setText(author.getEmail());
        dates.addAll(post.getDates());
        listDate.setItems(dates);
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
        posts = JsonUtil.jsonToPosts();
        for (int i = 0; i < posts.size(); i++){
            if (posts.get(i).getIdPost() == this.post.getIdPost()){
                int id = posts.get(i).getIdPost();
                posts.remove(posts.get(i));
                Post.getListId().remove((Integer) id);
            }
        }

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
