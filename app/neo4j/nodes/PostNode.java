package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonProperty;
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

   //@RelationshipEntity(type = Commented.TYPE)
   //@JsonProperty("comments")
   //private Set<Commented> comments = new HashSet<>();

    @Relationship(type = Pinned.TYPE)
    @JsonProperty("pinnings")
    private Set<Pinned> pinned = new HashSet<>();

    @Relationship(type = Posted.TYPE,  direction = Relationship.INCOMING)
    @JsonProperty("posted")
    private Posted posted = null;

    public PostNode() {
    }

    public PostNode(Long id, String text, String title, String picture) {
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.setId(id);
    }


    public Pinned addPinned(UserNode userNode){
        Pinned pinned = new Pinned(userNode, this);
        this.pinned.add(pinned);
        return pinned;
    }


    public Posted addPosted(UserNode userNode){
        Posted posted = new Posted(userNode, this);
        this.posted = posted;
        return posted;
    }
}
