package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.nodes.PostNode;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.UserNode;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = Posted.TYPE)
public class Posted extends AbstractRelationship {

    public static final String TYPE = "POSTED";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("postNode")
    private PostNode postNode;

    public Posted() {
    }

    public Posted(UserNode userNode, PostNode postNode) {
        this.userNode = userNode;
        this.postNode = postNode;
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
