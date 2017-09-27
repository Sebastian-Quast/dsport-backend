package protocols;

import neo4j.nodes.RegistrationNode;
import parser.AbstractBodyParser;

public class RegistrationProtocol extends AbstractProtocol<RegistrationNode>{

    public String username;
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    @Override
    public RegistrationNode toModel() {
        return new RegistrationNode(username, firstname, lastname, email, password);
    }

    public static class Parser extends AbstractBodyParser<RegistrationProtocol> {
        @Override
        public Class<RegistrationProtocol> getType() {
            return RegistrationProtocol.class;
        }
    }


    public RegistrationProtocol(String username, String firstname, String lastname, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public RegistrationProtocol() {
    }
}
