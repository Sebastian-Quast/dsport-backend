package neo4j.relationships;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import neo4j.nodes.Post;
import neo4j.entities.UniqueEntity;
import neo4j.nodes.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = Posted.TYPE)
public class Posted extends AbstractRelationship {

    public static final String TYPE = "POSTED";

    @StartNode
    @JsonIgnore
    private User user;

    @EndNode
    @JsonSerialize(as=UniqueEntity.class)
    @JsonProperty("post")
    private Post post;

    public Posted() {
    }

    public Posted(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
