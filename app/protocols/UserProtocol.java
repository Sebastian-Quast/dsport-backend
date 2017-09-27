package protocols;

import neo4j.nodes.UserNode;
import parser.AbstractBodyParser;

public class UserProtocol extends AbstractProtocol<UserNode>{

    public Long id;
    public String username;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String picture;

    @Override
    public UserNode toModel() {
        return new UserNode(id, username, firstname, lastname, email, password, picture);
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
}
