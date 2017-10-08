package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Like;
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


    @Relationship(type = Refers.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("refersPost")
    //@JsonCollectionSize
    private Set<Refers> comments;

    @Relationship(type = Posted.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("posted")
    private Posted posted;

    @Relationship(type = Pinned.TYPE)
    @JsonProperty("pinned")
    //@JsonCollectionSize
    private Set<Pinned> pinned;

    @Relationship(type = Like.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("likes")
    private Set<Like> likes;


    public PostNode() {
        this.pinned = new HashSet<>();
        this.comments = new HashSet<>();
        this.likes = new HashSet<>();
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

    public Posted getPosted() {
        return posted;
    }

    public void setPosted(Posted posted) {
        this.posted = posted;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String getLabel() {
        return "PostNode";
    }
}