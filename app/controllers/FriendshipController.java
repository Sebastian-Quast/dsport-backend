package controllers;

import neo4j.nodes.UserNode;
import neo4j.relationships.Friendship;
import neo4j.relationships.FriendshipRequest;
import neo4j.services.FriendRequestService;
import neo4j.services.FriendshipService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.Result;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;


public class FriendshipController extends AbstractController {

    private FriendRequestService friendRequestService;
    private FriendshipService friendshipService;
    private UserService userService;
    private SessionService sessionService;

    @Inject
    public FriendshipController(FriendRequestService friendRequestService, FriendshipService friendshipService, UserService userService, SessionService sessionService) {
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Secured
    public Result request(String id) {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(user -> userService.find(Long.valueOf(id)).map(friend -> F.Tuple(user, friend)))
                .map(userFriend -> friendRequestService.createOrUpdate(new FriendshipRequest(userFriend._1, userFriend._2))));
    }

     @Secured
     public Result accept(String id) {
         return userService.getUserTuple(sessionService.getId(), Long.valueOf(id))
                 .flatMap(userNodeUserNodeTuple -> friendRequestService.findRequested(userNodeUserNodeTuple._1, userNodeUserNodeTuple._2))
                 .map(friendshipRequest -> {
                    friendRequestService.delete(friendshipRequest.getId());
                    return toJsonResult(friendshipService.createOrUpdate(new Friendship(friendshipRequest.getUserNode(), friendshipRequest.getFriend())));
                 }).orElse(badRequest());
     }


    @Secured
    public Result decline(String id) {
        return userService.getUserTuple(sessionService.getId(), Long.valueOf(id))
                .flatMap(userNodeUserNodeTuple -> friendRequestService.findRequested(userNodeUserNodeTuple._1, userNodeUserNodeTuple._2))
                .map(friendshipRequest -> {
                    friendRequestService.delete(friendshipRequest.getId());
                    return ok();
                }).orElse(badRequest());
    }

    @Secured
    public Result delete(String id) {
        return userService.getUserTuple(sessionService.getId(), Long.valueOf(id))
                .flatMap(userNodeUserNodeTuple -> friendshipService.findFriendship(userNodeUserNodeTuple._1, userNodeUserNodeTuple._2))
                .map(friendship -> {
                    friendshipService.delete(friendship.getId());
                    return ok();
                }).orElse(badRequest());
    }


}
