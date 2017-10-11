package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class DistanceBasedSet extends AbstractNode {

    @JsonProperty("time")
    private String time;

    @JsonProperty("distance")
    private String distance;

    public DistanceBasedSet() {
    }

    public DistanceBasedSet(Long id, String time, String distance) {
        this.time = time;
        this.distance = distance;
        this.setId(id);
    }
}
