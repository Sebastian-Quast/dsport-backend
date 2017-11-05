package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.JsonCollectionSize;
import neo4j.relationships.Event.Created;
import neo4j.relationships.Event.Participate;
import neo4j.relationships.comment.Commented;
import neo4j.relationships.exercise.Performed;
import neo4j.relationships.exercise.Owns;
import neo4j.relationships.friendship.Friendship;
import neo4j.relationships.friendship.FriendshipRequest;
import neo4j.relationships.like.Like;
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

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email")
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

    @Relationship(type = Commented.TYPE)
    @JsonProperty("comments")
    @JsonCollectionSize
    private Set<Commented> comments;

    @Relationship(type = Like.TYPE)
    @JsonIgnore
    @JsonManagedReference
    private Set<Like> likes;

    @Relationship(type = Friendship.TYPE, direction = Relationship.UNDIRECTED)
    @JsonProperty("friendships")
    @JsonCollectionSize
    private Set<Friendship> friendships;

    @Relationship(type = Performed.TYPE)
    @JsonManagedReference
    @JsonIgnore
    private Set<Performed> performed;

    @Relationship(type = Owns.TYPE)
    @JsonManagedReference
    @JsonIgnore
    private Set<Owns> owns;

    @Relationship(type = Participate.TYPE)
    @JsonManagedReference
    @JsonIgnore
    private Set<Participate> participating;

    @Relationship(type = FriendshipRequest.TYPE)
    @JsonIgnore
    private Set<FriendshipRequest> friendshipsRequested;

    @Relationship(type = FriendshipRequest.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Set<FriendshipRequest> friendshipRequests;

    @Relationship(type = Created.TYPE)
    @JsonIgnore
    private Set<Created> events;


    public UserNode() {
        this.postings = new HashSet<>();
        this.pinnings = new HashSet<>();
        this.comments = new HashSet<>();
        this.friendshipsRequested = new HashSet<>();
        this.friendships = new HashSet<>();
        this.friendshipRequests = new HashSet<>();
        this.owns = new HashSet<>();
        this.performed = new HashSet<>();
        this.likes = new HashSet<>();
        this.events = new HashSet<>();
        this.participating = new HashSet<>();
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

    public void addPerformed(ExerciseUnitNode exerciseUnitNode) {
        this.performed.add(new Performed(this,exerciseUnitNode));
    }

    public void addParticipate(EventNode eventNode) {
        this.participating.add(new Participate(this,eventNode));
    }

    public void addEvent(EventNode eventNode){
        events.add(new Created(this, eventNode));
    }

    public void addExercise(ExerciseNode exerciseNode) {
        this.owns.add(new Owns(this,exerciseNode));
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

    public Set<Commented> getComments() {
        return comments;
    }

    public void setComments(Set<Commented> comments) {
        this.comments = comments;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(Set<Friendship> friendships) {
        this.friendships = friendships;
    }

    public Set<FriendshipRequest> getFriendshipsRequested() {
        return friendshipsRequested;
    }

    public Set<FriendshipRequest> getFriendshipRequests() {
        return friendshipRequests;
    }

    public Set<Performed> getPerformed() {
        return performed;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Created> getEvents() {
        return events;
    }

    public void setEvents(Set<Created> events) {
        this.events = events;
    }

    public Set<Participate> getParticipating() {
        return participating;
    }

    public void setParticipating(Set<Participate> participating) {
        this.participating = participating;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}