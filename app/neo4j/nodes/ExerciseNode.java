package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.SetType;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.exercise.OfType;
import neo4j.relationships.exercise.Owns;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class ExerciseNode extends AbstractNode{

    @JsonProperty
    String name;

    @JsonProperty
    private String setType;

    @Relationship(type = Owns.TYPE, direction = Relationship.INCOMING)
    private Owns ownedBy;

    @Relationship(type = OfType.TYPE, direction = Relationship.INCOMING)
    private Set<OfType> usedBy;

    public ExerciseNode() {
        this.usedBy = new HashSet<>();
    }

    public ExerciseNode(Long id, String name, SetType setType) {
        this.name = name;
        this.setType = setType.name();
        setId(id);
    }

    public SetType getSetType() {
        return SetType.valueOf(setType);
    }


}
