package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.UserNode;
import neo4j.relationships.friendship.Friendship;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class FriendshipService extends AbstractRelationshipService<Friendship> {

    @Inject
    public FriendshipService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<Friendship> getEntityType() {
        return Friendship.class;
    }

    public Optional<Friendship> findFriendship(UserNode user, UserNode friend) {
        String query = "Match(n:UserNode)-[r:FRIENDSHIP]-(m:UserNode) " +
                "WHERE ID(n)=" + user.getId() + " AND ID(m)=" + friend.getId() + " " +
                "RETURN n,r,m";
        return Optional.ofNullable(session.queryForObject(Friendship.class, query, Collections.emptyMap()));
    }
}
