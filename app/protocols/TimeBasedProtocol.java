package protocols;

import neo4j.nodes.sets.TimeBasedSet;
import parser.AbstractBodyParser;

public class TimeBasedProtocol extends AbstractProtocol<TimeBasedSet> {

    public Long id;
    public String time;

    @Override
    public TimeBasedSet toModel() {
        return new TimeBasedSet(id, time);
    }

    public static class Parser extends AbstractBodyParser<TimeBasedProtocol> {
        @Override
        public Class<TimeBasedProtocol> getType() {
            return TimeBasedProtocol.class;
        }
    }

    public TimeBasedProtocol() {
    }

    public TimeBasedProtocol(Long id, String time) {
        this.id = id;
        this.time = time;
    }
}
