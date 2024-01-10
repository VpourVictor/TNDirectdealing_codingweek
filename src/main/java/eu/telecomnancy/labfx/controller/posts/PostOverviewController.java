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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PostOverviewController extends HexaSuper {
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

    private Post post;
    private ArrayList<Post> posts;

    @FXML
    Pane hexagonPane;
    @FXML
    Polygon hexagon;


    public Polygon getHexagon() {
        return hexagon;
    }

    public void initData(Post post) {
        System.out.println(Post.getNbPosts());
        posts = JsonUtil.jsonToPosts();
/*        if (posts == null) {
            posts = new ArrayList<>();
        }*/
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
        User author = new User("test", "test");

        firstName.setText(author.getFirstName());
        lastName.setText(author.getLastName());
        dateStart.setText(DateUtil.format(post.getDateCouple().getDateStart()));
        dateEnd.setText(DateUtil.format(post.getDateCouple().getDateEnd()));
        title.setText(post.getTitle());
        country.setText(post.getAddress().getCountry());
        postalCode.setText(String.valueOf(post.getAddress().getPostalCode()));
        region.setText(post.getAddress().getRegion());
        streetName.setText(post.getAddress().getStreetName());
        image.setImage(post.getImage());
    }

    @FXML
    void initialize() {
        if (firstNameColumn != null && lastNameColumn != null) {
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        }
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
/*        posts.remove(post);
        Post.setNbPosts(Post.getNbPosts() - 1);
        JsonUtil.postsToJson(posts);*/

        SceneController sceneController = new SceneController();
        sceneController.goToAllPosts(event, posts);
    }
}
