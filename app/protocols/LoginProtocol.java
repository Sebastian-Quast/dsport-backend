package protocols;

import parser.AbstractBodyParser;


public class LoginProtocol {
    private String username;
    private String password;

    public static class Parser extends AbstractBodyParser<LoginProtocol> {
        @Override
        public Class<LoginProtocol> getType() {
            return LoginProtocol.class;
        }
    }

    public LoginProtocol(){}

    public LoginProtocol(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
