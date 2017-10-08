package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.CommentNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = RefersComment.TYPE)
public class RefersComment extends AbstractRelationship {

    public static final String TYPE = "REFERSCOMMENT";

    @StartNode
    @JsonSerialize(as = UniqueEntity.class)
    @JsonProperty("commentNode2")
    private CommentNode commentNode1;

    @EndNode
    @JsonSerialize(as = UniqueEntity.class)
    @JsonProperty("commentNode2")
    private CommentNode commentNode2;

    public RefersComment() {
    }

    public RefersComment(CommentNode commentNode1, CommentNode commentNode2) {
        this.commentNode1 = commentNode1;
        this.commentNode2 = commentNode2;
    }

    public CommentNode getCommentNode1() {
        return commentNode1;
    }

    public void setCommentNode1(CommentNode commentNode1) {
        this.commentNode1 = commentNode1;
    }

    public CommentNode getCommentNode2() {
        return commentNode2;
    }

    public void setCommentNode2(CommentNode commentNode2) {
        this.commentNode2 = commentNode2;
    }
}
