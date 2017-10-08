package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.CommentNode;
import neo4j.nodes.UserNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = CommentedPost.TYPE)
public class CommentedPost extends AbstractRelationship {

    public static final String TYPE = "COMMENTEDPOST";

    @StartNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("userNode")
    UserNode userNode;


    @EndNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("commentNode")
    CommentNode commentNode;


    public CommentedPost() {
    }

    public CommentedPost(UserNode userNode, CommentNode commentNode) {
        this.userNode = userNode;
        this.commentNode = commentNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public CommentNode getCommentNode() {
        return commentNode;
    }
}
