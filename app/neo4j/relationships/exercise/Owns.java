package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.UserNode;
import neo4j.nodes.ExerciseNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Owns.TYPE)
public class Owns extends AbstractRelationship {

    public static final String TYPE = "OWNS";

    @StartNode
    @JsonIgnore
    UserNode userNode;

    @EndNode
    ExerciseNode exerciseNode;

    public Owns() {
    }

    public Owns(UserNode userNode, ExerciseNode exerciseNode) {
        this.userNode = userNode;
        this.exerciseNode = exerciseNode;
    }
}
