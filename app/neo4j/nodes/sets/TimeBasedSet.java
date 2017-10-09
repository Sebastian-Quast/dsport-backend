package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class TimeBasedSet extends AbstractNode {

    @JsonProperty("time")
    private String time;

    public TimeBasedSet() {
    }

    public TimeBasedSet(Long id, String time) {
        this.time = time;
        this.setId(id);
    }
}
