package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.sets.RepeatBasedSet;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type =  CompletedRepeadBased.TYPE)
public class CompletedRepeadBased extends AbstractRelationship {

    public static final String TYPE ="COMPLETED_DISTANCE_BASED";

    @StartNode
    @JsonIgnore
    private ExerciseNode exerciseNode;

    @EndNode
    @JsonIgnore
    private RepeatBasedSet repeatBasedSet;

    public CompletedRepeadBased() {
    }

    public CompletedRepeadBased(ExerciseNode exerciseNode, RepeatBasedSet repeatBasedSet) {
        this.exerciseNode = exerciseNode;
        this.repeatBasedSet = repeatBasedSet;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }

    public void setExerciseNode(ExerciseNode exerciseNode) {
        this.exerciseNode = exerciseNode;
    }

    public RepeatBasedSet getRepeatBasedSet() {
        return repeatBasedSet;
    }

    public void setRepeatBasedSet(RepeatBasedSet repeatBasedSet) {
        this.repeatBasedSet = repeatBasedSet;
    }
}
