package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ApplicationToPost {
    private Post post;
    private String applicantEmail;

    private LocalDate start;

    private int duration;

    private boolean accepted;

    public ApplicationToPost(Post post, String applicantEmail, LocalDate start, int duration) {
        this.post = post;
        this.applicantEmail = applicantEmail;
        this.start = start;
        this.duration = duration;
        this.accepted = false;
    }
}
