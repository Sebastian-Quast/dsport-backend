package neo4j.services;

import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.Neo4jSessionFactory;
import neo4j.nodes.RegistrationNode;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class RegistrationService extends AbstractService<RegistrationNode> {

    private UserService userService;

    @Inject
    public RegistrationService(Neo4jSessionFactory neo4jSessionFactory, UserService userService) {
        super(neo4jSessionFactory);
        this.userService = userService;
    }

    @Override
    public Class<RegistrationNode> getEntityType() {
        return RegistrationNode.class;
    }

    @Override
    public Optional<RegistrationNode> createOrUpdate(RegistrationNode entity) {
        if(usernameExists(entity.getUsername(), entity.getId()) || userService.usernameExists(entity.getUsername(), entity.getId())) throw new UsernameAlreadyExistsException();
        if(emailExists(entity.getEmail(), entity.getId()) || userService.emailExists(entity.getEmail(), entity.getId())) throw new EmailAlreadyExistsException();
        return super.createOrUpdate(entity);
    }

    public Optional<RegistrationNode> findByHash(String hash) {
        String query = "Match (n:RegistrationNode) WHERE n.hash =  \""+ hash +"\" RETURN n";
        return Optional.ofNullable(session.queryForObject(RegistrationNode.class, query, Collections.emptyMap()));
    }

    public boolean usernameExists(String username, Long id){
        return existsQuery("Match (n:RegistrationNode) WHERE n.username =  \""+ username +"\" AND NOT(ID(n) = "+ (id==null?0L:id) +") RETURN true");
    }

    public boolean emailExists(String username, Long id){
        return existsQuery("Match (n:RegistrationNode) WHERE n.email =  \""+ username +"\" AND NOT(ID(n) = "+ (id==null?0L:id) +")  RETURN true");
    }
}
