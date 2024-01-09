package eu.telecomnancy.labfx.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalActor extends Person{
    private String job;

    public ExternalActor(String prenom, String nom) {
        super(prenom, nom);
    }
}
