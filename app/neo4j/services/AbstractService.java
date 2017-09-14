package neo4j.services;

import neo4j.nodes.AbstractNode;
import neo4j.Neo4jSessionFactory;
import org.neo4j.ogm.session.Session;

import javax.inject.Inject;
import java.util.Collection;

public abstract class AbstractService<T extends AbstractNode> {

    //Tutorial says 0
    private static final int DEPTH_LIST = 1;
    private static final int DEPTH_ENTITY = 1;

    protected Session session;

    @Inject
    public AbstractService(Neo4jSessionFactory neo4jSessionFactory){
        this.session = neo4jSessionFactory.getNeo4jSession();
    }


    public Collection<T> findAll() {
        return session.loadAll(getEntityType(), DEPTH_LIST);
    }

    public T find(Long id) {
        return session.load(getEntityType(), id, DEPTH_ENTITY);
    }

    public void delete(Long id) {
        session.delete(session.load(getEntityType(), id));
    }

    public T createOrUpdate(T entity) {
        session.save(entity, DEPTH_ENTITY);
        return find(entity.getId());
    }

    public abstract Class<T> getEntityType();

}
