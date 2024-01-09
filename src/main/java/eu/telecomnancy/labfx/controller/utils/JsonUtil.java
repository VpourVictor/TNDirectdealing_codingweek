package eu.telecomnancy.labfx.controller.utils;

import eu.telecomnancy.labfx.model.*;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

            for (int i = 1; i <= Post.getNbPosts() ; i++){
                JSONObject jsonObject = json.getJSONObject("post" + i);
                System.out.println(jsonObject.toString());
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
}
