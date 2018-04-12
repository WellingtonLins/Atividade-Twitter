package ifpb.ads.pos.twitter.web;

/**
 *
 * @author wellington
 */
public class DadoTwitter {
  private String  from;//Logged user name
  private int fromId;//Logged user id
  private String tweetId; 
  private String userId;// User that has been Influenced
  private Tipo tipo; 

    public DadoTwitter(String from, int fromId, String tweetId, String userId, Tipo tipo) {
        this.from = from;
        this.fromId = fromId;
        this.tweetId = tweetId;
        this.userId = userId;
        this.tipo = tipo;
    }

  
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
  
  

   


}
