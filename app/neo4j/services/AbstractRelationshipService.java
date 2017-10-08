package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.session.Session;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public abstract class AbstractRelationshipService<T extends AbstractRelationship> {

    //Tutorial says 0
    private static final int DEPTH_LIST = 1;
    private static final int DEPTH_ENTITY = 1;

    protected Session session;

    @Inject
    public AbstractRelationshipService(Neo4jSessionFactory neo4jSessionFactory) {
        this.session = neo4jSessionFactory.getNeo4jSession();
    }


    public Collection<T> findAll(int depth) {
        return session.loadAll(getEntityType(), depth);
    }
    public Collection<T> findAll() {
        return findAll(DEPTH_LIST);
    }

    public Optional<T> find(Long id, int depth) {
        return Optional.ofNullable(session.load(getEntityType(), id, depth));
    }

    public Optional<T> find(Long id) {
        session.clear();
        return find(id, DEPTH_ENTITY);
    }

    public void delete(Long id) {
        session.clear();
        session.delete(session.load(getEntityType(), id));
    }

    public Optional<T> createOrUpdate(T entity, int depth){
        T updated = find(entity.getId())
                .map(existing -> {
                    entity.setCreated(existing.getCreated());
                    return entity;
                }).orElse(entity);
        session.clear();
        session.save(updated, depth);
        return find(updated.getId());
    }

    public Optional<T> createOrUpdate(T entity){
        return createOrUpdate(entity, DEPTH_ENTITY);
    }

    public abstract Class<T> getEntityType();

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
