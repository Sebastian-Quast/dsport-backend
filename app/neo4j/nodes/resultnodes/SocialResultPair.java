package neo4j.nodes.resultnodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;

public class SocialResultPair implements Comparable<SocialResultPair>{

    private boolean likesSocialNode;

    private SocialNode socialNode;
    private UserNode userNode;

    @JsonProperty("type")
    private String type;

    public SocialResultPair(String type, SocialNode socialNode, UserNode userNode, boolean likesSocialNode) {
        this.socialNode = socialNode;
        this.userNode = userNode;
        this.type = type;
        this.likesSocialNode = likesSocialNode;
    }

    public SocialNode getSocialNode() {
        return socialNode;
    }

    public void setSocialNode(SocialNode socialNode) {
        this.socialNode = socialNode;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public boolean isLikesSocialNode() {
        return likesSocialNode;
    }

    public void setLikesSocialNode(boolean likesSocialNode) {
        this.likesSocialNode = likesSocialNode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(SocialResultPair o) {
        if (socialNode.getCreated().after(o.getSocialNode().getCreated())){
            return -1;
        } else if (socialNode.getCreated().before(o.getSocialNode().getCreated())) {
            return 1;
        } else {
            return 0;
        }
    }
}