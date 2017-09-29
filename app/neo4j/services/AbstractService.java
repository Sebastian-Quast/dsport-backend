package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.AbstractNode;
import org.neo4j.ogm.session.Session;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractNode> {

    //Tutorial says 0
    private static final int DEPTH_LIST = 1;
    private static final int DEPTH_ENTITY = 1;

    protected Session session;

    @Inject
    public AbstractService(Neo4jSessionFactory neo4jSessionFactory) {
        this.session = neo4jSessionFactory.getNeo4jSession();
    }


    public Collection<T> findAll() {
        return session.loadAll(getEntityType(), DEPTH_LIST);
    }

    public Optional<T> find(Long id) {
        return Optional.ofNullable(session.load(getEntityType(), id, DEPTH_ENTITY));
    }

    public void delete(Long id) {
        session.delete(session.load(getEntityType(), id));
    }

    public Optional<T> createOrUpdate(T entity){
        T updated = find(entity.getId())
                .map(existing -> {
                    entity.setCreated(existing.getCreated());
                    return entity;
                 }).orElse(entity);

        session.save(updated, DEPTH_ENTITY);
        return find(updated.getId());
    }

    public abstract Class<T> getEntityType();


    public static int getDepthList() {
        return DEPTH_LIST;
    }

    public static int getDepthEntity() {
        return DEPTH_ENTITY;
    }

    protected boolean existsQuery(String query) {
        return session.queryForObject(Boolean.class, query, Collections.emptyMap()) != null;
    }

    public boolean exists(Long id){
        return find(id).isPresent();
    }

    public Optional<T> ifNotExists(T entity){
        if(exists(entity.getId())){
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }
}


