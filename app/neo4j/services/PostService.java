package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.PostNode;
import neo4j.nodes.SocialNode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class PostService extends AbstractService<PostNode> {

    @Inject
    public PostService(Neo4jSessionFactory neo4jSessionFactory) {super(neo4jSessionFactory);}

    @Override
    public Class getEntityType() {return PostNode.class;}

    @Override
    public Optional<PostNode> createOrUpdate(PostNode entity) {
        session.save(entity, 1);
        return find(entity.getId());
    }

    public Optional<Iterable<PostNode>> findAll(String fromId) {
        String query = "Match(n:UserNode)-[POSTED]-(a:PostNode)" +
                       "Match(n:UserNode)-[PINNED]-(a:PostNode) WHERE ID(n)= "+fromId+" " +
                       "return DISTINCT a ORDER BY a.created";
        return Optional.ofNullable(session.query(PostNode.class, query, Collections.emptyMap()));
    }

    public Optional<Iterable<PostNode>> findPosts(String substring){
        //Case insensitive query for query substring
        String query = "MATCH (p:PostNode) WHERE p.title =~ '(?i)"+substring+".*' RETURN p ORDER BY p.title";
        return Optional.ofNullable(session.query(PostNode.class, query, Collections.emptyMap()));
    }

    public Optional<Iterable<SocialNode>> getPinboard(Long id) {
        session.clear();
        String query = "MATCH (u:UserNode)-[x]-(s:SocialNode) WHERE ID(u)=" + id + " " +
                "OPTIONAL MATCH (s)-[z]-(f:UserNode) " +
                "OPTIONAL MATCH (s)-[y]-(g:SocialNode) "+
                "return u,f,s,g,x,z,y ORDER By s.created DESC";
        return Optional.ofNullable(session.query(SocialNode.class, query, Collections.emptyMap()));
    }

    public Optional<Iterable<SocialNode>> getNewsFeed(Long id) {
        session.clear();
        String query = "MATCH (u:UserNode)-[x]-(s:SocialNode) WHERE ID(u)=" + id + " AND type(x)<>\"PERFORMED\" " +
                "OPTIONAL MATCH (s)-[z]-(f:UserNode) " +
                "OPTIONAL MATCH (s)-[y]-(a:SocialNode) " +
                "return u,f,s,x,z,y,a " +
                "UNION " +
                "MATCH (u1:UserNode)-[p:FRIENDSHIP]-(u:UserNode)-[x]-(s:SocialNode)  WHERE ID(u1)=" + id + " AND type(x)<>\"PERFORMED\" " +
                "OPTIONAL MATCH (s)-[z]-(f:UserNode) " +
                "OPTIONAL MATCH (s)-[y]-(a:SocialNode) " +
                "return u,f,s,x,z,y,a";
        return Optional.ofNullable(session.query(SocialNode.class, query, Collections.emptyMap()));
    }

}
