package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.PostNode;
import play.libs.Json;

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

}
