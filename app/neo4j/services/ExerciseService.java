package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.ExerciseNode;

import javax.inject.Inject;

public class ExerciseService extends AbstractService<ExerciseNode> {

    @Inject
    public ExerciseService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<ExerciseNode> getEntityType() {
        return ExerciseNode.class;
    }
}
