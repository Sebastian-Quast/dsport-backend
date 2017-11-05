package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.EventNode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class EventService extends AbstractService<EventNode> {

    @Inject
    public EventService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<EventNode> getEntityType() {
        return EventNode.class;
    }

    public Optional<Iterable<EventNode>> findEvents(String substring){
        //Case insensitive query for query substring
        String query = "MATCH (e:EventNode) WHERE e.title =~ '(?i)"+substring+".*' RETURN e ORDER BY e.title";
        return Optional.ofNullable(session.query(EventNode.class, query, Collections.emptyMap()));
    }
}
