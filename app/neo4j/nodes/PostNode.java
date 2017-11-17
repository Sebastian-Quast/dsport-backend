package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Refers;
import neo4j.relationships.post.Pinned;
import neo4j.relationships.post.Posted;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "PostNode")
public class PostNode extends SocialNode {


    @JsonProperty("text")
    private String text;


    @JsonProperty("title")
    private String title;


    @JsonProperty("picture")
    private String picture;


    @Relationship(type = Posted.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Posted posted;

    @Relationship(type = Pinned.TYPE)
    @JsonProperty("pinned")
    @JsonCollectionSize
    private Set<Pinned> pinned;

    public PostNode() {
        super();
        this.pinned = new HashSet<>();
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
        this.posted = posted;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<Pinned> getPinned() {
        return pinned;
    }

    public void setPinned(Set<Pinned> pinned) {
        this.pinned = pinned;
    }

    public Posted getPosted() {
        return posted;
    }

    public void setPosted(Posted posted) {
        this.posted = posted;
    }

}