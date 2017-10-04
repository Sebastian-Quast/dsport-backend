package protocols;

import neo4j.nodes.CommentNode;
import neo4j.nodes.PostNode;
import parser.AbstractBodyParser;

public class CommentProtocol extends AbstractProtocol<CommentNode> {

    public Long id;
    public String text;
    public String title;
    public String picture;


    @Override
    public CommentNode toModel() {
        return new CommentNode(id, text, title, picture);
    }


    public static class Parser extends AbstractBodyParser<CommentProtocol> {
        @Override
        public Class<CommentProtocol> getType() {
            return CommentProtocol.class;
        }
    }

    public CommentProtocol() {
    }

    public CommentProtocol(Long id, String text, String title, String picture) {
        this.id = id;
        this.text = text;
        this.title = title;
        this.picture = picture;
    }
}
