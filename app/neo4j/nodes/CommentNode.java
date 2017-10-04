package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Refers;
import neo4j.relationships.Commented;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "CommentNode")
public class CommentNode extends AbstractNode {

    @JsonProperty("text")
    private String text;


    @JsonProperty("title")
    private String title;

    @JsonProperty("picture")
    private String picture;

    @Relationship(type="COMMENTED", direction = Relationship.INCOMING)
    private Set<Commented> commented;

    @Relationship(type = "REFERS")
    private Set<Refers> refers;

    public CommentNode() {
        this.refers = new HashSet<>();
        this.commented = new HashSet<>();
    }

    public CommentNode(Long id, String text, String title, String picture) {
        this();
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.setId(id);
    }

    public Commented addCommentedBy(UserNode userNode) {
        Commented commented = new Commented(userNode, this);
        this.commented.add(commented);
        return commented;
    }


    public Refers addCommented(PostNode postNodes) {
        Refers refers = new Refers(postNodes, this);
        this.refers.add(refers);
        return refers;
    }

    public Set<Commented> getCommented() {
        return commented;
    }

    public void setCommented(Set<Commented> commented) {
        this.commented = commented;
    }

    public Set<Refers> getRefers() {
        return refers;
    }

    public void setRefers(Set<Refers> refers) {
        this.refers = refers;
    }
}
