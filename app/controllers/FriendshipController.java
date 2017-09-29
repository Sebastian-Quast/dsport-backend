package controllers;

import neo4j.relationships.FriendshipRequest;
import neo4j.services.UserService;
import play.mvc.Result;
import play.libs.F;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;

/**
 * Created by Florian on 27.09.2017.
 */
public class FriendshipController extends AbstractController {

    private UserService userService;
    private SessionService sessionService;

    @Inject
    public FriendshipController(UserService userService, SessionService sessionService){
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public Result accept(String id) {
        return ok();
    }

    @Secured
    public Result request(String id){
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(user -> userService.find(Long.valueOf(id)).map(friend -> F.Tuple(user, friend)))
                .map(userFriend -> userService.requestFriendship(new FriendshipRequest(userFriend._1, userFriend._2))));
    }

}
