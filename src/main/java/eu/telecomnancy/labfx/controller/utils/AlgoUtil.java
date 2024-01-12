package eu.telecomnancy.labfx.controller.utils;

import eu.telecomnancy.labfx.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

@Getter
@Setter
public class AlgoUtil {

    private ArrayList<User> users;
    private ArrayList<Post> posts;
    private ArrayList<ApplicationToPost> applications;

    public AlgoUtil() {
        this.users = JsonUtil.jsonToUsers();
        this.posts = JsonUtil.jsonToPosts();
        this.applications = (ArrayList<ApplicationToPost>) JsonUtil.jsonToApplications();
    }

    public AlgoUtil(ArrayList<?> list) {
        if (list.get(0) instanceof User) {
            this.users = (ArrayList<User>) list;
            this.posts = new ArrayList<>();
            this.applications = new ArrayList<>();
        } else if (list.get(0) instanceof Post) {
            this.posts = (ArrayList<Post>) list;
            this.users = new ArrayList<>();
            this.applications = new ArrayList<>();
        } else if (list.get(0) instanceof ApplicationToPost){
            this.applications = (ArrayList<ApplicationToPost>) list;
            this.users = new ArrayList<>();
            this.posts = new ArrayList<>();
        }
        else {
            throw new IllegalArgumentException("Invalid list type");
        }
    }

    public AlgoUtil(ArrayList<User> users, ArrayList<Post> posts) {
        this.users = users;
        this.posts = posts;
    }

    public ArrayList<Post> postSortedByRegion (String myRegion){
        ArrayList<Post> postFromMyRegion = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getAddress().getRegion().equals(myRegion)){
                postFromMyRegion.add(posts.get(i));
                posts.remove(i);
            }
            else {
                i++;
            }
        }
//        if (postFromMyRegion.isEmpty()){
//            return postFromMyRegion;
//        }
        return postFromMyRegion;
    }

    public ArrayList<Post> postSortedByType (String typeSearched){
        ArrayList<Post> postFromMyType = new ArrayList<>();
        int i = 0;
        if (typeSearched.equalsIgnoreCase("service") | typeSearched.equalsIgnoreCase("services") ){
            while (i < posts.size()){
                if (posts.get(i) instanceof Service){
                    postFromMyType.add(posts.get(i));
                    posts.remove(i);
                }
                else {
                    i++;
                }
            }
        }
        else if (typeSearched.equalsIgnoreCase("tool") | typeSearched.equalsIgnoreCase("tools") ){
            while (i < posts.size()){
                if (posts.get(i) instanceof Tool){
                    postFromMyType.add(posts.get(i));
                    posts.remove(i);
                }
                else {
                    i++;
                }
            }
        }
//        if (postFromMyType.isEmpty()){
//            return postFromMyType;
//        }
        return postFromMyType;
    }

    public ArrayList<Post> postSortedByCity (String myCity){
        ArrayList<Post> postFromMyCity = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getAddress().getCity().equals(myCity)){
                postFromMyCity.add(posts.get(i));
                posts.remove(i);
            }
            else {
                i++;
            }
        }
//        if (postFromMyCity.isEmpty()){
//            return postFromMyCity;
//        }
        return postFromMyCity;
    }

    public ArrayList<Post> postSortedByCountry (String myCountry){
        ArrayList<Post> postFromMyCountry = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getAddress().getCountry().equals(myCountry)){
                postFromMyCountry.add(posts.get(i));
                posts.remove(i);
            }
            else {
                i++;
            }
        }
