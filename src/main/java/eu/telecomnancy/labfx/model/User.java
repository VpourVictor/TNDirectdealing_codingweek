package eu.telecomnancy.labfx.model;


import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class User extends Person {

    @Setter
    private boolean isConnected;
    private String email;
    private String pseudo;
    private String password;
    private Address address;
    private ArrayList<Float> evaluationList;
    private int coins;
    private Image profilePicture;
    private ArrayList<Post> postedPosts = new ArrayList<>();
    private ArrayList<Post> appliedToPosts = new ArrayList<>();

    public User(String prenom, String nom) {
        super(prenom, nom);
    }

    public User(String prenom, String nom, String email, String pseudo, String password, Address address, Image profilePicture) {
        super(prenom, nom);
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.address = address;
        this.profilePicture = profilePicture;
    }


    public int getNumberOfEvaluations(){
        return this.evaluationList.size();
    }
    private float getEvaluation(){
        float finalNote = 0;
        for(Float note: this.evaluationList) {
            finalNote = finalNote + note;
        }
        return finalNote/getNumberOfEvaluations();
    }


}
