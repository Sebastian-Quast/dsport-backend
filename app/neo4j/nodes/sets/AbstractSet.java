package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.relationships.exercise.With;
import org.neo4j.ogm.annotation.Relationship;

public class AbstractSet extends AbstractNode {

    @JsonProperty("time")
    private String time;


    @Relationship(type = With.TYPE, direction = Relationship.INCOMING)
    @JsonProperty
    private With exerciseUnitNode;

    public AbstractSet() {
    }

    public AbstractSet(Long id, String time) {
        this.time = time;
        setId(id);
    }

    public boolean checkCompleteness(){
        return time != null;
    }

}
