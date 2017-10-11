package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.like.LikePost;
import neo4j.relationships.Refers;
import neo4j.relationships.post.Pinned;
import neo4j.relationships.post.Posted;
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
    @JsonCollectionSize
    private Set<Refers> comments;

    @Relationship(type = Posted.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Posted posted;

    @Relationship(type = Pinned.TYPE)
    @JsonProperty("pinned")
    @JsonCollectionSize
    protected Set<Pinned> pinned;

    @Relationship(type = LikePost.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("likes")
    @JsonCollectionSize
    @JsonBackReference
    private Set<LikePost> likes;


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

    public Set<LikePost> getLikes() {
        return likes;
    }

    public void setLikes(Set<LikePost> likes) {
        this.likes = likes;
    }


}