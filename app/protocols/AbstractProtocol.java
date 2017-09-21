package protocols;

import neo4j.nodes.AbstractNode;

public abstract class AbstractProtocol<T> {

    public abstract T toModel();

}
