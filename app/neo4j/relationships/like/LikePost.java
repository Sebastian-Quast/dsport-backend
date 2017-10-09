package neo4j.relationships.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = LikePost.TYPE)
public class LikePost extends AbstractRelationship {

    public static final String TYPE = "LIKE_POST";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonIgnore
    private PostNode post;

    public LikePost() {
    }

    public LikePost(UserNode userNode, PostNode post) {
        this.userNode = userNode;
        this.post = post;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public PostNode getEndNode() {
        return post;
    }

    public void setEndNode(PostNode endNode) {
        this.post = endNode;
    }
}
