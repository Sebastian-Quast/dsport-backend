package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Friendship;
import neo4j.relationships.Posted;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;


@NodeEntity(label = "User")
public class User extends AbstractNode {

    @JsonProperty("name")
    private String name;

    @Relationship(type = Friendship.TYPE)
    @JsonProperty("friendships")
    private Set<Friendship> friendships = new HashSet<>();

    @Relationship(type = Posted.TYPE)
    @JsonProperty("postings")
    private Set<Posted> postings = new HashSet<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
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

}
