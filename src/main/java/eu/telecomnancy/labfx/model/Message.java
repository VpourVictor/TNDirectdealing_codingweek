package eu.telecomnancy.labfx.model;

import eu.telecomnancy.labfx.controller.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private int id;
    private User sender;
    private User receiver;
    private String content;
    private String date;
    @Getter
    @Setter
    private static int nb_msgs = 0;

    public Message(User sender, User receiver, String content, String date){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = date;
        this.id = nb_msgs;
        saveinjason(this);
        nb_msgs++;
    }

    public Message(String mailsender, String mailreceiver, String content, String date){
        //this.sender = sender;
        //this.receiver = receiver;
        this.content = content;
        this.date = date;
        this.id = nb_msgs;
        saveinjason(this);
        nb_msgs++;
    }

    public void saveinjason(Message msg){
    JsonUtil.saveMsgInJason(this);
    }
}
