package eu.telecomnancy.labfx.view;

import eu.telecomnancy.labfx.controller.utils.AlgoUtil;
import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import eu.telecomnancy.labfx.model.AnchorPaneNode;
import eu.telecomnancy.labfx.model.ApplicationToPost;
import eu.telecomnancy.labfx.model.Post;
import eu.telecomnancy.labfx.model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class FullCalendarView {
    @Setter
    @Getter
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    @Getter
    private final VBox view;
    private final Text calendarTitle;
    private YearMonth currentYearMonth;
    private ArrayList<User> users;

    public FullCalendarView(YearMonth yearMonth) {
        // transforme ce code en fichier fxml

        currentYearMonth = yearMonth;
        GridPane calendar = new GridPane();
        calendar.setGridLinesVisible(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(80, 60);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }

        Text[] dayNames = new Text[]{new Text("Lundi"), new Text("Mardi"),
                new Text("Mercredi"), new Text("Jeudi"), new Text("Vendredi"),
                new Text("Samedi"), new Text("Dimanche")};
        GridPane dayLabels = new GridPane();

        dayLabels.setPrefWidth(505);

        int col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(150, 10);
            AnchorPane.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        calendarTitle = new Text();
        Button previousMonth = new Button("<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">");
        nextMonth.setOnAction(e -> nextMonth());

        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        populateCalendar(yearMonth);
        view = new VBox(titleBar, dayLabels, calendar);
    }

    public void populateCalendar(YearMonth yearMonth) {
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        for (AnchorPaneNode ap : allCalendarDays) {
            if (!ap.getChildren().isEmpty()) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);

            ArrayList<LocalDate> datesFromMyPost = new ArrayList<>();
            ArrayList<LocalDate> datesFromMyPostReserved = new ArrayList<>();
            ArrayList<LocalDate> datesFromPostAppliedTo = new ArrayList<>();

            users = JsonUtil.jsonToUsers();
            AlgoUtil algoUtil = new AlgoUtil();
            ArrayList<Post> posts = new ArrayList<>();
            for (User user : users) {
                if (user.isConnected()){
                    if (algoUtil.postAppliedToByUser(user) != null){
                        posts.addAll(algoUtil.postAppliedToByUser(user));
                        posts.forEach(post -> {
                            ArrayList<ApplicationToPost> applications = algoUtil.getApplicationsFromPost(post);
                            applications.forEach(applicationToPost -> {
                                if (applicationToPost.getApplicantEmail().equals(user.getEmail())){
                                    datesFromPostAppliedTo.addAll(applicationToPost.getDates());
                                }
                            });
                        });
                    }

                    if (algoUtil.postFromUser(user) != null){
                        posts.addAll(algoUtil.postFromUser(user));
                        posts.forEach(post -> {
                            datesFromMyPost.addAll(post.getDates());
                            datesFromMyPostReserved.addAll(post.getDatesOccupied());
                        });
                    }
                }
            }


            ArrayList<LocalDate> visitedDates = new ArrayList<>();
            if (datesFromMyPost.contains(calendarDate) && datesFromMyPostReserved.contains(calendarDate) && datesFromPostAppliedTo.contains(calendarDate)){
                visitedDates.add(calendarDate);
            }

            for (LocalDate date : datesFromMyPost){
                if (date.equals(calendarDate)){
                    ap.changeStyle("-fx-background-color: #6bf28f");
                }
            }

            for (LocalDate date : datesFromMyPostReserved){
                if (date.equals(calendarDate)){
                    ap.changeStyle("-fx-background-color: #f23f3f");
                }
            }

            for (LocalDate date : datesFromPostAppliedTo){
                if (date.equals(calendarDate)){
                    ap.changeStyle("-fx-background-color: #54b9f0");
                }
            }

            for (LocalDate date : visitedDates){
                if (date.equals(calendarDate)){
                    // dates qui sont présentes dans différentes catégories
                    ap.changeStyle("-fx-background-color: #35f2f2");
                }
            }

            AnchorPane.setTopAnchor(txt, 5.0);
            AnchorPane.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }

        calendarTitle.setText(yearMonth.getMonth().toString() + " " + yearMonth.getYear());
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }
}
