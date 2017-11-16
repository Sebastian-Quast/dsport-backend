package controllers;

import com.google.common.collect.Lists;
import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.nodes.UserNode;
import neo4j.nodes.resultnodes.UserNodeResult;
import neo4j.services.UserService;
import org.apache.commons.io.FileUtils;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class UserController extends AbstractCRUDController<UserNode, UserService> {

    private SessionService sessionService;

    @Inject
    public UserController(UserService service, SessionService sessionService) {
        super(service);
        this.sessionService = sessionService;
    }

    @Secured
    public Result findUsers(){
       return service.findUsers(request().body().asJson().get("query").asText(), sessionService.getId())
               .map(userNodes -> toJsonResult(toUserNodeResult(Lists.newArrayList(userNodes))))
               .orElse(badRequest());
    }

    @Secured
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result updatePicture() {

        String domain = "https://www.daily-sport.de";
        //TODO Datareference for directory
        String relativeImageDirectory = "user/data/profile/images";
        String domainDirectory = "/var/www/vhosts/daily-sport.de/httpdocs";
        String webUser = "squast";
        String webGroup = "psaserv";




        //Get image file from MultiPartFOrmData
        Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> image = multipartFormData.getFile("file");

        if (image != null) {
            String fileName = image.getFilename();
            String fileExtension = fileName.split("\\.")[1];
            File file = image.getFile();

            try {
                File destination = new File(domainDirectory+ "/" +relativeImageDirectory, UUID.randomUUID()+"."+fileExtension);

                FileUtils.moveFile(file, destination);

                Runtime.getRuntime().exec("chown "+webUser+":"+webGroup+" "+ destination.getAbsolutePath());
                Runtime.getRuntime().exec("chmod 755 "+ destination.getAbsolutePath());

                String leftUrl = domain + "/" + relativeImageDirectory + "/";
                String imageUrl = leftUrl + destination.getName();

                return toOptionalJsonResult(service.find(sessionService.getId()).map(userNode -> {
                    if (userNode.getPicture() != null){
                    String oldPicture = userNode.getPicture();
                    String oldPictureName = oldPicture.substring(leftUrl.length(), oldPicture.length());

                        try {
                            FileUtils.forceDelete(new File(domainDirectory+ "/" +relativeImageDirectory, oldPictureName));
                        } catch (IOException ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    userNode.setPicture(imageUrl);
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
            if (!Objects.equals(sessionService.getId(), friendship.getFriend().getId())){
                return toFriendNodeResult(friendship.getFriend());
            }else if (!Objects.equals(sessionService.getId(), friendship.getUserNode().getId())){
                return toFriendNodeResult(friendship.getUserNode());
            }
            return badRequest();
        })));
    }

    @Secured
    public Result getFriendRequests() {
        return toOptionalJsonResult(service.find(sessionService.getId()).map(userNode -> userNode.getFriendshipRequests().stream().map(friendshipRequest -> toRequestUserNodeResult(friendshipRequest.getUserNode()))));
    }

    private ArrayList<UserNodeResult> toUserNodeResult(ArrayList<UserNode> userNodes){
        ArrayList<UserNodeResult> userNodeResults = new ArrayList<>();
        for (UserNode userNode : userNodes) {
            userNodeResults.add(new UserNodeResult(userNode, isFriend(String.valueOf(userNode.getId())), hasRequest(String.valueOf(userNode.getId())), alreadyRequested(String.valueOf(userNode.getId()))));
        }
        return userNodeResults;
    }

    public UserNodeResult toSingleUserNodeResult(UserNode userNode){
        System.out.println(userNode.toString());
            return (new UserNodeResult(userNode, isFriend(String.valueOf(userNode.getId())), hasRequest(String.valueOf(userNode.getId())), alreadyRequested(String.valueOf(userNode.getId()))));
    }

    private UserNodeResult toFriendNodeResult(UserNode userNode){
        System.out.println(userNode.toString());
        if (userNode.getId() != sessionService.getId()){
            return (new UserNodeResult(userNode, true, false, false));
        }else return new UserNodeResult(userNode, false, false, false);
    }

    private UserNodeResult toRequestUserNodeResult(UserNode userNode){
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
        System.out.println("SessionUser ID: " + sessionService.getId());
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


}
