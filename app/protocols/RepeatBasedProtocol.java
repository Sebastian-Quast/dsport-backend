package protocols;

import neo4j.nodes.sets.RepeatBasedSet;
import parser.AbstractBodyParser;

public class RepeatBasedProtocol extends AbstractProtocol<RepeatBasedSet> {

    public Long id;
    public String time;
    public String weight;
    public String repeats;

    @Override
    public RepeatBasedSet toModel() {
        return new RepeatBasedSet(id, time, weight, repeats);
    }

    public static class Parser extends AbstractBodyParser<RepeatBasedProtocol> {
        @Override
        public Class<RepeatBasedProtocol> getType() {
            return RepeatBasedProtocol.class;
        }
    }

    public RepeatBasedProtocol() {
    }

    public RepeatBasedProtocol(Long id, String time, String weight, String repeats) {
        this.id = id;
        this.time = time;
        this.weight = weight;
        this.repeats = repeats;
    }
}
