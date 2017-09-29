package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Posted;

import javax.inject.Inject;
import java.util.Optional;

public class PostService extends AbstractService<PostNode> {

    @Inject
    public PostService(Neo4jSessionFactory neo4jSessionFactory) {super(neo4jSessionFactory);}

    @Override
    public Class getEntityType() {return PostNode.class;}
}
