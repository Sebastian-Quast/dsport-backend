package neo4j.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.Property;

import java.time.LocalDateTime;

public abstract class TimedEntity extends UniqueEntity {

    @Property
    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime created = LocalDateTime.now();

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
