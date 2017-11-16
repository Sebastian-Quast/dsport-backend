package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.EventNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Event.Participate;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class ParticipateService extends AbstractRelationshipService<Participate> {


    @Inject
    public ParticipateService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<Participate> getEntityType() {
        return Participate.class;
    }


    public Optional<Participate> findExisting(UserNode userNode, EventNode node){
        String query = "Match(n:UserNode)-[l:PARTICIPATING]->(p:EventNode) "+
                "WHERE ID(n)="+userNode.getId()+" AND ID(p)="+node.getId()+"  "+
                "return n,l,p";
        return Optional.ofNullable(session.queryForObject(Participate.class,query, Collections.emptyMap()));
    }

    public Optional<EventNode> unparticipate(Participate participate){
        EventNode socialNode = participate.getEvent();
        session.delete(participate);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(session.load(EventNode.class, socialNode.getId()));
    }
}
