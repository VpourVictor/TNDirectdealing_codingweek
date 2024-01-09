package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.controller.ConversationController;
import eu.telecomnancy.labfx.controller.MessagerieController;
import eu.telecomnancy.labfx.model.Conversation;
import eu.telecomnancy.labfx.model.Message;
import eu.telecomnancy.labfx.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            User user1 = new User("gab1", "granjon", "mail1", "Rezko");
            User user2 = new User("gab2", "granjon", "m2", "Megarem");
            User user3 = new User("gab3", "granjon", "m3", "ElfeSauvage");
            User user4 = new User("gab4", "granjon", "m4", "Victor");
            User user5 = new User("gab5", "granjon", "m5", "fjerk");

            Conversation c1 = new Conversation(user1, user3);
            Conversation c2 = new Conversation(user1, user4);
            Conversation c3 = new Conversation(user1, user5);
            Conversation c4 = new Conversation(user1, user2);
            Conversation c5 = new Conversation(user2, user3);
            Conversation c6 = new Conversation(user2, user4);
            Conversation c7 = new Conversation(user2, user5);

            user1.addConv(c1);
            user1.addConv(c2);
            user1.addConv(c3);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user3.addConv(c1);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);
            user1.addConv(c4);

            user4.addConv(c2);

            Message message1 = new Message(user1, user2, "Salut");
            Message message2 = new Message(user2, user1, "Ca va?");
            c1.addMessage(message1);
            c1.addMessage(message2);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/conversation.fxml"));
            Parent root = fxmlLoader.load();
            ConversationController cc = fxmlLoader.getController();
            cc.setConv(c1);
            cc.setAndLoad(user1);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            cc.setPrimaryStage(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

