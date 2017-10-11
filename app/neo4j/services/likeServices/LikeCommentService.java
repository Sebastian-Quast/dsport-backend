package neo4j.services.likeServices;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.CommentNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.like.LikeComment;
import neo4j.services.AbstractRelationshipService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class LikeCommentService extends AbstractRelationshipService<LikeComment>{

    @Inject
    public LikeCommentService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<LikeComment> getEntityType() {
        return LikeComment.class;
    }

    public Optional<LikeComment> findLike(UserNode userNode, CommentNode node){
        String query = "Match(n:UserNode)-[l:LIKE_COMMENT]->(p:CommentNode) "+
                "WHERE ID(n)="+userNode.getId()+" AND ID(p)="+node.getId()+"  "+
                "return n,l,p";
        return Optional.ofNullable(session.queryForObject(LikeComment.class,query, Collections.emptyMap()));
    }

}
