package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.entities.TimedEntity;
import neo4j.entities.UniqueEntity;

import java.time.LocalDateTime;

public abstract class AbstractNode extends TimedEntity {

    @JsonProperty("updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updated = LocalDateTime.now();

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = LocalDateTime.now();
    }
}
