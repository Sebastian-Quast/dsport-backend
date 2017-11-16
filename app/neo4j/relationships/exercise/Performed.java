package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Performed.TYPE)
public class Performed extends AbstractRelationship {

    public static final String TYPE = "PERFORMED";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonIgnore
    private ExerciseUnitNode exerciseUnitNode;

    public Performed() {
    }

    public Performed(UserNode userNode, ExerciseUnitNode exerciseUnitNode) {
        this.userNode = userNode;
        this.exerciseUnitNode = exerciseUnitNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public ExerciseUnitNode getExerciseUnitNode() {
        return exerciseUnitNode;
    }

    public void setExerciseUnitNode(ExerciseUnitNode exerciseUnitNode) {
        this.exerciseUnitNode = exerciseUnitNode;
    }
}
