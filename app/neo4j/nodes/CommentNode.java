package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Refers;
import neo4j.relationships.comment.Commented;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "CommentNode")
public class CommentNode extends SocialNode {

    @JsonProperty("text")
    private String text;

    @JsonProperty("title")
    private String title;

    @JsonProperty("picture")
    private String picture;

    @Relationship(type= Commented.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Commented commented;

    @Relationship(type = Refers.TYPE)
    @JsonProperty("refers")
    private Refers refers;

    public CommentNode() {
        super();
    }

    public CommentNode(Long id, String text, String title, String picture) {
        this();
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.setId(id);
    }

    public void setCommented(UserNode userNode) {
        this.commented = new Commented(userNode, this);
    }

    public void setRefers(SocialNode socialNode) {
        this.refers = new Refers(this, socialNode);
    }
    public Commented getCommented() {
        return commented;
    }

    public void setCommented(Commented commented) {
        this.commented = commented;
    }

    public Refers getRefers() {
        return refers;
    }

    public void setRefers(Refers refers) {
        this.refers = refers;
    }

}