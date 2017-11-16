package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.PostNode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class PostService extends AbstractService<PostNode> {

    @Inject
    public PostService(Neo4jSessionFactory neo4jSessionFactory) {super(neo4jSessionFactory);}

    @Override
    public Class getEntityType() {return PostNode.class;}


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

}
