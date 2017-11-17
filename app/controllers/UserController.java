package controllers;

import com.google.common.collect.Lists;
import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.nodes.EventNode;
import neo4j.nodes.PostNode;
import neo4j.nodes.SocialNode;
import neo4j.nodes.UserNode;
import neo4j.nodes.resultnodes.EventNodeResult;
import neo4j.nodes.resultnodes.SocialResultPair;
import neo4j.nodes.resultnodes.UserNodeResult;
import neo4j.services.DataService;
import neo4j.services.EventService;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import protocols.UserProtocol;
import sercurity.Role;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class UserController extends AbstractCRUDController<UserNode, UserService> {

    private SessionService sessionService;
    private DataService dataService;
    private PostService postService;
    private EventService eventService;

    @Inject
    public UserController(UserService service, SessionService sessionService, DataService dataService, PostService postService, EventService eventService) {
        super(service);
        this.sessionService = sessionService;
        this.dataService = dataService;
        this.postService = postService;
        this.eventService = eventService;
    }

    @Secured
    public Result findUsers() {
        return service.findUsers(request().body().asJson().get("query").asText(), sessionService.getId())
                .map(userNodes -> toJsonResult(toUserNodeResult(Lists.newArrayList(userNodes))))
                .orElse(badRequest());
    }

    @Secured
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result updatePicture() {

        //Get image file from MultiPartFOrmData
        Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> image = multipartFormData.getFile("file");

        if (image != null) {
            String fileName = image.getFilename();
            String fileExtension = fileName.split("\\.")[1];
            File file = image.getFile();

            try {

                String url = dataService.move(file, fileExtension, DataService.Resource.USER_IMAGE);

                return toOptionalJsonResult(service.find(sessionService.getId()).map(userNode -> {
                    try {
                        dataService.deleteWithURL(userNode.getPicture(), DataService.Resource.USER_IMAGE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    userNode.setPicture(url);
                    return service.createOrUpdate(userNode);
                }));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return badRequest();

    }


    @BodyParser.Of(UserProtocol.Parser.class)
    @Secured(Role.ADMIN)
    @Override
    public Result create() {

        UserProtocol protocol = request().body().as(UserProtocol.class);

        try {
            return toOptionalJsonResult(service.createOrUpdate(protocol.toModel()));
        } catch (UsernameAlreadyExistsException e) {
            return badRequest(languageService.get("usernameAlreadyExists"));
        } catch (EmailAlreadyExistsException e) {
            return badRequest(languageService.get("emailAlreadyExists"));
        }
    }

    @Override
    public boolean shouldRead(UserNode existing) {
        return false;
    }

    @Override
    public boolean shouldDelete(UserNode existing) {
        return false;
    }

    @Override
    public boolean shouldCreate(UserNode toCreate) {
        return false;
    }

    @Override
    public boolean shouldUpdate(UserNode toUpdate) {
        return false;
    }

    @Secured
    @BodyParser.Of(UserProtocol.Parser.class)
    @Override
    public Result update() {

        UserProtocol protocol = request().body().as(UserProtocol.class);

        if (!sessionService.isUser(protocol.id) && !sessionService.hasRole(Role.ADMIN)) {
            return unauthorized();
        }

        try {
            return service.find(protocol.id)
                    .map(userNode -> {
                        if (protocol.password == null) {
                            protocol.setPassword(userNode.getPassword());
                        }
                        if (protocol.picture == null) {
                            protocol.setPicture(userNode.getPicture());
                        }
                        return toJsonResult(service.createOrUpdate(protocol.toModel()));
                    }).orElse(badRequest());
        } catch (UsernameAlreadyExistsException e) {
            return badRequest(languageService.get("usernameAlreadyExists"));
        } catch (EmailAlreadyExistsException e) {
            return badRequest(languageService.get("emailAlreadyExists"));
        }
    }


    @Secured
    public Result getFriends() {
        //Bidirectional relationship, check if user is friendNode or UserNode in current friendship relation and return
        return toOptionalJsonResult(service.find(sessionService.getId()).map(userNode -> userNode.getFriendships().stream().map(friendship ->
        {
            if (!Objects.equals(sessionService.getId(), friendship.getFriend().getId())) {
                return toFriendNodeResult(friendship.getFriend());
            } else if (!Objects.equals(sessionService.getId(), friendship.getUserNode().getId())) {
                return toFriendNodeResult(friendship.getUserNode());
            }
            return badRequest();
        })));
    }

    @Secured
    public Result getFriendRequests() {
        return toOptionalJsonResult(service.find(sessionService.getId()).map(userNode -> userNode.getFriendshipRequests().stream().map(friendshipRequest -> toRequestUserNodeResult(friendshipRequest.getUserNode()))));
    }

    private ArrayList<UserNodeResult> toUserNodeResult(ArrayList<UserNode> userNodes) {
        ArrayList<UserNodeResult> userNodeResults = new ArrayList<>();
        for (UserNode userNode : userNodes) {
            userNodeResults.add(new UserNodeResult(userNode, isFriend(String.valueOf(userNode.getId())), hasRequest(String.valueOf(userNode.getId())), alreadyRequested(String.valueOf(userNode.getId()))));
        }
        return userNodeResults;
    }

    public UserNodeResult toSingleUserNodeResult(UserNode userNode) {
        System.out.println(userNode.toString());
        return (new UserNodeResult(userNode, isFriend(String.valueOf(userNode.getId())), hasRequest(String.valueOf(userNode.getId())), alreadyRequested(String.valueOf(userNode.getId()))));
    }

    private UserNodeResult toFriendNodeResult(UserNode userNode) {
        System.out.println(userNode.toString());
        if (userNode.getId() != sessionService.getId()) {
            return (new UserNodeResult(userNode, true, false, false));
        } else return new UserNodeResult(userNode, false, false, false);
    }

    private UserNodeResult toRequestUserNodeResult(UserNode userNode) {
        System.out.println(userNode.toString());
        return (new UserNodeResult(userNode, false, true, false));
    }

    private boolean hasRequest(String id) {
        return service.find(sessionService.getId())
                .map(UserNode::getFriendshipRequests)
                .map(friendshipRequests -> friendshipRequests.stream()
                        .anyMatch(friendshipRequest ->
                                friendshipRequest.getUserNode().getId().equals(Long.valueOf(id))
                                        && friendshipRequest.getFriend().getId().equals(sessionService.getId())))
                .orElse(false);
    }

    //TODO one request for all Friends
    private boolean isFriend(String id) {
        return service.find(sessionService.getId())
                .map(UserNode::getFriendships)
                .map(friendships -> friendships.stream()
                        .anyMatch(friendship ->
                                (friendship.getUserNode().getId().equals(sessionService.getId())
                                        && friendship.getFriend().getId().equals(Long.valueOf(id)))
                                        ||
                                        (friendship.getUserNode().getId().equals(Long.valueOf(id))
                                                && friendship.getFriend().getId().equals(sessionService.getId()))))
                .orElse(false);
    }

    private boolean alreadyRequested(String id) {
        return service.find(sessionService.getId())
                .map(UserNode::getFriendshipsRequested)
                .map(friendshipRequests -> friendshipRequests.stream()
                        .anyMatch(friendshipRequest ->
                                friendshipRequest.getUserNode().getId().equals(sessionService.getId())
                                        && friendshipRequest.getFriend().getId().equals(Long.valueOf(id))))
                .orElse(false);
    }


    @Secured
    public Result getPinboard(String id) {
        return toOptionalJsonResult(postService.getPinboard(Long.valueOf(id))
                .map(Lists::newArrayList).map(this::toSocialResult));
    }

    @Secured
    public Result getNewsFeed(String id) {
        return toOptionalJsonResult(postService.getNewsFeed(Long.valueOf(id))
                .map(Lists::newArrayList).map(this::toSocialResult));
    }


    private ArrayList<SocialResultPair> toSocialResult(ArrayList<SocialNode> socialNodes) {
        socialNodes.sort((o1,o2) -> o2.getCreated().compareTo(o1.getCreated()));
        ArrayList<SocialResultPair> socialResultNodes = new ArrayList<>();
        for (SocialNode socialNode : socialNodes) {
            if (socialNode instanceof PostNode) {
                socialResultNodes.add(new SocialResultPair("POST", socialNode, ((PostNode) socialNode).getPosted().getUserNode(),
                        service.find(sessionService.getId()).map(userNode -> userNode.getLikes().stream().anyMatch(like -> like.getNode().getId().equals(socialNode.getId()))).orElse(false)));
            } else if (socialNode instanceof EventNode) {
                socialResultNodes.add(new SocialResultPair("EVENT",
                        new EventNodeResult((EventNode) socialNode, (service.find(sessionService.getId()).map(userNode -> userNode.getParticipating().stream().anyMatch(participate -> participate.getEvent().getId().equals(socialNode.getId()))).orElse(false))),
                        ((EventNode) socialNode).getArranged().getUser(),
                        service.find(sessionService.getId()).map(userNode -> userNode.getLikes().stream().anyMatch(like -> like.getNode().getId().equals(socialNode.getId()))).orElse(false)));
            }
        }

        socialNodes.forEach(socialNode -> System.out.println(socialNode.getCreated()));
        return socialResultNodes;
    }

}
