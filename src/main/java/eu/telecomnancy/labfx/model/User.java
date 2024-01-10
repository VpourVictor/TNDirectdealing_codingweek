package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User extends Person {

    @Setter
    private boolean isConnected;
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
    @Getter
    @Setter
    private static int nbUsers = 0;

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
    private List<Conversation> convs;

    public User(String prenom, String nom) {
        super(prenom, nom);
        nbUsers++;
    }

    public User(String prenom, String nom, String email) {
        super(prenom, nom, email);
        this.convs = new ArrayList<Conversation>();
        this.profilePicture = new Image(getClass().getResourceAsStream("/pictures/defaultpfp.png"));
        nbUsers++;
    }

    public User(String prenom, String nom, String email, String pseudo, String password, Address address, Image profilePicture) {
        super(prenom, nom, email);
        this.pseudo = pseudo;
        this.password = password;
        this.address = address;
        this.profilePicture = profilePicture;
        this.convs = new ArrayList<Conversation>();
        nbUsers++;
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

    public void delConv(Conversation conv){
        convs.remove(conv);
    }

}
