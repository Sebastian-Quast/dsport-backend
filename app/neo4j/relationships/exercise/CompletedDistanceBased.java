package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.sets.DistanceBasedSet;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type =  CompletedDistanceBased.TYPE)
public class CompletedDistanceBased extends AbstractRelationship {

    public static final String TYPE ="COMPLETED_DISTANCE_BASED";

    @StartNode
    @JsonIgnore
    private ExerciseNode exerciseNode;

    @EndNode
    @JsonIgnore
    private DistanceBasedSet distanceBasedSet;

    public CompletedDistanceBased() {
    }

    public CompletedDistanceBased(ExerciseNode exerciseNode, DistanceBasedSet distanceBasedSet) {
        this.exerciseNode = exerciseNode;
        this.distanceBasedSet = distanceBasedSet;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }

    public void setExerciseNode(ExerciseNode exerciseNode) {
        this.exerciseNode = exerciseNode;
    }

    public DistanceBasedSet getDistanceBasedSet() {
        return distanceBasedSet;
    }

    public void setDistanceBasedSet(DistanceBasedSet distanceBasedSet) {
        this.distanceBasedSet = distanceBasedSet;
    }
}
