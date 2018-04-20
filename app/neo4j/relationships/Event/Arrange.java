package neo4j.relationships.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.SourceTree;
import neo4j.nodes.EventNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Arrange.TYPE)
public class Arrange extends AbstractRelationship {

    public static final String TYPE = "ARRANGE";

    @StartNode
    @JsonIgnore
    UserNode user;

    @EndNode
    @JsonIgnore
    EventNode event;

    public Arrange() {
    }

    public Arrange(UserNode user, EventNode event) {
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
