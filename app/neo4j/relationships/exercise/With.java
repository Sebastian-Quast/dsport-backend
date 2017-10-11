package neo4j.relationships.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.nodes.sets.AbstractSet;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type =  With.TYPE)
public class With extends AbstractRelationship {

    public static final String TYPE ="WITH";

    @StartNode
    @JsonIgnore
    private ExerciseUnitNode exerciseUnitNode;

    @EndNode
    @JsonIgnore
    private AbstractSet abstractSet;

    public With() {
    }

    public With(ExerciseUnitNode exerciseUnitNode, AbstractSet abstractSet) {
        this.exerciseUnitNode = exerciseUnitNode;
        this.abstractSet = abstractSet;
    }

    public AbstractSet getAbstractSet() {
        return abstractSet;
    }
}
