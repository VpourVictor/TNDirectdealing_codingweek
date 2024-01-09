package eu.telecomnancy.labfx.controller.posts;

import eu.telecomnancy.labfx.controller.SceneController;
import eu.telecomnancy.labfx.controller.utils.DateUtil;
import eu.telecomnancy.labfx.model.Person;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.Service;
import eu.telecomnancy.labfx.model.Tool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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

    public void initData(Post post) {
        this.post = post;
        if (post instanceof Service) {
            descriptionService.setText(post.getDescriptionService());
            personData.addAll(post.getProviders());
            personTable.setItems(personData);

        } else if (post instanceof Tool) {
            stateTool.setText(post.getStateTool());
        }
        description.setText(post.getDescription());
        firstName.setText(post.getAuthor().getFirstName());
        lastName.setText(post.getAuthor().getLastName());
        dateStart.setText(DateUtil.format(post.getDateCouple().getDateStart()));
        dateEnd.setText(DateUtil.format(post.getDateCouple().getDateEnd()));
        title.setText(post.getTitle());
        country.setText(post.getAdress().getCountry());
        postalCode.setText(String.valueOf(post.getAdress().getPostalCode()));
        region.setText(post.getAdress().getRegion());
        streetName.setText(post.getAdress().getStreetName());
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
        sceneController.goToEditPost(event, post);
    }
}
