package neo4j.relationships.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.CommentNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = LikeComment.TYPE)
public class LikeComment extends AbstractRelationship {

    public static final String TYPE = "LIKE_COMMENT";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonIgnore
    private CommentNode comment;

    public LikeComment() {
    }

    public LikeComment(UserNode userNode, CommentNode comment) {
        this.userNode = userNode;
        this.comment = comment;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public CommentNode getEndNode() {
        return comment;
    }

    public void setEndNode(CommentNode commentNode) {
        this.comment = commentNode;
    }
}
