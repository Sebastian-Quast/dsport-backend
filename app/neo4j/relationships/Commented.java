package neo4j.relationships;


import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = Commented.TYPE)
public class Commented extends AbstractRelationship {

    public static final String TYPE = "COMMENTED";

}
