package ifpb.ads.pos.twitter.web;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 20/02/2018, 14:15:17
 */
@Named
@RequestScoped
public class ControladorTwitter {


    private Client builder = ClientBuilder.newClient();

//    public List<Friend> todos() {
    public String todos() {
//        return readmentions_timeline(getCredentials());
        return readTimeline(getCredentials());
//        return readTimelineFriends(getCredentials());
    }


    private String readTimeline(Credentials c) {
//    private List<Twitter> readTimeline(Credentials c) {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/statuses/user_timeline.json");
        String headerAuthorization = authenticator.in(endpoint).authenticate();

        WebTarget timelineTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json");
        Response time = timelineTarget.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();
            String readEntity = time.readEntity(String.class);
     
//        JsonArray readEntity = time.readEntity(JsonArray.class);
//        return readEntity.getValuesAs(JsonObject.class)
//                .stream()
//                .map((JsonObject t) -> new Twitter(t.getString("id_str"), t.getString("text")))
//                .collect(Collectors.toList());
//    String readEntity = time.readEntity(String.class);
     
        return readEntity;
    }
    private List<Friend> readTimelineFriends(Credentials c) {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/friends/list.json");
        String headerAuthorization = authenticator.in(endpoint).authenticate();

        WebTarget timelineTarget = builder.target("https://api.twitter.com/1.1/friends/list.json");
        Response time = timelineTarget.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();
        JsonObject readEntity = time.readEntity(JsonObject.class);
        
        JsonArray jsonArray = readEntity.getJsonArray("users");

        return jsonArray.getValuesAs(JsonObject.class)
                .stream()
                .map((JsonObject t) -> new Friend(t.getInt("id"), t.getString("name"),
                t.getInt("followers_count"),  t.getInt("friends_count")))
                .collect(Collectors.toList());
  
    }
        
    private String readmentions_timeline(Credentials c) {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/statuses/mentions_timeline.json");
        String headerAuthorization = authenticator.in(endpoint).authenticate();

        WebTarget timelineTarget = builder.target("https://api.twitter.com/1.1/statuses/mentions_timeline.json");
        Response time = timelineTarget.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();
//        JsonArray readEntity = time.readEntity(JsonArray.class);
        String readEntity = time.readEntity(String.class);
     
        return readEntity;
//                .stream()
////                .peek((JsonObject t) -> {
////                    JsonObject jsonObject = t.getJsonObject("entities");
//////                    System.out.print(jsonObject.getString("id_str"));
////                    System.out.print(" "+jsonObject);
//////                    System.out.println(" - "+jsonObject.getJsonArray("user_mentions"));
////                })
//                .map((JsonObject t) -> new Twitter(t.getString("id_str"),t.getString("user_mentions")))
//                .collect(Collectors.toList());
    }

    private Credentials getCredentials() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (Credentials) session.getAttribute("token");
    }
}
