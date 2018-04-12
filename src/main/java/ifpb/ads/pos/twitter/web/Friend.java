package ifpb.ads.pos.twitter.web;

/**
 *
 * @author wellington
 */
public class Friend {
    private int id;
    private String name;
    private int followers_count;
    private int friends_count;  

    
    public Friend(int id, String name, int followers_count, int friends_count) {
        this.id = id;
        this.name = name;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    @Override
    public String toString() {
        return "Friend{" + "id=" + id + ", name=" + name + ", followers_count=" + followers_count + ", friends_count=" + friends_count + '}';
    }
    
}