package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = OfType.TYPE)
public class OfType extends AbstractRelationship {

    public static final String TYPE = "OF_TYPE";

    @StartNode
    @JsonIgnore
    private ExerciseUnitNode exerciseUnitNode;

    @EndNode
    private ExerciseNode exerciseNode;

    public OfType() {
    }

    public OfType(ExerciseUnitNode exerciseUnitNode, ExerciseNode exerciseNode) {
        this.exerciseUnitNode = exerciseUnitNode;
        this.exerciseNode = exerciseNode;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }
}
