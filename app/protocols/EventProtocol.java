package protocols;

import neo4j.nodes.EventNode;
import parser.AbstractBodyParser;

public class EventProtocol extends AbstractProtocol<EventNode> {

    public Long id;
    public String title;
    public String text;
    public String date;
    public String location;
    public String picture;
    public String event_picture;


    @Override
    public EventNode toModel() {
        return new EventNode(id, title, text, date, location, picture, event_picture);
    }

    public static class Parser extends AbstractBodyParser<EventProtocol> {
        @Override
        public Class<EventProtocol> getType() {
            return EventProtocol.class;
        }
    }

    public EventProtocol() {
    }

    public EventProtocol(Long id, String title, String text, String date, String location, String picture, String event_picture) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.location = location;
        this.picture = picture;
        this.event_picture = event_picture;
    }
}