//        if (postFromMyCountry.isEmpty()){
//            return null;
//        }
        return postFromMyCountry;
    }

    public ArrayList<Post> postSortedByUsers (){
        ArrayList<Post> postsByUsers = new ArrayList<>();
        for(User user: users){
            ArrayList<Post> postsFromUser = postFromUser(user);
            if (postsFromUser != null){
                postsByUsers.addAll(postsFromUser);
            }
        }
//        if (postsByUsers.isEmpty()){
//            return null;
//        }
        return postsByUsers;
    }

    public ArrayList<Post> postSortedByEvaluation (){
        this.users.sort(Comparator.comparingDouble(User::getEvaluation).reversed());
        return postSortedByUsers();
    }

    //Fonction qui renvoie le post correspondant au postId si il existe, sinon renvoie null
    public Post postFromIdPost(int idPost){
       for(Post post : posts){
           if (post.getIdPost() == idPost){
               return post;
           }
       }
       return null;
    }

    //Fonction qui renvoie les posts correspondant à une liste de idPost, sinon renvoie null
    public ArrayList<Post> postsFromIds(ArrayList<Integer> ids){
        ArrayList<Post> postIds = new ArrayList<>();
        for(Post post : posts){
            if (ids.contains(post.getIdPost())){
                postIds.add(post);
            }
        }
        return postIds;
    }

    public ArrayList<Post> postFromUser(User user){
        return postsFromIds(user.getPostedPosts());
    }

    public ArrayList<Post> postAppliedToByUser(User user){
        return postsFromIds(user.getAppliedToPosts());
    }

    public ArrayList<Post> postInState(State state){
        ArrayList<Post> postWithState = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getState() == state){
                postWithState.add(posts.get(i));
                posts.remove(i);
            }
            else {
                i++;
            }
        }
        return postWithState;
    }

    public ArrayList<Post> allExceptMasque(){
        ArrayList<Post> postWithState = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getState() == State.MASQUE){
                posts.remove(i);
            }
            else {
                postWithState.add(posts.get(i));
                i++;
            }
        }
        return postWithState;
    }

    public ArrayList<Post> postInVisible(){
        ArrayList<Post> postWithState = new ArrayList<>();
        int i = 0;
        while (i < posts.size()){
            if (posts.get(i).getState() != State.MASQUE && posts.get(i).getState() != State.TERMINE){
                postWithState.add(posts.get(i));
                posts.remove(i);
            }
            else {
                i++;
            }
        }
        return postWithState;
    }

    public User getUserFromMail(String mail){
        for (User user : users){
            if (user.getEmail().equals(mail)){
                return user;
            }
        }
        return null;
    }

    public static ArrayList<Double> createRandomDoubleArrayList(int size) {
        ArrayList<Double> doubleArrayList = new ArrayList<>(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            double randomValue = random.nextDouble() + random.nextDouble()*10; // Génère une valeur aléatoire entre 0.0 (inclus) et 1.0 (exclus)
            doubleArrayList.add(randomValue);
        }

        return doubleArrayList;
    }

    public User userConnected(ArrayList<User> users){
        for(User user: users ){
            if (user.isConnected()){
                return user;
            }
        }
        return users.get(0);
    }

    // récupère une ApplicationToPost à partir de son id
    public ApplicationToPost getApplicationToPostFromId(int id){
        for (ApplicationToPost applicationToPost : applications){
            if (applicationToPost.getIdAppli() == id){
                return applicationToPost;
            }
        }
        return null;
    }

    public void updatePostsAndCoins(){
        for(Post post : posts){
            LocalDate latestDate = Collections.max(post.getDates());
            if (latestDate.isBefore(LocalDate.now())){
                post.setState(State.TERMINE);
                ArrayList<ApplicationToPost> allApplications = getApplicationsFromPost(post);
                for(ApplicationToPost application : allApplications){
                    if (application.isAccepted()){
                        User helpingUser = getUserFromMail(application.getApplicantEmail());
                        User helpedUser = getUserFromMail(post.getAuthorEmail());
                        helpedUser.setCoins(helpedUser.getCoins()  - 25);
                        helpingUser.setCoins(helpingUser.getCoins() + 25);
                    }
                }

            }
        }
    }

    public ArrayList<ApplicationToPost> getApplicationsFromPost(Post post){
        ArrayList<ApplicationToPost> applicationsFromPost = new ArrayList<>();
        for (ApplicationToPost applicationToPost : applications){
            if (post.getApplications().contains(applicationToPost.getIdAppli())){
                applicationsFromPost.add(applicationToPost);
            }
        }
        if (applicationsFromPost.isEmpty()){
            return null;
        }
        return applicationsFromPost;
    }
}
