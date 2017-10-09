package neo4j.relationships.like;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = LikeExercise.TYPE)
public class LikeExercise extends AbstractRelationship {

    public static final String TYPE = "LIKE_ECERCISE";

    @StartNode
    @JsonSerialize(as=UniqueEntity.class)
    private UserNode userNode;

    @EndNode
    @JsonSerialize(as=UniqueEntity.class)
    private AbstractNode endNode;

    public LikeExercise() {
    }

    public LikeExercise(UserNode userNode, AbstractNode endNode) {
        this.userNode = userNode;
        this.endNode = endNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public AbstractNode getEndNode() {
        return endNode;
    }

    public void setEndNode(AbstractNode endNode) {
        this.endNode = endNode;
    }
}
