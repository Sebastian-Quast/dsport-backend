package controllers;

import com.google.common.collect.Lists;
import neo4j.nodes.EventNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Event.Created;
import neo4j.services.EventService;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.EventProtocol;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;


public class EventController extends AbstractCRUDController<EventNode, EventService> {

    private UserService userService;
    private SessionService sessionService;

    @Inject
    public EventController(EventService service, UserService userService, SessionService sessionService) {
        super(service);
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Secured
    public Result findEvents(){
        return service.findEvents(request().body().asJson().get("query").asText())
                .map(eventNodes -> toJsonResult(Lists.newArrayList(eventNodes)))
                .orElse(badRequest());
    }

    @Secured
    @BodyParser.Of(EventProtocol.Parser.class)
    @Override
    public Result update() {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .map(userNode -> {
                    EventNode eventNode = request().body().as(EventProtocol.class).toModel();
                    return service.createOrUpdate(eventNode);
                }));
    }

    @Secured
    @BodyParser.Of(EventProtocol.Parser.class)
    @Override
    public Result create() {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .map(userNode -> {
                    EventNode eventNode = request().body().as(EventProtocol.class).toModel();
                    userNode.addEvent(eventNode);
                    return userService.createOrUpdate(userNode).map(userNode1 -> eventNode);
                }));
    }

    @Secured
    public Result getEvents(String id){
        if (shouldReadEvents(id)){
            return toOptionalJsonResult(userService.find(Long.valueOf(id))
                    .map(UserNode::getEvents)
                    .map(events -> events.stream().map(Created::getEvent)));
        }else return forbidden();
    }

    public boolean shouldReadEvents(String fromId) {
        return userService.find(sessionService.getId()).map(userNode -> userNode.getFriendships().stream().anyMatch(friendship -> friendship.getId().equals(fromId)) || sessionService.getId().equals(Long.valueOf(fromId))).orElse(false);
    }

    @Override
    public boolean shouldRead(EventNode existing) {
        return false;
    }

    @Override
    public boolean shouldDelete(EventNode existing) {
        return false;
    }

    @Override
    public boolean shouldCreate(EventNode toCreate) {
        return false;
    }

    @Override
    public boolean shouldUpdate(EventNode toUpdate) {
        return false;
    }
}
