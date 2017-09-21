package services;

import sercurity.Role;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SessionService {

    private Long id;
    private String token;
    private List<Role> roles;

    public boolean isUser(Long id){
        return this.id.equals(id);
    }

    public boolean hasRole(Role role){
        return roles.contains(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
