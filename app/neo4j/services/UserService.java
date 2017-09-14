package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.User;

import javax.inject.Inject;

public class UserService extends AbstractService<User> {

    @Inject
    public UserService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }
}
