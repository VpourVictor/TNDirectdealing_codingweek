package eu.telecomnancy.labfx.controller.utils;

import eu.telecomnancy.labfx.model.*;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class JsonUtil {
    public static JSONObject providersToJson(List<Person> providers) {
        JSONObject usersJson = new JSONObject();
        try {
            if (!providers.isEmpty()){
                int i = 1;
                for (Person person : providers) {
                    JSONObject json = providerToJson(person);
                    usersJson.put("provider" + i++, json);
                }
            }
            else {
                usersJson.put("provider0", "");
            }

            return usersJson;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject providerToJson(Person provider) {
        JSONObject json = new JSONObject();
        try {
            json.put("firstName", provider.getFirstName());
            json.put("lastName", provider.getLastName());
            json.put("email", provider.getEmail());

            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Person> jsonToProviders(int nbProviders, JSONObject json){
        ArrayList<Person> providers = new ArrayList<>();

        for (int i = 1; i <= nbProviders ; i++){
            JSONObject jsonObject = json.getJSONObject("provider" + i);
            providers.add(jsonToProvider(jsonObject));
        }
        return providers;
    }

    private static ExternalActor jsonToProvider(JSONObject provider) {
        String firstName = provider.getString("firstName");
        String lastName = provider.getString("lastName");
        String email = provider.getString("email");

        return new ExternalActor(firstName, lastName, email);
    }


    public static JSONObject postToJson(Post post) {
        JSONObject json = new JSONObject();

        try {
            json.put("id", post.getIdPost());
            json.put("title", post.getTitle());
            json.put("description", post.getDescription());
            json.put("author_email", post.getAuthorEmail());
            json.put("dates", post.getDates());
            json.put("datesOccupied", post.getDatesOccupied());
            json.put("applications", post.getApplications());
            json.put("address", adressToJson(post.getAddress()));
            if (post.getImage() != null)
                json.put("path", post.getImage().getUrl());
            else
                json.put("path", "");
            json.put("state", post.getState());
            json.put("type_date", post.getType_date());

            if (post instanceof Service) {
                json.put("type", "service");
                json.put("descriptionService", post.getDescriptionService());
                json.put("providers", providersToJson(post.getProviders()));
            }
            else if (post instanceof Tool) {
                json.put("type", "tool");
                json.put("stateTool", post.getStateTool());
            }

            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void postsToJson(ArrayList<Post> posts) {
        String path = "src/main/resources/json/posts.json";

        JSONObject postsJson = new JSONObject();
        try {
            if (!posts.isEmpty()){
                for (Post post : posts) {
                    JSONObject json = postToJson(post);
                    postsJson.put("post" + post.getIdPost(), json);
                }
            }
            else {
                postsJson.put("post0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(postsJson.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Post> jsonToPosts(){
        try {
            InputStream is = new FileInputStream("src/main/resources/json/posts.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = buffer.readLine();
            StringBuilder builder = new StringBuilder();

            while(line != null){
                builder.append(line).append("\n");
                line = buffer.readLine();
            }

            ArrayList<Post> posts = new ArrayList<>();

            JSONObject json = new JSONObject(builder.toString());

            if (Post.getNbPosts() == 0)
                return posts;

            for (int i = 0; i < Post.getListId().size() ; i++){
                int val = Post.getListId().get(i);
                JSONObject jsonObject = json.getJSONObject("post" + val);
                posts.add(jsonToPost(jsonObject));
            }
            return posts;
        } catch (IOException e) {
            return null;
        }
    }

    public static Post jsonToPost(JSONObject jsonObject){
            String type = jsonObject.getString("type");

            ArrayList<LocalDate> dates = new ArrayList<>();
            JSONArray jsonDates = jsonObject.getJSONArray("dates");
            for (int j = 0; j < jsonDates.length(); j++) {
                dates.add(LocalDate.parse(jsonDates.getString(j)));
            }

            JSONArray jsonDatesOccupied = jsonObject.getJSONArray("datesOccupied");
            ArrayList<LocalDate> datesOccupied = new ArrayList<>();
            for (int j = 0; j < jsonDatesOccupied.length(); j++) {
                datesOccupied.add(LocalDate.parse(jsonDatesOccupied.getString(j)));
            }

            List<ApplicationToPost> applicationsToPost = jsonToApplications();
            ArrayList<Integer> applicationsId = new ArrayList<>();
            for (ApplicationToPost applicationToPost : applicationsToPost) {
                applicationsId.add(applicationToPost.getIdAppli());
            }

            State state = State.valueOf(jsonObject.getString("state"));

            List<Person> providers = null;
            try {
                int nbProviders = jsonObject.getJSONObject("providers").length();
                 providers = jsonToProviders(nbProviders, jsonObject.getJSONObject("providers"));
            }
            catch (Exception ignored) {

            }

            Image image;
            if (jsonObject.getString("path").isEmpty())
                image = null;
            else
                image = new Image(jsonObject.getString("path"));

            Type_Date type_date = Type_Date.valueOf(jsonObject.getString("type_date"));

            if (type.equals("service")) {
                return new Service(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.getString("description"),
                        jsonObject.getString("title"), jsonObject.getString("author_email"), dates, datesOccupied, type_date,
                        jsonToAdress(jsonObject.getJSONObject("address")), image, state, jsonObject.getString("descriptionService"), providers, applicationsId);
            }
            else if (type.equals("tool")) {
                return new Tool(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.getString("description"),
                        jsonObject.getString("title"), jsonObject.getString("author_email"), dates, datesOccupied, type_date,
                        jsonToAdress(jsonObject.getJSONObject("address")), image, state, jsonObject.getString("stateTool"), applicationsId);
            }
            else
                return null;
    }

    private static Address jsonToAdress(JSONObject address) {
        return new Address(Integer.parseInt(address.get("streetNumber").toString()), address.getString("streetName"),
                Integer.parseInt(address.get("postalCode").toString()), address.getString("city"), address.getString("region"),
                address.getString("country"));
    }

    public static JSONObject adressToJson(Address address) {
        JSONObject json = new JSONObject();
        try {
            json.put("streetNumber", address.getStreetNumber());
            json.put("streetName", address.getStreetName());
            json.put("postalCode", address.getPostalCode());
            json.put("city", address.getCity());
            json.put("region", address.getRegion());
            json.put("country", address.getCountry());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static JSONObject userToJson(User user) {
        JSONObject json = new JSONObject();
        try {
            json.put("firstName", user.getFirstName());
            json.put("lastName", user.getLastName());
            json.put("email", user.getEmail());
            json.put("pseudo", user.getPseudo());
            json.put("password", user.getPassword());
            json.put("address", adressToJson(user.getAddress()));
            if (user.getProfilePicture() != null)
                json.put("path", user.getProfilePicture().getUrl());
            else
                json.put("path", "");
            json.put("coins", user.getCoins() );
            json.put("isConnected", valueOf(user.isConnected()));
            json.put("evaluationList", user.getEvaluationList());
            json.put("posts", user.getPostedPosts());
            json.put("appliedToPosts", user.getAppliedToPosts());
            return json;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void usersToJson(ArrayList<User> users) {
        String path = "src/main/resources/json/users.json";

        JSONObject usersJson = new JSONObject();
        try {
            if (!users.isEmpty()){
                int i = 1;
                for (User user : users) {
                    JSONObject json = userToJson(user);
                    usersJson.put("user" + i++, json);
                }
            }
            else {
                usersJson.put("user0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(usersJson.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static User jsonToUser(JSONObject author) {
        Image image;
        if (author.getString("path").isEmpty())
            image = null;
        else
            image = new Image(author.getString("path"));

        User user = new User(author.getString("firstName"), author.getString("lastName"),
                author.getString("email"), author.getString("pseudo"),
                author.getString("password"), jsonToAdress(author.getJSONObject("address")),
                image);
        User.setNbUsers(User.getNbUsers() - 1);
        user.setCoins(author.getInt("coins"));
        user.setConnected("true".equals(author.getString("isConnected")));

        ArrayList<Integer> appliedPosts = new ArrayList<>();
        JSONArray jsonAppliedPosts = author.getJSONArray("appliedToPosts");
        for (int j = 0; j < jsonAppliedPosts.length(); j++) {
            appliedPosts.add(jsonAppliedPosts.getInt(j));
        }

        ArrayList<Integer> postedPosts = new ArrayList<>();
        JSONArray jsonPostedPosts = author.getJSONArray("posts");
        for (int j = 0; j < jsonPostedPosts.length(); j++) {
            postedPosts.add(jsonPostedPosts.getInt(j));
        }

        user.setAppliedToPosts(appliedPosts);
        user.setPostedPosts(postedPosts);

        return user;
    }

    public static ArrayList<User> jsonToUsers(){
        try {
            InputStream is = new FileInputStream("src/main/resources/json/users.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = buffer.readLine();
            StringBuilder builder = new StringBuilder();

            while(line != null){
                builder.append(line).append("\n");
                line = buffer.readLine();
            }

            ArrayList<User> users = new ArrayList<>();

            JSONObject json = new JSONObject(builder.toString());

            if (User.getNbUsers() == 0)
                return users;

            for (int i = 1; i <= User.getNbUsers() ; i++){
                JSONObject jsonObject = json.getJSONObject("user" + i);
                users.add(jsonToUser(jsonObject));
            }
            return users;
        } catch (IOException e) {
            return null;
        }
    }

    public static JSONObject messageToJson(Message message) {
        JSONObject json = new JSONObject();
        String path = "src/main/resources/json/messages.json";

        try {
            json.put("contenu", message.getContent());
            json.put("sender", message.getSender().getEmail());
            json.put("receiver", message.getReceiver().getEmail());
            json.put("date", message.getDate());
            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveMsgInJason(Message msg){
        String path = "src/main/resources/json/messages.json";
        ArrayList<Message> list = recupMsgData(path);
        list.add(msg);
        sendMsgInJason(list);
        Message.setNb_msgs(Message.getNb_msgs()+1);
    }

    public static ArrayList<Message> recupMsgData(String path){
        try {
            JSONObject json = readJsonFileFromPath(path);
            ArrayList<Message> list = new ArrayList<>();

            //int nb_msgs = Message.getNb_msgs();
            if (Message.getNb_msgs() == 0){
                return list;
            }
            System.out.println("jusquici ca va");
            for (int i = 1; i <= Message.getNb_msgs() ; i++){
                JSONObject jsonmsg = json.getJSONObject("message" + i);
                System.out.println("ici?");
                Message msg = jsonToMessage(jsonmsg);
                System.out.println("la?");
                //Message.setNb_msgs(Message.getNb_msgs()-1);
                list.add(msg);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error in jsonToUserList the JSONFile", e);
        }
    }

    public static Message jsonToMessage(JSONObject jsonmsg){
        String sendermail = jsonmsg.getString("sender");
        String receivermail = jsonmsg.getString("receiver");
        System.out.println("check les mails recup" + sendermail + " et " + receivermail);
        User sender = getUserFromMail(sendermail);
        User receiver = getUserFromMail(receivermail);
        Message message = new Message(sender, receiver, jsonmsg.getString("contenu"), jsonmsg.getString("date"));
        return message;
    }

    public static JSONObject listMsgToObject(ArrayList<Message> list){
        String path = "src/main/resources/json/messages.json";
        JSONObject msgJson = new JSONObject();
        try {
            if (!list.isEmpty()){
                int index = 1;
                for (Message message : list) {
                    System.out.println("dans sendMsgInJason, mail = " + message.getSender().getEmail() + " et " + message.getReceiver().getEmail());
                    JSONObject jsonMsg = messageToJson(message);
                    msgJson.put("message" + index, jsonMsg);
                    index++;
                }
            }
            else {
                msgJson.put("message0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgJson;
    }
    public static void sendMsgInJason(ArrayList<Message> list){
        JSONObject msgJson = listMsgToObject(list);
        String path = "src/main/resources/json/messages.json";
        writeJsonInJsonFile(path, msgJson);
    }

    public static User getUserFromMail(String mail){
        try {
            ArrayList<User> users = jsonToUsers();
            int i = 0;
            while ((i < User.getNbUsers()) && (!users.get(i).getEmail().equals(mail))) {
                i++;
            }
            User user = users.get(i);
            return user;
        }
        catch (Exception e){throw new RuntimeException("yeeeeeeeeeeeeeeeeeeeeeeeeeeet", e);} //TODO check si c'est ok comme ca (cas ou on creer un msg avec un user qui n'existe pas) (ne peut pas arriver normalement, msg dans discussion)
    }

    public static void writeJsonInJsonFile(String path, JSONObject json ){
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(json.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject readJsonFileFromPath (String path){
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = buffer.readLine();
            StringBuilder builder = new StringBuilder();

            while (line != null) {
                builder.append(line).append("\n");
                line = buffer.readLine();
            }

            JSONObject json = new JSONObject(builder.toString());
            return json;
        }

        catch (Exception e) {
            throw new RuntimeException("Error in readJsonFileFromPath", e);
        }
    }

    public static ArrayList<Conversation> recupConvFromJson(String path){
        try {
            JSONObject json = readJsonFileFromPath(path);
            ArrayList<Conversation> list = new ArrayList<>();

            if (Conversation.getNb_convs() == 0){
                return list;
            }
            for (int i = 1; i <= Conversation.getNb_convs() ; i++){
                JSONObject jsonConv = json.getJSONObject("conv" + i);
                Conversation conversation = jsonToConv(jsonConv);
                list.add(conversation);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error in jsonToConvList the JSONFile", e);
        }
    }

    public static JSONObject convToJson(Conversation conversation){
        JSONObject json = new JSONObject();
        String path = "src/main/resources/json/conversations.json";

        try {
            json.put("id", conversation.getId());
            json.put("user1", conversation.getUser1().getEmail());
            json.put("user2", conversation.getUser2().getEmail());      //TODO sale pour l'instant, je stock directement les messages dans conversation
            json.put("liste_msg", listMsgToObject(conversation.getMessages()));
            return json;
        }
        //msgJson.put("message" + index, jsonMsg);
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Conversation jsonToConv(JSONObject jsonConv){
        String user1mail = jsonConv.getString("user1");
        String user2mail = jsonConv.getString("user2");
        int id = jsonConv.getInt("id");
        //JSONObject msgs = jsonConv.getJSONObject("liste_msg");
        //ArrayList<Message> msg_liste = jsonToMsgList(msgs);
        System.out.println("check les mails recup" + user1mail + " et " + user2mail);
        User user1 = getUserFromMail(user1mail);
        User user2 = getUserFromMail(user2mail);
        Conversation conversation = new Conversation(user1, user2, messageFromConvBetween(user1, user2), id);
        //Conversation.setNb_convs(Conversation.getNb_convs() - 1);
        return conversation;
    }

    public static ArrayList<Message> messageFromConvBetween(User user1, User user2){
        String path = "src/main/resources/json/messages.json";
        ArrayList<Message> msgList = recupMsgData(path);
        ArrayList<Message> listfinale = new ArrayList<Message>();
        for (Message msg : msgList){
            System.out.println("message contenu : " + msg.getContent());
            if (msg.getReceiver().getEmail().equals(user1.getEmail()) || msg.getReceiver().getEmail().equals(user2.getEmail())){
                if (msg.getSender().getEmail().equals(user1.getEmail()) || msg.getSender().getEmail().equals(user2.getEmail())){
                    System.out.println("message retenu : " + msg.getContent());
                    listfinale.add(msg);
                }
            }
        }
        return listfinale;
    }

    public static void saveConvInJson(Conversation conversation){
        String path = "src/main/resources/json/conversations.json";
        ArrayList<Conversation> convs = recupConvFromJson(path);
        convs.add(conversation);
        sendConvLInJson(convs);
        Conversation.setNb_convs(Conversation.getNb_convs() + 1);

    }

    public static void sendConvLInJson(ArrayList<Conversation> convs){
        JSONObject convsToSend = listConvToJsonObj(convs);
        String path = "src/main/resources/json/conversations.json";
        writeJsonInJsonFile(path, convsToSend);
    }

    public static JSONObject listConvToJsonObj(ArrayList<Conversation> convList){
        String path = "src/main/resources/json/conversations.json";
        JSONObject convJson = new JSONObject();
        try {
            if (!convList.isEmpty()){
                int index = 1;
                for (Conversation conversation : convList) {
                    //System.out.println("dans sendconvInJason, mail = " + message.getSender().getEmail() + " et " + message.getReceiver().getEmail());
                    JSONObject jsonConv = convToJson(conversation);
                    convJson.put("conv" + index, jsonConv);
                    index++;
                }
            }
            else {
                convJson.put("conv0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return convJson;
    }

    public static void actualiserConv(int id){
        String path = "src/main/resources/json/conversations.json";
        ArrayList<Conversation> convs = recupConvFromJson(path);
        int i = 0;
        while (i <convs.size() && convs.get(i).getId() != id){
            i++;
        }
        if (i == convs.size()){
            System.out.println("Conversation introuvable pour y ajouter un message");
        }
        else{
            //convs.get(i).addMessage(message);
            sendConvLInJson(convs);
        }

    }

    public static boolean conversationExiste(String mail, String user_email){
        String path = "src/main/resources/json/conversations.json";
        ArrayList<Conversation> convs = recupConvFromJson(path);
        for (Conversation conversation : convs){
            if (conversation.getUser1().getEmail().equals(mail) || conversation.getUser1().getEmail().equals(user_email)){
                if (conversation.getUser2().getEmail().equals(mail) || conversation.getUser2().getEmail().equals(user_email)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void delConv(Conversation conv){
        String path = "src/main/resources/json/conversations.json";
        ArrayList<Conversation> convs = recupConvFromJson(path);
        int i = 0;
        while (convs.get(i).getId() != conv.getId()) {
            i++;
        }
        convs.remove(i);
        while (i < convs.size()){
            convs.get(i).setId(convs.get(i).getId() - 1);
            i++;
        }
        Conversation.setNb_convs(Conversation.getNb_convs() - 1);

        JSONObject listConvJson = listConvToJsonObj(convs);
        writeJsonInJsonFile(path, listConvJson);
    }

    public static List<ApplicationToPost> jsonToApplications() {
        try {
            InputStream is = new FileInputStream("src/main/resources/json/applications.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = buffer.readLine();
            StringBuilder builder = new StringBuilder();

            while(line != null){
                builder.append(line).append("\n");
                line = buffer.readLine();
            }

            List<ApplicationToPost> applications = new ArrayList<>();

            JSONObject json = new JSONObject(builder.toString());

            if (ApplicationToPost.getNbAppli() == 0)
                return applications;

            for (int i = 0; i < ApplicationToPost.getListId().size() ; i++){
                int val = ApplicationToPost.getListId().get(i);
                JSONObject jsonObject = json.getJSONObject("application" + val);
                applications.add(jsonToApplication(jsonObject));

            }
            return applications;
        } catch (IOException e) {
            return null;
        }
    }

    public static ApplicationToPost jsonToApplication(JSONObject jsonObject) {
        String applicantEmail = jsonObject.getString("applicant");
        String comment = jsonObject.getString("comment");

        ArrayList<LocalDate> dates = new ArrayList<>();
        JSONArray jsonDates = jsonObject.getJSONArray("dates");
        for (int j = 0; j < jsonDates.length(); j++) {
            dates.add(LocalDate.parse(jsonDates.getString(j)));
        }

        int id = jsonObject.getInt("id");
        boolean isAccepted = jsonObject.getBoolean("isAccepted");

        return new ApplicationToPost(id, isAccepted, applicantEmail, dates, comment);
    }

    public static JSONObject applicationToJson(ApplicationToPost application) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", application.getIdAppli());
            json.put("applicant", application.getApplicantEmail());
            json.put("comment", application.getComment());
            json.put("dates", application.getDates());
            json.put("isAccepted", application.isAccepted());
            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void applicationsToJson(List<ApplicationToPost> applications) {
        String path = "src/main/resources/json/applications.json";

        JSONObject applicationsJson = new JSONObject();
        try {
            if (!applications.isEmpty()){
                int i = 1;
                for (ApplicationToPost application : applications) {
                    JSONObject json = applicationToJson(application);
                    applicationsJson.put("application" + i++, json);
                }
            }
            else {
                applicationsJson.put("application0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(applicationsJson.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}







