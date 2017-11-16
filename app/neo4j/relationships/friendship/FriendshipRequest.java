package neo4j.relationships.friendship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = FriendshipRequest.TYPE)
public class FriendshipRequest extends AbstractRelationship {

    public static final String TYPE = "FRIENDSHIP_REQUEST";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonProperty("friend")
    private UserNode friend;

    public FriendshipRequest() {
    }

    public FriendshipRequest(UserNode userNode, UserNode friend) {
        this.userNode = userNode;
        this.friend = friend;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public UserNode getFriend() {
        return friend;
    }

    public void setFriend(UserNode friend) {
        this.friend = friend;
    }
}
