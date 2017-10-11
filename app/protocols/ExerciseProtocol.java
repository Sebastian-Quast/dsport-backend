package protocols;

import neo4j.SetType;
import neo4j.nodes.ExerciseNode;
import parser.AbstractBodyParser;

public class ExerciseProtocol extends AbstractProtocol<ExerciseNode> {

    public Long id;
    public String name;
    public SetType type;

    @Override
    public ExerciseNode toModel() {
        return new ExerciseNode(id, name, type);
    }

    public static class Parser extends AbstractBodyParser<ExerciseProtocol> {
        @Override
        public Class<ExerciseProtocol> getType() {
            return ExerciseProtocol.class;
        }
    }
}
