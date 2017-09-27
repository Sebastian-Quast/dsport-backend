package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Friendship;
import neo4j.relationships.Pinned;
import neo4j.relationships.Posted;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;


@NodeEntity(label = "UserNode")
public class UserNode extends AbstractNode {

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

    @Relationship(type = Pinned.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("pinnings")
    private Set<Pinned> pinnings = new HashSet<>();


    public UserNode() {
    }

    public UserNode(String firstname) {
        this.firstname = firstname;
    }

    public UserNode(String username, String firstname, String lastname, String email, String password, String picture) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public UserNode(Long id, String username, String firstname, String lastname, String email, String password, String picture) {
        this(username, firstname, lastname, email, password, picture);
        this.setId(id);
    }

    public Friendship addFriend(UserNode userNode){
        Friendship friendship = new Friendship(this, userNode);
        friendships.add(friendship);
        return friendship;
    }

    public Posted addPost(PostNode postNode){
        Posted posted = new Posted(this, postNode);
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public Set<Posted> getPostings() {
        return postings;
    }
}
