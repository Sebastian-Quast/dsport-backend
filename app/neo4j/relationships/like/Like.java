package neo4j.relationships.like;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.AbstractRelationship;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Like.TYPE)
public class Like extends AbstractRelationship{

    public static final String TYPE = "LIKE";

    @StartNode
    UserNode user;

    @EndNode
    SocialNode node;

    public Like() {
    }

    public Like(UserNode user, SocialNode node) {
        this.user = user;
        this.node = node;
    }

    public UserNode getUser() {
        return user;
    }

    public SocialNode getNode() {
        return node;
    }
}
