package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.like.Like;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class SocialService extends AbstractService<SocialNode> {

    @Inject
    public SocialService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<SocialNode> getEntityType() {
        return SocialNode.class;
    }

    public Optional<SocialNode> like(UserNode userNode, SocialNode socialNode){
            socialNode.addLike(userNode);
            return createOrUpdate(socialNode);
    }

    public Optional<Like> findExistingLike(UserNode userNode, SocialNode node){
        String query = "Match(n:UserNode)-[l:LIKE]->(p:SocialNode) "+
                "WHERE ID(n)="+userNode.getId()+" AND ID(p)="+node.getId()+"  "+
                "return n,l,p";
        return Optional.ofNullable(session.queryForObject(Like.class,query, Collections.emptyMap()));
    }

    public Optional<SocialNode> dislike(Like like){
        SocialNode socialNode = like.getNode();
        session.delete(like);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(session.load(SocialNode.class, socialNode.getId()));
    }

}
