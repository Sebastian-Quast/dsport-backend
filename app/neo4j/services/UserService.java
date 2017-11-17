package neo4j.services;

import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.Neo4jSessionFactory;
import neo4j.nodes.RegistrationNode;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;
import neo4j.nodes.resultnodes.SocialResultNode;
import org.neo4j.ogm.model.Result;
import play.libs.F;

import javax.inject.Inject;
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
        String query = "Match(user:UserNode {username : \"" + username + "\",password : \"" + password + "\"}) return user";
        return Optional.ofNullable(session.queryForObject(UserNode.class, query, Collections.emptyMap()));
    }

    public Optional<UserNode> createByRegistrationNode(RegistrationNode registrationNode) {
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
        if (usernameExists(entity.getUsername(), entity.getId())) throw new UsernameAlreadyExistsException();
        if (emailExists(entity.getEmail(), entity.getId())) throw new EmailAlreadyExistsException();
        return super.createOrUpdate(entity);
    }

    public Optional<F.Tuple<UserNode, UserNode>> getUserTuple(Long user1Id, Long user2Id) {
        return find(user1Id)
                .flatMap(user -> find(Long.valueOf(user2Id)).map(friend -> F.Tuple(user, friend)));
    }

    public boolean usernameExists(String username, Long id) {
        return existsQuery("Match (user:UserNode) WHERE user.username =  \"" + username + "\" AND NOT(ID(user) = " + (id == null ? 0L : id) + ") RETURN true");
    }

    public boolean emailExists(String username, Long id) {
        return existsQuery("Match (user:UserNode) WHERE user.email =  \"" + username + "\" AND NOT(ID(user) = " + (id == null ? 0L : id) + ")  RETURN true");
    }

    public Optional<Iterable<UserNode>> findUsers(String substring, Long id) {
        //Case insensitive query for query substring
        String query = "MATCH (n:UserNode) WHERE n.username =~ '(?i)" + substring + ".*' AND ID(n)<> " + id + " RETURN n ORDER BY n.username";
        return Optional.ofNullable(session.query(UserNode.class, query, Collections.emptyMap()));
    }

}
