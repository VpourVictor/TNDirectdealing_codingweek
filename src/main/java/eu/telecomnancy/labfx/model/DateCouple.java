package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class DateCouple {
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public DateCouple(LocalDate dateStart, LocalDate dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
