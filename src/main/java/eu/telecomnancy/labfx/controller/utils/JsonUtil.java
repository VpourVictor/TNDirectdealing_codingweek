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

public class JsonUtil {
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
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public static void postToJson(Post post) {
        String path = "src/main/resources/json/posts.json";
        System.out.println(post.getClass());

        JSONObject postJson = new JSONObject();
        try {
            JSONObject json = new JSONObject();
            json.put("id", post.getIdPost());
            json.put("title", post.getTitle());
            json.put("description", post.getDescription());
            json.put("author", userToJson(post.getAuthor()));
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
            postJson.put("post " + post.getIdPost(), json);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(postJson.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Post> jsonToPost(){
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

            for (int i = 1; i <= Post.getNbPosts(); i++){
                System.out.println("post " + i);
                if (json.get("post " + i) != null) {
                    json = json.getJSONObject("post " + i);
                    String type = json.getString("type");
                    System.out.println(type);
                    LocalDate startDate = LocalDate.parse(json.getString("startDate"));
                    LocalDate endDate;
                    if (json.getString("endDate").isEmpty())
                        endDate = null;
                    else
                        endDate = LocalDate.parse(json.getString("endDate"));

                    State state = State.valueOf(json.getString("state"));

                    Image image;
                    if (json.getString("path").isEmpty())
                        image = null;
                    else
                        image = new Image(json.getString("path"));

                    if (type.equals("service")) {
                        System.out.println("add service");
                        posts.add(new Service(json.getString("description"), json.getString("title"), jsonToUser(json.getJSONObject("author")), startDate, endDate, jsonToAdress(json.getJSONObject("address")), image, state, json.getString("descriptionService")));
                    }
                    else if (type.equals("tool")) {
                        System.out.println("add tool");
                        posts.add(new Tool(json.getString("description"), json.getString("title"), jsonToUser(json.getJSONObject("author")), startDate, endDate, jsonToAdress(json.getJSONObject("address")), image, state, json.getString("stateTool")));
                    }
                }
            }

            for (Post post : posts)
                System.out.println(post.getIdPost());

            return posts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static User jsonToUser(JSONObject author) {
        Image image;
        if (author.getString("path").isEmpty())
            image = null;
        else
            image = new Image(author.getString("path"));
        return new User(author.getString("firstName"), author.getString("lastName"),
                author.getString("email"), author.getString("pseudo"),
                author.getString("password"), jsonToAdress(author.getJSONObject("address")),
                image);
    }

    private static Address jsonToAdress(JSONObject address) {
        return new Address(Integer.parseInt(address.get("streetNumber").toString()), address.getString("streetName"),
                Integer.parseInt(address.get("postalCode").toString()), address.getString("city"), address.getString("region"),
                address.getString("country"));
    }
}
