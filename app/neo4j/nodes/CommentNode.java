package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.*;
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

    @Relationship(type= CommentedPost.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("commentedPost")
    private CommentedPost commentedPost;

    @Relationship(type= CommentedComment.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("commentedComment")
    @JsonCollectionSize
    private Set<CommentedComment> commentedComment;

    @Relationship(type = Refers.TYPE)
    @JsonProperty("refersPost")
    private Set<Refers> refers;

    @Relationship(type = RefersComment.TYPE)
    @JsonProperty("refersComment")
    @JsonCollectionSize
    private Set<RefersComment> refersComment;

    @Relationship(type = Like.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("likes")
    private Set<Like> likes;

    public CommentNode() {
        this.refers = new HashSet<>();
        this.refersComment = new HashSet<>();
        this.commentedComment = new HashSet<>();
        this.likes = new HashSet<>();
    }

    public CommentNode(Long id, String text, String title, String picture) {
        this();
        this.text = text;
        this.title = title;
        this.picture = picture;
        this.setId(id);
    }

    public void addCommentedPost(UserNode userNode) {
        CommentedPost commented = new CommentedPost(userNode, this);
        this.commentedPost = commented;
    }

    public void addCommentedComment(UserNode userNode) {
        CommentedComment commented = new CommentedComment(userNode, this);
        this.commentedComment.add(commented);
    }


    public void addRefersPost(PostNode postNodes) {
        Refers refers = new Refers(postNodes, this);
        this.refers.add(refers);
    }

    public void addRefersComment(CommentNode commentNode) {
        RefersComment refers = new RefersComment(this, commentNode);
        this.refersComment.add(refers);
    }

    public CommentedPost getCommentedPost() {
        return commentedPost;
    }

    public void setCommentedPost(CommentedPost commentedPost) {
        this.commentedPost = commentedPost;
    }

    public Set<CommentedComment> getCommentedComment() {
        return commentedComment;
    }

    public void setCommentedComment(Set<CommentedComment> commentedComment) {
        this.commentedComment = commentedComment;
    }

    public Set<Refers> getRefers() {
        return refers;
    }

    public void setRefers(Set<Refers> refers) {
        this.refers = refers;
    }

    public Set<RefersComment> getRefersComment() {
        return refersComment;
    }

    public void setRefersComment(Set<RefersComment> refersComment) {
        this.refersComment = refersComment;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String getLabel() {return "CommentNode";};
}