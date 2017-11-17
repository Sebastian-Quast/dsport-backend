package neo4j.nodes.resultnodes;

import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;

public class SocialResultNode extends UserNode {

    private UserNode creator;

    public SocialResultNode(UserNode creator, SocialNode socialNode) {
        super();
        this.creator = creator;
    }
}
