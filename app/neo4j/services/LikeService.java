package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Like;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class LikeService extends AbstractRelationshipService<Like> {

    @Inject
    public LikeService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<Like> getEntityType() {
        return Like.class;
    }

    public Optional<Like> findLike(UserNode userNode, AbstractNode node){
        String query = "Match(n:UserNode)-[l:LIKE]->(p:"+node.getLabel()+") "+
                "WHERE ID(n)="+userNode.getId()+" AND ID(p)="+node.getId()+"  "+
                "return n,l,p";
        return Optional.ofNullable(session.queryForObject(Like.class,query, Collections.emptyMap()));
    }

}
