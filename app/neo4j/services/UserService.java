package neo4j.services;

import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.Neo4jSessionFactory;
import neo4j.nodes.RegistrationNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.FriendshipRequest;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class UserService extends AbstractService<UserNode> {

    @Inject
    public UserService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<UserNode> getEntityType() {
        return UserNode.class;
    }

    public Optional<UserNode> findByCredentials(String username, String password) {
        String query = "Match(user:UserNode {username : \""+ username +"\",password : \""+ password +"\"}) return user";
        return Optional.ofNullable(session.queryForObject(UserNode.class ,query, Collections.emptyMap()));
    }

    public Optional<UserNode> createByRegistrationNode (RegistrationNode registrationNode){
        UserNode node = new UserNode(
                registrationNode.getUsername(),
                registrationNode.getFirstname(),
                registrationNode.getLastname(),
                registrationNode.getEmail(),
                registrationNode.getPassword(),
                null);
        return createOrUpdate(node);
    }

    @Override
    public Optional<UserNode> createOrUpdate(UserNode entity) {
        if(usernameExists(entity.getUsername(), entity.getId())) throw new UsernameAlreadyExistsException();
        if(emailExists(entity.getEmail(), entity.getId())) throw new EmailAlreadyExistsException();
        return super.createOrUpdate(entity);
    }


    public boolean usernameExists(String username, Long id){
        return existsQuery("Match (user:UserNode) WHERE user.username =  \""+ username +"\" AND NOT(ID(user) = "+ (id==null?0L:id) +") RETURN true");
    }

    public boolean emailExists(String username, Long id){
        return existsQuery("Match (user:UserNode) WHERE user.email =  \""+ username +"\" AND NOT(ID(user) = "+ (id==null?0L:id) +")  RETURN true");
    }

    public Optional<FriendshipRequest> requestFriendship(FriendshipRequest request){
        session.save(request);
        return Optional.ofNullable(session.load(FriendshipRequest.class, request.getId()));
    }
}
