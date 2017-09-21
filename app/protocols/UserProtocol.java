package protocols;

import neo4j.nodes.User;
import parser.AbstractBodyParser;

public class UserProtocol extends AbstractProtocol<User> {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String picture;

    @Override
    public User toModel() {
        return new User(id, username, firstname, lastname, email, password, picture);
    }

    public static class Parser extends AbstractBodyParser<UserProtocol> {
        @Override
        public Class<UserProtocol> getType() {
            return UserProtocol.class;
        }
    }


    public UserProtocol(Long id, String username, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public UserProtocol() {
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}
