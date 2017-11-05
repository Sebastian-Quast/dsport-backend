package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.CommentNode;
import neo4j.nodes.PostNode;
import neo4j.nodes.SocialNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Refers.TYPE)
public class Refers extends AbstractRelationship {

    public static final String TYPE = "REFERS";

    @StartNode
    @JsonIgnore
    private CommentNode comment;

    @EndNode
    @JsonIgnore
    private SocialNode node;

    public Refers() {
    }

    public Refers(CommentNode comment, SocialNode node) {
        this.comment = comment;
        this.node = node;
    }

    public CommentNode getComment() {
        return comment;
    }

    public void setComment(CommentNode comment) {
        this.comment = comment;
    }

    public SocialNode getNode() {
        return node;
    }

    public void setNode(SocialNode node) {
        this.node = node;
    }
}
