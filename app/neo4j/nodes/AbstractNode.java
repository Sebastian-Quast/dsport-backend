package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.entities.TimedEntity;
import neo4j.entities.UniqueEntity;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class AbstractNode extends TimedEntity {
    @JsonProperty("updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updated = new Date(System.currentTimeMillis());

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date now) {
        this.updated = now;
    }
}
