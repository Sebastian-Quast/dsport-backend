package neo4j.nodes.resultnodes;

import neo4j.nodes.EventNode;

public class EventNodeResult extends EventNode {

    private Boolean participating;

    public EventNodeResult(EventNode eventNode, Boolean participating) {
        super(eventNode);
        this.participating = participating;
    }

    public Boolean getParticipating() {
        return participating;
    }

    public void setParticipating(Boolean participating) {
        this.participating = participating;
    }
}
