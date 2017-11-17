package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.ExerciseUnitNode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class ExerciseUnitService extends AbstractService<ExerciseUnitNode> {

    @Inject
    public ExerciseUnitService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<ExerciseUnitNode> getEntityType() {
        return ExerciseUnitNode.class;
    }

    public <T> Optional<Iterable<T>> getSets(Class<T> tClass, ExerciseUnitNode exerciseUnitNode){
        String query = "Match (e:ExerciseNode)-[x]-(n:ExerciseUnitNode)-[r]-(p:AbstractSet) WHERE ID(n)="+ exerciseUnitNode.getId()+" return e,x,n,r,p";
        return Optional.ofNullable(session.query(tClass,query, Collections.emptyMap()));
    }

}
