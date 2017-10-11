package protocols;

import neo4j.nodes.sets.DistanceBasedSet;
import parser.AbstractBodyParser;

public class DistanceBasedProtocol extends AbstractProtocol<DistanceBasedSet> {

    public Long id;
    public String time;
    public String distance;

    @Override
    public DistanceBasedSet toModel() {
        return new DistanceBasedSet(id, time, distance);
    }

    public static class Parser extends AbstractBodyParser<DistanceBasedProtocol> {
        @Override
        public Class<DistanceBasedProtocol> getType() {
            return DistanceBasedProtocol.class;
        }
    }

    public DistanceBasedProtocol() {
    }

    public DistanceBasedProtocol(Long id, String time, String distance) {
        this.id = id;
        this.time = time;
        this.distance = distance;
    }
}
