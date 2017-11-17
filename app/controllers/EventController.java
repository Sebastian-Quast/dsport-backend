package controllers;

import com.google.common.collect.Lists;
import neo4j.nodes.EventNode;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;
import neo4j.nodes.resultnodes.EventNodeResult;
import neo4j.nodes.resultnodes.SocialResultPair;
import neo4j.relationships.Event.Arrange;
import neo4j.services.DataService;
import neo4j.services.EventService;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import protocols.EventProtocol;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class EventController extends AbstractCRUDController<EventNode, EventService> {

    private UserService userService;
    private SessionService sessionService;
    private DataService dataService;

    @Inject
    public EventController(EventService service, UserService userService, SessionService sessionService, DataService dataService) {
        super(service);
        this.userService = userService;
        this.sessionService = sessionService;
        this.dataService = dataService;
    }

    @Secured
    public Result findEvents() {
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
    public Result getEvents1(String id) {
        if (shouldReadEvents(id)) {
            return toOptionalJsonResult(userService.find(Long.valueOf(id))
                    .map(UserNode::getEvents)
                    .map(events -> events.stream().map(Arrange::getEvent)));
        } else return forbidden();
    }


   //@Secured
   //public Result getEvents(String id) {
   //    return toJsonResult(service.getEvents(Long.valueOf(id))
   //            .map(socialNodes -> service.getParticipating(Long.valueOf(id))
   //                    .map(socialNodes1 -> F.Tuple(Lists.newArrayList(socialNodes), Lists.newArrayList(socialNodes1)))
   //                    .map(arrayListArrayListTuple -> {
   //                        arrayListArrayListTuple._1.addAll(arrayListArrayListTuple._2);
   //                        return arrayListArrayListTuple._1;
   //                    }).map(socialNodes1 -> {
   //                        Collections.sort(socialNodes1);
   //                        return socialNodes1;
   //                    }).map(this::toSocialResult)));

   //}

    @Secured
    public Result getEvents(String id) {
        return toOptionalJsonResult(service.getEvents(Long.valueOf(id))
                .map(Lists::newArrayList).map(this::toSocialResult));

    }

    private ArrayList<SocialResultPair> toSocialResult(ArrayList<SocialNode> socialNodes) {

        ArrayList<SocialResultPair> socialResultNodes = new ArrayList<>();
        for (SocialNode socialNode : socialNodes) {
            if (socialNode instanceof EventNode) {
                socialResultNodes.add(new SocialResultPair("EVENT",
                        new EventNodeResult((EventNode)socialNode, (userService.find(sessionService.getId()).map(userNode -> userNode.getParticipating().stream().anyMatch(participate ->
                             participate.getEvent().getId().equals(socialNode.getId())
                        )).orElse(false))),
                        ((EventNode) socialNode).getArranged().getUser(),
                        userService.find(sessionService.getId()).map(userNode -> userNode.getLikes().stream().anyMatch(like -> like.getNode().getId().equals(socialNode.getId()))).orElse(false)));
            }
        }
        System.out.println(socialNodes.size());
        return socialResultNodes;
    }


    @Secured
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result updatePicture(String id) {

        //Get image file from MultiPartFOrmData
        Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> image = multipartFormData.getFile("file");

        if (image != null) {
            String fileName = image.getFilename();
            String fileExtension = fileName.split("\\.")[1];
            File file = image.getFile();

            try {

                String url = dataService.move(file, fileExtension, DataService.Resource.EVENT_IMAGE);

                return toOptionalJsonResult(service.find(Long.valueOf(id)).map(eventNode -> {
                    try {
                        if (eventNode.getPicture() != null) {
                            dataService.deleteWithURL(eventNode.getPicture(), DataService.Resource.EVENT_IMAGE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    eventNode.setPicture(url);
                    return service.createOrUpdate(eventNode);
                }));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return badRequest();

    }


    public boolean shouldReadEvents(String fromId) {
        return userService.find(sessionService.getId()).map(userNode -> userNode.getFriendships().stream().anyMatch(friendship -> friendship.getId().equals(fromId)) || sessionService.getId().equals(Long.valueOf(fromId))).orElse(false);
    }

    @Override
    public boolean shouldRead(EventNode existing) {
        return true;
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
