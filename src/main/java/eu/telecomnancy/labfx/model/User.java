package eu.telecomnancy.labfx.model;


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
    private String email;
    private String pseudo;
    private String password;
    private Address address;
    private ArrayList<Double> evaluationList;
    private int coins;
    private Image profilePicture;
    private ArrayList<Post> postedPosts = new ArrayList<>();
    private ArrayList<Post> appliedToPosts = new ArrayList<>();
    private List<Conversation> convs;

    public User(String prenom, String nom, String mail) {
        super(prenom, nom);
        this.email = mail;
        this.convs = new ArrayList<Conversation>();
        this.profilePicture = new Image(getClass().getResourceAsStream("/pictures/defaultpfp.png"));
    }

    public User(String prenom, String nom, String mail, String pseudo) {
        super(prenom, nom);
        this.email = mail;
        this.pseudo = pseudo;
        this.convs = new ArrayList<Conversation>();
        this.profilePicture = new Image(getClass().getResourceAsStream("/pictures/defaultpfp.png"));
    }

    public User(String prenom, String nom, String email, String pseudo, String password, Address address, Image profilePicture) {
        super(prenom, nom);
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.address = address;
        this.profilePicture = profilePicture;
        this.convs = new ArrayList<Conversation>();
    }


    public int getNumberOfEvaluations(){
        return this.evaluationList.size();
    }
    private Double getEvaluation(){
        Double finalNote = 0.;
        for(Double note: this.evaluationList) {
            finalNote = finalNote + note;
        }
        return (Double)finalNote/getNumberOfEvaluations();
    }

    public void addConv(Conversation conv){
        convs.add(conv);
    }

    public void delConv(Conversation conv){
        convs.remove(conv);
    }

}
