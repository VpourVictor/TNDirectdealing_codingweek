package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Conversation {
    private int id;
    private User user1;
    private User user2;
    private ArrayList<Message> messages;
    @Getter
    @Setter
    private static int nb_convs = 0;

    public Conversation(User usr1, User usr2){
        user1 = usr1;
        user2 = usr2;
        messages = new ArrayList<Message>();
        id = nb_convs + 1;
    }

    public Conversation(User usr1, User usr2, ArrayList<Message> list){
        user1 = usr1;
        user2 = usr2;
        messages = list;
        id = nb_convs + 1;
    }

    public Conversation(User usr1, User usr2, ArrayList<Message> list, int id){
        user1 = usr1;
        user2 = usr2;
        messages = list;
        this.id =id;
    }

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public User getOther(User user){
        if (user == user1){
            return user2;
        }
        else{
            return user1;
        }
    }
}
