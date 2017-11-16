package neo4j.relationships.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.EventNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Participate.TYPE)
public class Participate extends AbstractRelationship{

    public static final String TYPE = "PARTICIPATING";

    @StartNode
    @JsonIgnore
    UserNode user;

    @EndNode
    EventNode event;

    public Participate() {
    }

    public Participate(UserNode user, EventNode event) {
        this.user = user;
        this.event = event;
    }

    public UserNode getUser() {
        return user;
    }

    public EventNode getEvent() {
        return event;
    }
}
