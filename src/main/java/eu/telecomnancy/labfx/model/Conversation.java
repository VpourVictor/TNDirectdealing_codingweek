package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Conversation {
    private User user1;
    private User user2;
    private List<Message> messages;

    public Conversation(User usr1, User usr2){
        user1 = usr1;
        user2 = usr2;
        messages = new ArrayList<Message>();
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
