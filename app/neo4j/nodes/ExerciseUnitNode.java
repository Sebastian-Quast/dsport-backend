package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;
import neo4j.nodes.sets.AbstractSet;
import neo4j.nodes.sets.DistanceBasedSet;
import neo4j.nodes.sets.RepeatBasedSet;
import neo4j.nodes.sets.TimeBasedSet;
import neo4j.relationships.exercise.OfType;
import neo4j.relationships.exercise.With;
import neo4j.relationships.exercise.Performed;
import neo4j.relationships.like.LikeExercise;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class ExerciseUnitNode extends AbstractNode {


    @Relationship(type = With.TYPE)
    @JsonIgnore
    @JsonBackReference
    private Set<With> with;

    @Relationship(type = OfType.TYPE)
    @JsonIgnore
    @JsonBackReference
    private OfType ofType;

    @Relationship(type = Performed.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Performed performed;

    @Relationship(type = LikeExercise.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Set<LikeExercise> likes;


    public ExerciseUnitNode() {
        this.with = new HashSet<>();
    }

    public ExerciseUnitNode(Long id) {
        this.setId(id);
    }


    public void addSet(AbstractSet abstractSet){
        With with = new With(this, abstractSet);
        this.with.add(with);
    }

    public Set<With> getWith() {
        return with;
    }

    public void setOfType(OfType ofType) {
        this.ofType = ofType;
    }

    public Class<?> getSetType(){
        switch (ofType.getExerciseNode().getSetType()) {
            case TIME: return TimeBasedSet.class;
            case REPEAT: return RepeatBasedSet.class;
            case DISTANCE: return DistanceBasedSet.class;
        }
        return AbstractSet.class;
    }
}
