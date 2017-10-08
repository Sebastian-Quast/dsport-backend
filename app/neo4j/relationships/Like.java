package neo4j.relationships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Like.TYPE)
public class Like extends AbstractRelationship {

    public static final String TYPE = "LIKE";

    @StartNode
    //@JsonSerialize(as=UniqueEntity.class)
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonIgnore
    private AbstractNode endNode;

    public Like() {
    }

    public Like(UserNode userNode, AbstractNode endNode) {
        this.userNode = userNode;
        this.endNode = endNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }
}
