package neo4j.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;

public abstract class TimedEntity extends UniqueEntity {

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date created = new Date(System.currentTimeMillis());

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
