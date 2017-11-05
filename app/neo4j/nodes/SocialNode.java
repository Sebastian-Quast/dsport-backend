package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Refers;
import neo4j.relationships.like.Like;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

public class SocialNode extends AbstractNode {

    @Relationship(type = Like.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("likes")
    @JsonCollectionSize
    //@JsonBackReference
    private Set<Like> likes;

    @Relationship(type = Refers.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("comments")
    @JsonCollectionSize
    //@JsonBackReference
    private Set<Refers> comments;

    public SocialNode() {
        this.likes = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Refers> getComments() {
        return comments;
    }

    public void setComments(Set<Refers> comments) {
        this.comments = comments;
    }

    public void addLike(UserNode userNode){
        likes.add(new Like(userNode, this));
    }

    public void addComment(CommentNode commentNode){
        comments.add(new Refers(commentNode, this));
    }
}
