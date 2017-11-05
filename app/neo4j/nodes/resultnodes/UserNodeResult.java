package neo4j.nodes.resultnodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.AbstractNode;
import neo4j.nodes.UserNode;

public class UserNodeResult extends AbstractNode{

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("isFriend")
    private Boolean isFriend;

    @JsonProperty("hasRequest")
    private Boolean hasRequest;

    @JsonProperty("isRequested")
    private Boolean isRequested;

    public UserNodeResult(UserNode userNode, Boolean isFriend, Boolean hasRequest, Boolean isRequested) {
        this.username = userNode.getUsername();
        this.firstname = userNode.getFirstname();
        this.lastname = userNode.getLastname();
        this.email = userNode.getEmail();
        this.picture = userNode.getPicture();
        this.isFriend = isFriend;
        this.hasRequest = hasRequest;
        this.isRequested = isRequested;
        setId(userNode.getId());
        setCreated(userNode.getCreated());
        setUpdated(userNode.getUpdated());
    }
}
