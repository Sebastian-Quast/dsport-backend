package neo4j.relationships.Event;

import neo4j.nodes.EventNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Created.TYPE )
public class Created extends AbstractRelationship {

    public static final String TYPE = "CREATED";

    @StartNode
    UserNode user;

    @EndNode
    EventNode event;

    public Created() {
    }

    public Created(UserNode user, EventNode event) {
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
