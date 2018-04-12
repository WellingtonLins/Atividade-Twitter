package ifpb.ads.pos.twitter.web;

import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 20/02/2018, 15:01:51
 */
public class Twitter {

    private String id;
    private String text;
    private String mentions;

    public Twitter(String id) {
        this.id = id;
    }

    
//    public Twitter(String id, String text) {
//        this.id = id;
//        this.text = text;
//    }

    public Twitter(String id,String mentions) {
        this.id = id;
        this.text = text;
        this.mentions = mentions;
    }


    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

  

}
