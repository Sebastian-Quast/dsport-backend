package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import json.JsonCollectionSize;
import neo4j.nodes.sets.AbstractSet;
import neo4j.nodes.sets.DistanceBasedSet;
import neo4j.nodes.sets.RepeatBasedSet;
import neo4j.nodes.sets.TimeBasedSet;
import neo4j.relationships.exercise.OfType;
import neo4j.relationships.exercise.Performed;
import neo4j.relationships.exercise.With;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class ExerciseUnitNode extends SocialNode {

    @Relationship(type = With.TYPE)
    @JsonCollectionSize
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


    public ExerciseUnitNode() {
        super();
        this.with = new HashSet<>();
    }

    public ExerciseUnitNode(Long id) {
        this();
        this.setId(id);
    }


    public void addSet(AbstractSet abstractSet){
        With with = new With(this, abstractSet);
        this.with.add(with);
    }

    public OfType getOfType() {
        return ofType;
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
