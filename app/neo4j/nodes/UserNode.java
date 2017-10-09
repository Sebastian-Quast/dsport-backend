package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.comment.CommentedComment;
import neo4j.relationships.comment.CommentedPost;
import neo4j.relationships.exercise.Exercised;
import neo4j.relationships.friendship.Friendship;
import neo4j.relationships.friendship.FriendshipRequest;
import neo4j.relationships.like.LikeComment;
import neo4j.relationships.like.LikePost;
import neo4j.relationships.post.Pinned;
import neo4j.relationships.post.Posted;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;


@NodeEntity(label = "UserNode")
public class UserNode extends AbstractNode {

    @JsonProperty("username")
    private String username;

    //@JsonProperty("firstname")
    @JsonIgnore
    private String firstname;

    //@JsonProperty("lastname")
    @JsonIgnore
    private String lastname;

    //@JsonProperty("email")
    @JsonIgnore
    private String email;

    //@JsonProperty("password")
    @JsonIgnore
    private String password;

    @JsonProperty("picture")
    private String picture;

    @Relationship(type = Posted.TYPE)
    @JsonProperty("postings")
    @JsonCollectionSize
    private Set<Posted> postings;

    @Relationship(type = Pinned.TYPE, direction = Relationship.INCOMING)
    @JsonProperty("pinnings")
    @JsonCollectionSize
    private Set<Pinned> pinnings;

    @Relationship(type = CommentedPost.TYPE)
    @JsonProperty("commentsByPost")
    @JsonCollectionSize
    private Set<CommentedPost> commentsByPost;

    @Relationship(type = CommentedComment.TYPE)
    @JsonProperty("commentsByComments")
    @JsonCollectionSize
    private Set<CommentedComment> commentsByComment;

    @Relationship(type = LikePost.TYPE)
    @JsonIgnore
    @JsonManagedReference
    private Set<LikePost> likesPost;

    @Relationship(type = LikeComment.TYPE)
    @JsonIgnore
    @JsonManagedReference
    private Set<LikeComment> likesComment;

    @Relationship(type = Friendship.TYPE, direction = Relationship.UNDIRECTED)
    @JsonProperty("friendships")
    @JsonCollectionSize
    private Set<Friendship> friendships;

    @Relationship(type = Exercised.TYPE)
    @JsonIgnore
    @JsonManagedReference
    private Set<Exercised> exercised;

    @Relationship(type = FriendshipRequest.TYPE)
    @JsonIgnore
    private Set<FriendshipRequest> friendshipsRequested;

    @Relationship(type = FriendshipRequest.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Set<FriendshipRequest> friendshipRequests;


    public UserNode() {
        this.postings = new HashSet<>();
        this.pinnings = new HashSet<>();
        this.commentsByPost = new HashSet<>();
        this.likesPost = new HashSet<>();
        this.likesComment = new HashSet<>();
        this.friendshipsRequested = new HashSet<>();
        this.friendships = new HashSet<>();
        this.friendshipRequests = new HashSet<>();
    }

    public UserNode(String username, String firstname, String lastname, String email, String password, String picture) {
        this();
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public UserNode(Long id, String username, String firstname, String lastname, String email, String password, String picture) {
        this(username, firstname, lastname, email, password, picture);
        this.setId(id);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public Set<Posted> getPostings() {
        return postings;
    }

    public void setPostings(Set<Posted> postings) {
        this.postings = postings;
    }

    public Set<Pinned> getPinnings() {
        return pinnings;
    }

    public void setPinnings(Set<Pinned> pinnings) {
        this.pinnings = pinnings;
    }

    public Set<CommentedPost> getCommentsByPost() {
        return commentsByPost;
    }

    public void setCommentsByPost(Set<CommentedPost> commentsByPost) {
        this.commentsByPost = commentsByPost;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(Set<Friendship> friendships) {
        this.friendships = friendships;
    }

    public Set<CommentedComment> getCommentsByComment() {
        return commentsByComment;
    }

    public void setCommentsByComment(Set<CommentedComment> commentsByComment) {
        this.commentsByComment = commentsByComment;
    }

    public Set<LikePost> getLikes() {
        return likesPost;
    }

    public void setLikes(Set<LikePost> likes) {
        this.likesPost = likes;
    }

    public Set<LikeComment> getLikesComment() {
        return likesComment;
    }

    public void setLikesComment(Set<LikeComment> likesComment) {
        this.likesComment = likesComment;
    }

    public Set<FriendshipRequest> getFriendshipsRequested() {
        return friendshipsRequested;
    }

    public Set<FriendshipRequest> getFriendshipRequests() {
        return friendshipRequests;
    }

    public Set<Exercised> getExercised() {
        return exercised;
    }
}