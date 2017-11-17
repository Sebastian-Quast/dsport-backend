package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Event.Arrange;
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
    private String time;

    @JsonProperty
    private String locationAdress;

    @JsonProperty
    private String locationName;

    @JsonProperty
    private String picture;

    @JsonProperty
    private String event_picture;

    @Relationship(type = Arrange.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Arrange arranged;

    @Relationship(type = Participate.TYPE, direction = Relationship.INCOMING)
    @JsonCollectionSize
    private Set<Participate> participates;


    public EventNode() {
        super();
        this.participates = new HashSet<>();
    }

    public EventNode(Long id, String title, String text, String time, String locationName, String locationAdress, String picture, String event_picture) {
        this();
        this.title = title;
        this.text = text;
        this.time = time;
        this.locationName = locationName;
        this.locationAdress = locationAdress;
        this.picture = picture;
        this.event_picture = event_picture;
        this.setId(id);
    }

    public EventNode(EventNode eventNode) {
        this.title = eventNode.getTitle();
        this.text = eventNode.getText();
        this.time = eventNode.getTime();
        this.locationName = eventNode.getLocationName();
        this.locationAdress = eventNode.getLocationAdress();
        this.picture = eventNode.getPicture();
        this.event_picture = eventNode.getEvent_picture();
        this.arranged = eventNode.getArranged();
        this.participates = eventNode.getParticipates();
        this.setLikes(eventNode.getLikes());
        this.setComments(eventNode.getComments());
        this.setId(eventNode.getId());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocationAdress() {
        return locationAdress;
    }

    public void setLocationAdress(String locationAdress) {
        this.locationAdress = locationAdress;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEvent_picture() {
        return event_picture;
    }

    public void setEvent_picture(String event_picture) {
        this.event_picture = event_picture;
    }

    public Arrange getArranged() {
        return arranged;
    }

    public void setArranged(Arrange arranged) {
        this.arranged = arranged;
    }

    public void setParticipates(Set<Participate> participates) {
        this.participates = participates;
    }

    public Set<Participate> getParticipates() {
        return participates;
    }
}
