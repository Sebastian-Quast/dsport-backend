package protocols;

import neo4j.nodes.EventNode;
import parser.AbstractBodyParser;

public class EventProtocol extends AbstractProtocol<EventNode> {

    public Long id;
    public String title;
    public String text;
    public String time;
    public String locationName;
    public String locationAdress;
    public String picture;
    public String event_picture;


    @Override
    public EventNode toModel() {
        return new EventNode(id, title, text, time, locationName, locationAdress, picture, event_picture);
    }

    public static class Parser extends AbstractBodyParser<EventProtocol> {
        @Override
        public Class<EventProtocol> getType() {
            return EventProtocol.class;
        }
    }

    public EventProtocol() {
    }

    public EventProtocol(Long id, String title, String text, String date, String locationName, String locationAdress, String picture, String event_picture) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.time = date;
        this.locationName = locationName;
        this.locationAdress = locationAdress;
        this.picture = picture;
        this.event_picture = event_picture;
    }
}
