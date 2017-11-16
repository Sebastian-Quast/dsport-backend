package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class DistanceBasedSet extends AbstractSet {

    @JsonProperty("distance")
    private String distance;

    public DistanceBasedSet() {
    }

    public DistanceBasedSet(Long id, String time, String distance) {
        super(id,time);
        this.distance = distance;
    }

    @Override
    public boolean checkCompleteness() {
        return super.checkCompleteness() && distance != null;
    }
}
