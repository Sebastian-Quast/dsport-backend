package controllers;

import neo4j.services.EventService;
import neo4j.services.ParticipateService;
import neo4j.services.UserService;
import play.mvc.Result;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;

public class ParticipateController extends AbstractController {

    private ParticipateService participateService;
    private UserService userService;
    private SessionService sessionService;
    private EventService eventService;

    @Inject
    public ParticipateController(ParticipateService participateService, UserService userService, SessionService sessionService, EventService eventService) {
        this.participateService = participateService;
        this.userService = userService;
        this.sessionService = sessionService;
        this.eventService = eventService;
    }


    @Secured
    public Result participateEvent(String id) {
        return userService.find(sessionService.getId())
                .flatMap(userNode -> eventService.find(Long.valueOf(id)).map(eventNode -> participateService.findExisting(userNode, eventNode)
                        .flatMap(participate -> participateService.unparticipate(participate))
                        .map(eventNode1 -> toJsonResult(eventNode1.getParticipates().size()))
                        .orElseGet(() -> {
                            userNode.addParticipate(eventNode);
                            userService.createOrUpdate(userNode);
                            return toJsonResult(eventNode.getParticipates().size());
                        }))).orElse(badRequest());
    }
}
