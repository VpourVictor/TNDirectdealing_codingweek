package eu.telecomnancy.labfx.model;

import eu.telecomnancy.labfx.controller.posts.PostEditController;
import eu.telecomnancy.labfx.controller.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.TreeSet;


@Getter
public class MyDatePicker {
    @Setter
    private ObservableList<LocalDate> selectedDates;
    private final DatePicker datePicker;
    private PostEditController postEditController;
    private LocalDate start;
    private LocalDate end;

    public MyDatePicker(DatePicker dp, PostEditController postEditController) {
        this.selectedDates = FXCollections.observableArrayList();
        this.datePicker = dp;
        this.postEditController = postEditController;
        setUpDatePicker();
    }

    public void setUpDatePicker() {
        this.selectedDates.addAll(postEditController.dates);

        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : DateUtil.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return ((string == null) || string.isEmpty()) ? null : LocalDate.parse(string, DateUtil.DATE_FORMATTER);
            }
        });

        EventHandler<MouseEvent> mouseClickedEventHandler = (MouseEvent clickEvent) ->
        {
            if (clickEvent.getButton() == MouseButton.PRIMARY) {
                if (!this.selectedDates.contains(this.datePicker.getValue())) {
                    this.selectedDates.add(datePicker.getValue());
                    start();
                    end();
                    postEditController.dates.add(datePicker.getValue());
                    postEditController.listDate.setItems(FXCollections.observableArrayList(postEditController.dates));

                } else {
                    this.selectedDates.remove(this.datePicker.getValue());
                    start();
                    end();
                    postEditController.dates.remove(datePicker.getValue());
                    postEditController.listDate.setItems(FXCollections.observableArrayList(postEditController.dates));

                }
            }
            this.datePicker.show();
            clickEvent.consume();
        };

        this.datePicker.setDayCellFactory((DatePicker param) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }

                if (!empty) {
                    addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                } else {
                    removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
                }

                if (selectedDates.contains(item)) {
                    setStyle("-fx-background-color: rgba(3, 169, 244, 0.7);");
                } else {
                    setStyle(null);
                }
            }
        });
    }

    private void start() {
        LocalDate min = null;
        for (LocalDate date : this.selectedDates) {
            if (min == null || date.isBefore(min)) {
                min = date;
            }
        }
        start = min;
    }

    private void end() {
        LocalDate max = null;
        for (LocalDate date : this.selectedDates) {
            if (max == null || date.isAfter(max)) {
                max = date;
            }
        }
        end = max;
    }
}