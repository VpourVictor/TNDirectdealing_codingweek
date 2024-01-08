package eu.telecomnancy.labfx;


import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class User extends Person {

    private String email;
    private String pseudo;
    private String password;
    private Address address;
    private ArrayList<Float> evaluationList;
    private int coins;
    private Image profilePicture;
    // TODO ArrayList<Post> postedPosts
    // TODO ArrayList<Post> appliedToPosts

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
