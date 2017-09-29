package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Pinned.TYPE)
public class Pinned extends AbstractRelationship {

    public static final String TYPE = "PINNED";

    @StartNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("postNode")
    private PostNode postNode;

    @EndNode
    @JsonIgnore
    private UserNode userNode;

    public Pinned() {
    }

    public Pinned(UserNode userNode, PostNode postNode) {
        this.postNode = postNode;
        this.userNode = userNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public PostNode getPostNode() {
        return postNode;
    }

    public void setPostNode(PostNode postNode) {
        this.postNode = postNode;
    }
}
