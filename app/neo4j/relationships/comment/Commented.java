package neo4j.relationships.comment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.CommentNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Commented.TYPE)
public class Commented extends AbstractRelationship {

    public static final String TYPE = "COMMENTED";

    @StartNode
    @JsonIgnore
    UserNode userNode;


    @EndNode
    @JsonIgnore
    CommentNode commentNode;


    public Commented() {
    }

    public Commented(UserNode userNode, CommentNode commentNode) {
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
