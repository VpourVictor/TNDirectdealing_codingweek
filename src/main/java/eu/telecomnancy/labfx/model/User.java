package eu.telecomnancy.labfx.model;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
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

    private boolean isConnected;
    private String pseudo;
    private String password;
    private Address address;
    private ArrayList<Double> evaluationList = new ArrayList<>();
    private int coins;
    private Image profilePicture;
    private ArrayList<Integer> postedPosts = new ArrayList<>();
    private ArrayList<Integer> appliedToPosts = new ArrayList<>();
    @Getter
    @Setter
    private static int nbUsers = 0; //JsonUtil.jsonToUsers().size();
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

    public User(String prenom, String nom, String mail, String pseudo) {
        super(prenom, nom, mail);
        this.pseudo = pseudo;
        this.convs = new ArrayList<Conversation>();
        this.profilePicture = new Image(getClass().getResourceAsStream("/pictures/defaultpfp.png"));
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

    public void addConv(Conversation conv){
        convs.add(conv);
    }

}
