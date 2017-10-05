package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Commented;
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

    @Relationship(type = Friendship.TYPE, direction = Relationship.UNDIRECTED)
    @JsonProperty("friendships")
    private Set<Friendship> friendships = new HashSet<>();

    @JsonProperty("postings")
    @Relationship(type = Posted.TYPE)
    private Set<Posted> postings;

    @Relationship(type = Pinned.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("pinnings")
    private Set<Pinned> pinnings;

    @Relationship(type= Commented.TYPE)
    @JsonProperty("comments")
    private Set<Commented> comments;

    //@Relationship(type = Friendship.TYPE)
    //@JsonProperty("friendshipRequested")
    //private Set<FriendshipRequest> friendshipsRequested = new HashSet<>();

    //@Relationship(type = Friendship.TYPE, direction = Relationship.INCOMING)
    //@JsonProperty("friendshipRequests")
    //private Set<FriendshipRequest> friendshipRequests = new HashSet<>();

    public UserNode() {
        this.postings = new HashSet<>();
        this.pinnings = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public UserNode(String username, String firstname, String lastname, String email, String password, String picture) {
        this();
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

    public Set<Posted> getPostings() {
        return postings;
    }

    public void setPostings(Set<Posted> postings) {
        this.postings = postings;
    }

    public Set<Pinned> getPinnings() {
        return pinnings;
    }

    public void setPinnings(Set<Pinned> pinnings) {
        this.pinnings = pinnings;
    }

    public Set<Commented> getComments() {
        return comments;
    }

    public void setComments(Set<Commented> comments) {
        this.comments = comments;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(Set<Friendship> friendships) {
        this.friendships = friendships;
    }
}