package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.CommentNode;

import javax.inject.Inject;

public class CommentService extends AbstractService<CommentNode> {

    @Inject
    public CommentService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class getEntityType() {
        return CommentNode.class;
    }

}
