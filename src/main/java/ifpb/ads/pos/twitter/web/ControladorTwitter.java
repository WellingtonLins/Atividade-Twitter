package ifpb.ads.pos.twitter.web;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
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
@Named("controladorTwitter")
@RequestScoped
public class ControladorTwitter {

    private Client builder = ClientBuilder.newClient();

    public List<DadoTwitter> todos() {
//    public String todos() {
//        return readmentions_timeline(getCredentials());
        return readTimeline(getCredentials());
//        return readTimelineFriends(getCredentials());
    }
  public String redirect() {
       return redirect(getCredentials());
    }
 
    private List<DadoTwitter> readTimeline(Credentials c) {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/statuses/user_timeline.json");
        String headerAuthorization = authenticator.in(endpoint).authenticate();

        WebTarget timelineTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json");
        Response time = timelineTarget.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JsonArray readEntity = time.readEntity(JsonArray.class);
        System.out.print(readEntity);
        return readEntity.getValuesAs(JsonObject.class)
                .stream()
                .map((JsonObject t) -> new DadoTwitter(t.getJsonObject("user").getString("name"),
                t.getJsonObject("user").getInt("id"), t.getString("id_str"),
                t.getString("text"), t.getBoolean("retweeted")))
                .collect(Collectors.toList());

    }
  
    public String redirect(Credentials c) {

            AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);

//            EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=joenihon&count=5000");
            EndpointInTwitter endpoint = new EndpointInTwitter("GET", "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=joenihon&max_id=50");
            String headerAuthorization = authenticator.in(endpoint).authenticate();

            WebTarget timelineTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=joenihon&max_id=50");
            Response time = timelineTarget.request().accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", headerAuthorization)
                    .get();
   
 

         String readEntity = time.readEntity(String.class);
              System.out.print(readEntity);
            return  readEntity;
            
            
            
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
                t.getInt("followers_count"), t.getInt("friends_count")))
                .collect(Collectors.toList());

    }

    private Credentials getCredentials() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (Credentials) session.getAttribute("token");
    }
}
