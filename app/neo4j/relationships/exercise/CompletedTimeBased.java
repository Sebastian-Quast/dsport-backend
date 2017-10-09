package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.sets.TimeBasedSet;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type =  CompletedTimeBased.TYPE)
public class CompletedTimeBased extends AbstractRelationship {

    public static final String TYPE ="COMPLETED_DISTANCE_BASED";

    @StartNode
    @JsonIgnore
    private ExerciseNode exerciseNode;

    @EndNode
    @JsonIgnore
    private TimeBasedSet postNode;

    public CompletedTimeBased() {
    }

    public CompletedTimeBased(ExerciseNode exerciseNode, TimeBasedSet postNode) {
        this.exerciseNode = exerciseNode;
        this.postNode = postNode;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }

    public void setExerciseNode(ExerciseNode exerciseNode) {
        this.exerciseNode = exerciseNode;
    }

    public TimeBasedSet getPostNode() {
        return postNode;
    }

    public void setPostNode(TimeBasedSet postNode) {
        this.postNode = postNode;
    }
}
