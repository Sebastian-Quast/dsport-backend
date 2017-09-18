package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Friendship;
import neo4j.relationships.Posted;
import neo4j.services.UserService;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import play.mvc.Result;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@NodeEntity(label = "User")
public class User extends AbstractNode {

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("picture")
    private String picture;

    @Relationship(type = Friendship.TYPE)
    @JsonProperty("friendships")
    private Set<Friendship> friendships = new HashSet<>();

    @Relationship(type = Posted.TYPE)
    @JsonProperty("postings")
    private Set<Posted> postings = new HashSet<>();

    public User() {
    }

    public User(String firstname) {
        this.firstname = firstname;
    }

    public User(String username, String firstname, String lastname, String email,String password, String picture) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public Friendship addFriend(User user){
        Friendship friendship = new Friendship(this, user);
        friendships.add(friendship);
        return friendship;
    }

    public Posted addPost(Post post){
        Posted posted = new Posted(this, post);
        this.postings.add(posted);
        return posted;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
