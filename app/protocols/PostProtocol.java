package protocols;

import neo4j.nodes.PostNode;
import parser.AbstractBodyParser;

public class PostProtocol extends AbstractProtocol<PostNode> {

    public Long id;
    public String text;
    public String title;
    public String picture;

    @Override
    public PostNode toModel() {
        return new PostNode(id, text, title, picture);
    }


    public static class Parser extends AbstractBodyParser<PostProtocol> {
        @Override
        public Class<PostProtocol> getType() {
            return PostProtocol.class;
        }
    }

    public PostProtocol() {
    }

    public PostProtocol(Long id,String text, String title, String picture) {
        this.id = id;
        this.text = text;
        this.title = title;
        this.picture = picture;
    }
}
