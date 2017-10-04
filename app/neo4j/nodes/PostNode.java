package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Refers;
import neo4j.relationships.Pinned;
import neo4j.relationships.Posted;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "PostNode")
public class PostNode extends AbstractNode {


    @JsonProperty("text")
    private String text;


    @JsonProperty("title")
    private String title;


    @JsonProperty("picture")
    private String picture;


    @JsonProperty("refers")
    @Relationship(type = Refers.TYPE, direction = Relationship.INCOMING)
    private Set<Refers> comments;

    @JsonProperty("posted")
    @Relationship(type = Posted.TYPE, direction = Relationship.INCOMING)
    private Set<Posted> posted;

    @JsonProperty("pinnings")
    @Relationship(type = Pinned.TYPE)
    private Set<Pinned> pinned;


    public PostNode() {
        this.pinned = new HashSet<>();
        this.posted = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public PostNode(Long id, String text, String title, String picture) {
        this();
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.setId(id);
    }


    public void addPinned(UserNode userNode) {
        Pinned pinned = new Pinned(userNode, this);
        this.pinned.add(pinned);
    }

    public void addPosted(UserNode userNode) {
        Posted posted = new Posted(userNode, this);
        this.posted.add(posted);
    }

    public Set<Refers> getComments() {
        return comments;
    }

    public void setComments(Set<Refers> comments) {
        this.comments = comments;
    }

    public Set<Pinned> getPinned() {
        return pinned;
    }

    public void setPinned(Set<Pinned> pinned) {
        this.pinned = pinned;
    }

    public Set<Posted> getPosted() {
        return posted;
    }

    public void setPosted(Set<Posted> posted) {
        this.posted = posted;
    }
}
