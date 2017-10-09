package protocols;

import neo4j.nodes.ExerciseNode;
import parser.AbstractBodyParser;

public class ExerciseProtocol extends AbstractProtocol<ExerciseNode> {

    public Long id;
    public String title;
    public String type;

    @Override
    public ExerciseNode toModel() {
        return new ExerciseNode(id,title ,type );
    }

    public static class Parser extends AbstractBodyParser<ExerciseProtocol> {
        @Override
        public Class<ExerciseProtocol> getType() {
            return ExerciseProtocol.class;
        }
    }

    public ExerciseProtocol() {
    }

    public ExerciseProtocol(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
