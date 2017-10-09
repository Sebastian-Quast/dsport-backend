package neo4j.services.likeServices;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.like.LikePost;
import neo4j.services.AbstractRelationshipService;
import org.neo4j.ogm.model.Result;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class LikePostService extends AbstractRelationshipService<LikePost> {

    @Inject
    public LikePostService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<LikePost> getEntityType() {
        return LikePost.class;
    }

    public Optional<LikePost> findLike(UserNode userNode, PostNode node){
        String query = "Match(n:UserNode)-[l:LIKE_POST]->(p:PostNode) "+
                "WHERE ID(n)="+userNode.getId()+" AND ID(p)="+node.getId()+"  "+
                "return n,l,p";
        return Optional.ofNullable(session.queryForObject(LikePost.class,query, Collections.emptyMap()));
    }

}
