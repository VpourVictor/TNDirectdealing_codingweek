package eu.telecomnancy.labfx.controller.utils;

import eu.telecomnancy.labfx.model.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.String.valueOf;

public class JsonUtil {
    // bonne version
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

    public static JSONObject providersToJson(ArrayList<User> users) {
        JSONObject usersJson = new JSONObject();
        try {
            if (!users.isEmpty()){
                for (User user : users) {
                    JSONObject json = providerToJson(user);
                    usersJson.put("provider" + user.getEmail(), json);
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

    // todo finir enregistrement des personnes prestataires
    private static JSONObject providerToJson(Person user) {
        JSONObject json = new JSONObject();
        try {
            json.put("firstName", user.getFirstName());
            json.put("lastName", user.getLastName());

            if (user instanceof User) {
                json.put("email", ((User) user).getEmail());
            }

            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static JSONObject postToJson(Post post) {
        JSONObject json = new JSONObject();

        try {
            json.put("id", post.getIdPost());
            json.put("title", post.getTitle());
            json.put("description", post.getDescription());
            json.put("author_email", post.getAuthorEmail());
            json.put("startDate", post.getDateCouple().getDateStart());
            if (post.getDateCouple().getDateEnd() != null)
                json.put("endDate", post.getDateCouple().getDateEnd());
            else
                json.put("endDate", "");
            json.put("address", adressToJson(post.getAddress()));
            if (post.getImage() != null)
                json.put("path", post.getImage().getUrl());
            else
                json.put("path", "");
            json.put("state", post.getState());

            if (post instanceof eu.telecomnancy.labfx.model.Service) {
                json.put("type", "service");
                json.put("descriptionService", post.getDescriptionService());
            }
            else if (post instanceof eu.telecomnancy.labfx.model.Tool) {
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
                String type = jsonObject.getString("type");
                LocalDate startDate = LocalDate.parse(jsonObject.getString("startDate"));
                LocalDate endDate;
                if (jsonObject.getString("endDate").isEmpty())
                    endDate = null;
                else
                    endDate = LocalDate.parse(jsonObject.getString("endDate"));

                State state = State.valueOf(jsonObject.getString("state"));

                Image image;
                if (jsonObject.getString("path").isEmpty())
                    image = null;
                else
                    image = new Image(jsonObject.getString("path"));

                if (type.equals("service")) {
                    posts.add(new Service(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.getString("description"),
                            jsonObject.getString("title"), jsonObject.getString("author_email"), startDate, endDate, jsonToAdress(jsonObject.getJSONObject("address")),
                            image, state, jsonObject.getString("descriptionService")));
                }
                else if (type.equals("tool")) {
                    posts.add(new Tool(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.getString("description"),
                            jsonObject.getString("title"), jsonObject.getString("author_email"), startDate, endDate,
                            jsonToAdress(jsonObject.getJSONObject("address")), image, state, jsonObject.getString("stateTool")));
                }
            }
            return posts;
        } catch (IOException e) {
            return null;
        }
    }

    private static Address jsonToAdress(JSONObject address) {
        return new Address(Integer.parseInt(address.get("streetNumber").toString()), address.getString("streetName"),
                Integer.parseInt(address.get("postalCode").toString()), address.getString("city"), address.getString("region"),
                address.getString("country"));
    }

    //Attention la fonction retourne le contenu sous la forme {email : "...", firstname : "...", ...}
    //Et non le {userX : {email : "...", firstname : "...", ...} sinon meme fonction mais deux lignes
    // à uncomment
    public static JSONObject userToJsonNotVoid(User user) {
        //JSONObject userJson = new JSONObject();
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
            json.put("isConnected", valueOf(user.isConnected()) );
            json.put("evaluationList", listDoubleToJson(user.getEvaluationList()));
            //json.put("postedPosts", listPostToJson(user.getPostedPosts()));
            //json.put("appliedToPosts", listPostToJson(user.getAppliedToPosts()));
            json.put("nbOfPostedPosts", user.getNbOfPostedPosts());
            json.put("nbOfAppliedToPosts", user.getNbOfAppliedToPosts());
            json.put("nbOfEvaluation", user.getNbOfEvaluation());
            //userJson.put("user" + user.getNbUsers(), json);
            //return userJson;
            return json;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void registerNewUser(User newUser){
        JSONObject json = userToJsonNotVoid(newUser);
        writeJsonInJsonFile("src/main/resources/json/users.json",json);

    }

    //Marche bien sans les listes de posts pour le moment
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
        user.setCoins(author.getInt("coins"));
        user.setConnected("true".equals(author.getString("isConnected")));
        user.setNbOfPostedPosts(author.getInt("nbOfPostedPosts"));
        user.setNbOfEvaluation(author.getInt("nbOfEvaluation"));
        user.setNbOfAppliedToPosts(author.getInt("nbOfAppliedToPosts"));
        //TODO il manque des champs là user.setPostedPosts(jsonToListPost(author.getString("postedPosts")))
        //user.setAppliedToPosts(jsonToListPost(author.getString("appliedToPosts")))
        //user.setEvaluationList(jsonToListDouble(author.getString("evaluationList")));
        return user;
    }

    public static void userListToJson(ArrayList<User> users, String path) {

        JSONObject usersJson = new JSONObject();
        try {
            if (!users.isEmpty()){
                int index = 1;
                for (User user : users) {
                    JSONObject jsonUser = userToJsonNotVoid(user);
                    usersJson.put("user" + index, jsonUser);
                    index++;
                }
            }
            else {
                usersJson.put("user0", "");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        writeJsonInJsonFile(path, usersJson );
    }

    //Marche bien sans les listes de posts pour le moment
    public static ArrayList<User> jsonToUserList(String path){
        try {
            JSONObject json = readJsonFileFromPath (path);
            ArrayList<User> users = new ArrayList<>();

            int nb_user = User.getNbUsers();
            if (nb_user == 0){
                return users;
            }
            //for (int i = 1; i <= 2 ; i++){
            //

            for (int i = 1; i <= User.getNbUsers() ; i++){
                System.out.println("user" + i);
                JSONObject jsonUser = json.getJSONObject("user" + i);
                User user = jsonToUser(jsonUser);
                User.setNbUsers(User.getNbUsers()-1);
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            throw new RuntimeException("Error in jsonToUserList the JSONFile", e);
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

    public static ArrayList<Post> jsonToPostsWithParameter(JSONObject jsonPostsList){
        try {
            ArrayList<Post> posts = new ArrayList<>();

            //JSONObject json = new JSONObject(builder.toString());

            if (Post.getNbPosts() == 0)
                return posts;

            for (int i = 1; i <= Post.getNbPosts() ; i++){
                JSONObject jsonOnePost = jsonPostsList.getJSONObject("post" + i);
                String type = jsonOnePost.getString("type");
                LocalDate startDate = LocalDate.parse(jsonOnePost.getString("startDate"));
                LocalDate endDate;
                if (jsonOnePost.getString("endDate").isEmpty())
                    endDate = null;
                else
                    endDate = LocalDate.parse(jsonOnePost.getString("endDate"));

                State state = State.valueOf(jsonOnePost.getString("state"));

                Image image;
                if (jsonOnePost.getString("path").isEmpty())
                    image = null;
                else
                    image = new Image(jsonOnePost.getString("path"));

                if (type.equals("service")) {
                    posts.add(new Service(Integer.parseInt(jsonOnePost.get("id").toString()), jsonOnePost.getString("description"),
                            jsonOnePost.getString("title"), jsonOnePost.getString("author_email"), startDate, endDate, jsonToAdress(jsonOnePost.getJSONObject("address")),
                            image, state, jsonOnePost.getString("descriptionService")));
                }
                else if (type.equals("tool")) {
                    posts.add(new Tool(Integer.parseInt(jsonOnePost.get("id").toString()), jsonOnePost.getString("description"),
                            jsonOnePost.getString("title"), jsonOnePost.getString("author_email"), startDate, endDate,
                            jsonToAdress(jsonOnePost.getJSONObject("address")), image, state, jsonOnePost.getString("stateTool")));
                }
            }
            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Error in jsonToPostsWithParameter the JSONFile", e);
        }
    }

    public static JSONObject listPostToJson(ArrayList<Post> listOfPost){
        JSONObject json = new JSONObject();
        try {
            int index = 0;
            if (listOfPost.isEmpty()){
                json.put("Post" , "");
            }
            else {
                for (Post post : listOfPost) {
                    json.put("Post" + String.valueOf(index), postToJsonNotVoid(post));
                    index++;
                }
            }
            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject postToJsonNotVoid(Post post) {

        try {
            JSONObject json = new JSONObject();
            json.put("id", post.getIdPost());
            json.put("title", post.getTitle());
            json.put("description", post.getDescription());
            json.put("author", post.getAuthorEmail());
            json.put("startDate", post.getDateCouple().getDateStart());

            if (post.getDateCouple().getDateEnd() != null)
                json.put("endDate", post.getDateCouple().getDateEnd());
            else
                json.put("endDate", "");
            json.put("address", adressToJson(post.getAddress()));
            if (post.getImage() != null)
                json.put("path", post.getImage().getUrl());
            else
                json.put("path", "");
            json.put("state", post.getState());

            if (post instanceof eu.telecomnancy.labfx.model.Service) {
                json.put("type", "service");
                json.put("descriptionService", post.getDescriptionService());
            }
            else if (post instanceof eu.telecomnancy.labfx.model.Tool) {
                json.put("type", "tool");
                json.put("stateTool", post.getStateTool());
            }

            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeJsonInJsonFile(String path, JSONObject json ){
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(json.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject listDoubleToJson(ArrayList<Double> listOfDouble){
        JSONObject json = new JSONObject();
        try {
            int index = 0;
            for (Double note : listOfDouble) {
                json.put("Note" + String.valueOf(index), String.valueOf(note));
                index++;
            }
            return json;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Double> jsonToListDouble(JSONObject json) {
        ArrayList<Double> listOfDouble = new ArrayList<>();
        try {
            for (String key : json.keySet()) {
                String valueAsString = json.getString(key);
                double doubleValue = Double.parseDouble(valueAsString);
                listOfDouble.add(doubleValue);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to list of doubles", e);
        }
        return listOfDouble;
    }

    public static ArrayList<Integer> jsonToListInteger(JSONObject json) {
        ArrayList<Integer> listOfInt = new ArrayList<>();
        try {
            for (String key : json.keySet()) {
                int idPost = json.getInt(key);
                listOfInt.add(idPost);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to list of integers", e);
        }
        return listOfInt;
    }

    public static JSONObject listIntegerToJson(ArrayList<Integer> listOfIntegers) {
        JSONObject json = new JSONObject();
        try {
            int index = 0;
            for (Integer val : listOfIntegers) {
                json.put("Post" + index, val);
                index++;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting list of integers to JSON", e);
        }
        return json;
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
        sendMsgInJason(list);
    }

    public static ArrayList<Message> recupMsgData(String path){
        try {
            JSONObject json = readJsonFileFromPath(path);
            ArrayList<Message> list = new ArrayList<>();

            int nb_msgs = Message.getNb_msgs();
            if (nb_msgs == 0){
                return list;
            }
            //for (int i = 1; i <= 2 ; i++){
            //

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
        Message message = new Message(jsonmsg.getString("sender"), jsonmsg.getString("receiver"),
                jsonmsg.getString("contenu"), jsonmsg.getString("date"));
        //user.setCoins(author.getInt("coins"));
        //user.setConnected("true".equals(author.getString("isConnected")));
        //user.setNbOfPostedPosts(author.getInt("nbOfPostedPosts"));
        //user.setNbOfEvaluation(author.getInt("nbOfEvaluation"));
        //user.setNbOfAppliedToPosts(author.getInt("nbOfAppliedToPosts"));
        //TODO il manque des champs là user.setPostedPosts(jsonToListPost(author.getString("postedPosts")))
        //user.setAppliedToPosts(jsonToListPost(author.getString("appliedToPosts")))
        //user.setEvaluationList(jsonToListDouble(author.getString("evaluationList")));
        return user;
    }

    public static void sendMsgInJason(ArrayList<Message> list){}



//    public static JSONObject readJsonFromFile(String fileName) {
//        try (FileReader fileReader = new FileReader(fileName)) {
//            int data;
//            StringBuilder jsonString = new StringBuilder();
//
//            while ((data = fileReader.read()) != -1) {
//                jsonString.append((char) data);
//            }
//
//            return new JSONObject(jsonString.toString());
//        } catch (Exception e) {
//            throw new RuntimeException("Error reading the JSONFile", e);
//        }
//    }
//
//    public static void writeJsonToFile(JSONObject json, String path) {
//        try (FileWriter fileWriter = new FileWriter(path)) {
//            fileWriter.write(json.toString());
//        } catch (Exception e) {
//            throw new RuntimeException("Error writing the JSONFile", e);
//        }
//    }
//
//    public static void addJsonUserInFile (JSONObject userJson, String path, int nbOfUsers){
//        JSONObject existingData = readJsonFromFile(path);
//
//        existingData.put("user" + nbOfUsers, userJson);
//        writeJsonToFile(existingData, path);
//    }
//
//    public static void betterRegisterNewUser(User newUser){
//        JSONObject json = userToJsonNotVoid(newUser);
//        addJsonUserInFile(json, "src/main/resources/json/users.json", newUser.getNbUsers() );
//
//    }

}





