package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.relationships.Event.Created;
import neo4j.relationships.Event.Participate;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class EventNode extends SocialNode {

    @JsonProperty
    private String title;

    @JsonProperty
    private String text;

    @JsonProperty
    private String date;

    @JsonProperty
    private String location;

    @JsonProperty
    private String picture;

    @JsonProperty
    private String event_picture;

    @Relationship(type = Created.TYPE, direction = Relationship.INCOMING)
    private Created created;

    @Relationship(type = Participate.TYPE, direction = Relationship.INCOMING)
    private Set<Participate> participates;


    public EventNode() {
        super();
        this.participates = new HashSet<>();
    }

    public EventNode(Long id,String title, String text, String date, String location, String picture, String event_picture) {
        this();
        this.title = title;
        this.text = text;
        this.date = date;
        this.location = location;
        this.picture = picture;
        this.event_picture = event_picture;
        this.setId(id);
    }

    public Set<Participate> getParticipates() {
        return participates;
    }
}
