package neo4j.relationships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = Friendship.TYPE)
public class Friendship extends AbstractRelationship {

    public static final String TYPE = "FRIENDSHIP";

    @StartNode
    @JsonIgnore
    private User user;

    @EndNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("friend")
    private User friend;

    public Friendship() {
    }

    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}
