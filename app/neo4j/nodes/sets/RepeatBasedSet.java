package neo4j.nodes.sets;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import play.data.validation.Constraints;

@NodeEntity
public class RepeatBasedSet extends AbstractSet {

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("repeats")
    private String repeats;

    public RepeatBasedSet() {
    }

    public RepeatBasedSet(Long id, String time, String weight, String repeats) {
        super(id, time);
        this.weight = weight;
        this.repeats = repeats;
    }

    @Override
    public boolean checkCompleteness() {
        return super.checkCompleteness() && weight != null && repeats != null;
    }
}
