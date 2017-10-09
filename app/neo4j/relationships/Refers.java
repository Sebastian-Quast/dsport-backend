package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.CommentNode;
import neo4j.nodes.PostNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Refers.TYPE)
public class Refers extends AbstractRelationship {

    //TODO BUG CLASS CANNOT BE RENAMED OR DELETED OR MOVED TO ANOTHER PACKAGE CAUSES "CLASS NOT FOUND EXCEPTION"


    public static final String TYPE = "REFERS";

    @StartNode
    @JsonIgnore
    private CommentNode commentNode;

    @EndNode
    @JsonIgnore
    private PostNode postNode;

    public Refers() {
    }

    public Refers(PostNode postNode, CommentNode commentNode) {
        this.postNode = postNode;
        this.commentNode = commentNode;
    }

    public PostNode getPostNode() {
        return postNode;
    }

    public CommentNode getCommentNode() {
        return commentNode;
    }
}
