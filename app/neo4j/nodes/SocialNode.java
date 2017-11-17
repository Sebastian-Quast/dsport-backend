package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Refers;
import neo4j.relationships.like.Like;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SocialNode extends AbstractNode implements Comparable<SocialNode>{

    @Relationship(type = Like.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("likes")
    @JsonCollectionSize
    private Set<Like> likes;

    @Relationship(type = Refers.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("comments")
    @JsonCollectionSize
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


    public int compareTo(SocialNode socialNode) {
        if (this.getCreated().after(socialNode.getCreated())){
            return -1;
        } else if (this.getCreated().before(socialNode.getCreated())) {
            return 1;
        } else {
            return 0;
        }

    }
}
