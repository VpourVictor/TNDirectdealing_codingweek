package eu.telecomnancy.labfx.model;

import eu.telecomnancy.labfx.controller.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lombok.Getter;

import java.time.LocalDate;
import java.util.TreeSet;


@Getter
public class MyDatePicker {
    @Getter
    private final ObservableSet<LocalDate> selectedDates;
    private final DatePicker datePicker;
    private final boolean isStart;

    public MyDatePicker(DatePicker dp, boolean isStart) {
        this.selectedDates = FXCollections.observableSet(new TreeSet<>());
        this.datePicker = dp;
        this.isStart = isStart;
        setUpDatePicker();
    }

    private void setUpDatePicker() {
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
                    if (this.isStart) {
                        this.datePicker.setValue(start());
                    } else {
                        this.datePicker.setValue(end());
                    }
                } else {
                    this.selectedDates.remove(this.datePicker.getValue());
                    if (this.isStart) {
                        this.datePicker.setValue(start());
                    } else {
                        this.datePicker.setValue(end());
                    }
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

    private LocalDate start() {
        // séléction de la date de début
        // on parcourt les dates sélectionnées et on prend la plus petite
        LocalDate min = null;
        for (LocalDate date : this.selectedDates) {
            if (min == null || date.isBefore(min)) {
                min = date;
            }
        }
        return min;
    }

    private LocalDate end() {
        // séléction de la date de fin
        // on parcourt les dates sélectionnées et on prend la plus grande
        LocalDate max = null;
        for (LocalDate date : this.selectedDates) {
            if (max == null || date.isAfter(max)) {
                max = date;
            }
        }
        return max;
    }
}