package eu.telecomnancy.labfx.controller.utils;

import eu.telecomnancy.labfx.model.*;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
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

            for (int i = 1; i <= Post.getNbPosts() ; i++){
                JSONObject jsonObject = json.getJSONObject("post" + i);
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
                        jsonObject.getString("title"), jsonObject.getString("author_email"), dates, type_date,
                        jsonToAdress(jsonObject.getJSONObject("address")), image, state, jsonObject.getString("descriptionService"), providers);
            }
            else if (type.equals("tool")) {
                return new Tool(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.getString("description"),
                        jsonObject.getString("title"), jsonObject.getString("author_email"), dates, type_date,
                        jsonToAdress(jsonObject.getJSONObject("address")), image, state, jsonObject.getString("stateTool"));
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
            json.put("id", message.getId());
            json.put("contenu", message.getContent());
            json.put("sender", message.getSender());
            json.put("receiver", message.getReceiver());
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
        sendMsgInJason(list, path);
    }

    public static ArrayList<Message> recupMsgData(String path){
        try {
            JSONObject json = readJsonFileFromPath(path);
            ArrayList<Message> list = new ArrayList<>();

            int nb_msgs = Message.getNb_msgs();
            if (nb_msgs == 0){
                return list;
            }

            for (int i = 1; i <= nb_msgs ; i++){
                JSONObject jsonmsg = json.getJSONObject("message" + i);
                Message msg = jsonToMessage(jsonmsg);
                Message.setNb_msgs(Message.getNb_msgs()-1);
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
        User sender = getUserFromMail(sendermail);
        User receiver = getUserFromMail(receivermail);
        Message message = new Message(sender, receiver, jsonmsg.getString("contenu"), jsonmsg.getString("date"));
        return message;
    }

    public static void sendMsgInJason(ArrayList<Message> list, String path){
        JSONObject msgJson = new JSONObject();
        try {
            if (!list.isEmpty()){
                int index = 1;
                for (Message message : list) {
                    JSONObject jsonMsg = messageToJson(message);
                    msgJson.put("message" + index, jsonMsg);
                    index++;
                }
            }
            else {
                msgJson.put("user0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        writeJsonInJsonFile(path, msgJson);
    }

    public static User getUserFromMail(String mail){
        try {
            ArrayList<User> users = JsonUtil.jsonToUsers();
            int i = 0;
            int nb_user = User.getNbUsers();
            while ((i < nb_user) && (!users.get(i).getEmail().equals(mail))) {
                i++;
            }
            User user = users.get(i);
            return user;
        }
        catch (Exception e){throw new RuntimeException(e);} //TODO check si c'est ok comme ca (cas ou on creer un msg avec un user qui n'existe pas) (ne peut pas arriver normalement, msg dans discussion)
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
}







