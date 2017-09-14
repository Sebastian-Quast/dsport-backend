package neo4j.nodes;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Post")
public class Post extends AbstractNode {

    @JsonProperty("title")
    private String title;

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }
}
