package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class RepeatBasedSet extends AbstractNode {

    @JsonProperty("time")
    private String time;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("repeats")
    private String distance;

    public RepeatBasedSet() {
    }

    public RepeatBasedSet(Long id, String time, String weight, String distance) {
        this.time = time;
        this.weight = weight;
        this.distance = distance;
        this.setId(id);
    }
}
