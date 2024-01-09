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
    private ArrayList<Double> evaluationList = new ArrayList<>();
    private int coins;
    private Image profilePicture;
    private ArrayList<Post> postedPosts = new ArrayList<>();
    private ArrayList<Post> appliedToPosts = new ArrayList<>() ;
    protected int nbOfEvaluation = 0;
    protected int nbOfPostedPosts = 0;
    protected int nbOfAppliedToPosts = 0;

    public void addPostedPosts( Post post ) {
        this.postedPosts.add(post);
        this.nbOfPostedPosts = getNbOfPostedPosts()+1;
    }

    public void addAppliedToPosts( Post post ) {
        this.appliedToPosts.add(post);
        this.nbOfAppliedToPosts = getNbOfAppliedToPosts()+1;
    }

    public void addEvaluation( Double note ) {
        this.evaluationList.add(note);
        this.nbOfEvaluation = getNbOfEvaluation()+1;
    }

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
    private double getEvaluation(){
        double finalNote = 0;
        for(Double note: this.evaluationList) {
            finalNote = finalNote + note;
        }
        return finalNote/getNumberOfEvaluations();
    }


}
