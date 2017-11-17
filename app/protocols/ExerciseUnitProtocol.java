package protocols;

import neo4j.nodes.ExerciseUnitNode;
import parser.AbstractBodyParser;

public class ExerciseUnitProtocol extends AbstractProtocol<ExerciseUnitNode> {

    public Long id;

    @Override
    public ExerciseUnitNode toModel() {
        return new ExerciseUnitNode(id);
    }

    public static class Parser extends AbstractBodyParser<ExerciseUnitProtocol> {
        @Override
        public Class<ExerciseUnitProtocol> getType() {
            return ExerciseUnitProtocol.class;
        }
    }

    public ExerciseUnitProtocol() {
    }

    public ExerciseUnitProtocol(Long id) {
        this.id = id;
    }
}
