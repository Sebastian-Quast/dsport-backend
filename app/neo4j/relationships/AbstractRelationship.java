package neo4j.relationships;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.entities.TimedEntity;
import neo4j.entities.UniqueEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.time.LocalDateTime;

@RelationshipEntity(type = "RELATION")
public abstract class AbstractRelationship extends TimedEntity{

}
