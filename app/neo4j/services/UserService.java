package neo4j.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import neo4j.Neo4jSessionFactory;
import neo4j.nodes.User;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class UserService extends AbstractService<User> {

    @Inject
    public UserService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    public Optional<User> updateUser(User user, String key, String value){

        switch (key)
        {
            case "username" :
                user.setUsername(value);
                break;
            case "firstname" :
                user.setFirstname(value);
                break;
            case "lastname" :
                user.setLastname(value);
                break;
            case "email" :
                user.setEmail(value);
                break;
            default:
                return Optional.empty();
        }
        user.setUpdated();
        session.save(user, getDepthEntity());
        return find(user.getId());
    }

    public Optional<User> findByCredentials(String username, String password) {
        String query = "Match(user:User {username : \""+ username +"\",password : \""+ password +"\"}) return user";
        return Optional.ofNullable(session.queryForObject(User.class ,query, Collections.emptyMap()));
    }

    public Optional<Boolean> verifyPw(User user, String password) {
        System.out.println(password.equals(user.getPassword()));
        return Optional.of(password.equals(user.getPassword()));
    }

}
