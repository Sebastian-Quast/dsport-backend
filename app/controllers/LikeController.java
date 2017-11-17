package controllers;

import neo4j.relationships.like.Like;
import neo4j.services.SocialService;
import neo4j.services.UserService;
import play.mvc.Result;
import sercurity.Secured;

import javax.inject.Inject;
import java.util.Optional;

public class LikeController extends AbstractController {

    private UserService userService;
    private SocialService socialService;

    @Inject
    public LikeController(SocialService socialService, UserService userService) {
        this.userService = userService;
        this.socialService = socialService;
    }

    @Secured
    public Result like(String id) {
        session().clear();
        return userService.find(sessionService.getId())
                .flatMap(userNode -> socialService.find(Long.valueOf(id))
                        .map(likeableNode -> socialService.findExistingLike(userNode, likeableNode)
                        .flatMap(like -> socialService.dislike(like))
                        .map(likeableNode1 -> toJsonResult(likeableNode1.getLikes().size()))
                        .orElseGet(() -> {
                            socialService.like(userNode, likeableNode);
                            return toJsonResult(likeableNode.getLikes().size());
                        }))).orElse(badRequest());

    }



    @Secured
    public Result getLikes(String id) {
        return toOptionalJsonResult(socialService.find(Long.valueOf(id)).map(likeableNode -> likeableNode.getLikes().stream().map(Like::getUser)));
    }

}
