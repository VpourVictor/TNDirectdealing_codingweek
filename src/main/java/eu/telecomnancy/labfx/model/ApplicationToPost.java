package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApplicationToPost {
    @Getter
    @Setter
    protected static int nbAppli;

    private int idAppli;

    private String applicantEmail;

    private ArrayList<LocalDate> dates;

    private String comment;

    private boolean accepted;

    @Getter
    protected static List<Integer> listId = new ArrayList<>();

    public ApplicationToPost(String comment) {
        this.comment = comment;
        this.accepted = false;
        ApplicationToPost.nbAppli++;
        this.idAppli = nbAppli;
        ApplicationToPost.listId.add(this.idAppli);
    }

    public ApplicationToPost(int id, String applicantEmail, ArrayList<LocalDate> dates, String comment) {
        this.applicantEmail = applicantEmail;
        this.dates = dates;
        this.comment = comment;
        this.idAppli = id;
    }
}
