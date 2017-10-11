package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Exercised.TYPE)
public class Exercised extends AbstractRelationship {

    public static final String TYPE = "EXERCISED";

    @StartNode
    @JsonIgnore
    private UserNode userNode;

    @EndNode
    @JsonIgnore
    private ExerciseNode exerciseNode;

    public Exercised() {
    }

    public Exercised(UserNode userNode, ExerciseNode exerciseNode) {
        this.userNode = userNode;
        this.exerciseNode = exerciseNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }

    public void setExerciseNode(ExerciseNode exerciseNode) {
        this.exerciseNode = exerciseNode;
    }
}
