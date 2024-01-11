package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class ApplicationToPost {
    protected static int nbAppli = 0;

    private int idAppli;

    private String applicantEmail;

    private ArrayList<LocalDate> dates;

    private String comment;

    public ApplicationToPost(String comment) {
        this.comment = comment;
    }

    public ApplicationToPost(String applicantEmail, ArrayList<LocalDate> dates, String comment) {
        this.applicantEmail = applicantEmail;
        this.dates = dates;
        this.comment = comment;
        nbAppli++;
        this.idAppli = nbAppli;
    }
}
