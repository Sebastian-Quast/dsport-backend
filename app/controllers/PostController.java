package controllers;

import com.google.common.collect.Lists;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Posted;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.PostProtocol;
import sercurity.Role;
import sercurity.Secured;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostController extends AbstractCRUDController<PostNode, PostService> {

    private UserService userService;

    @Inject
    public PostController(PostService service, UserService userService) {
        super(service);
        this.userService = userService;
    }

    //test method, delete later to enable secured function
    @Override
    public Result byId(String id) {
        return service.find(Long.valueOf(id))
                .map(node -> toJsonResult(node))
                .orElse(badRequest(languageService.get("notFound")));
    }

    @Secured
    @BodyParser.Of(PostProtocol.Parser.class)
    public Result postTo(String toId) {
        PostProtocol postProtocol = request().body().as(PostProtocol.class);
        if (shouldCreate(postProtocol.toModel())) {
            return userService.find(sessionService.getId())
                    .flatMap(from -> userService.find(Long.valueOf(toId)).map(to -> F.Tuple(from, to)))
                    .flatMap(fromTo -> service.ifNotExists(postProtocol.toModel()).map(post -> F.Tuple3(fromTo._1, fromTo._2, post)))
                    .map(fromToPost -> {
                        fromToPost._3.addPinned(fromToPost._2);
                        fromToPost._3.addPosted(fromToPost._1);
                        return toJsonResult(service.createOrUpdate(fromToPost._3));
                    })
                    .orElse(badRequest());
        } else return forbidden();
    }


    @Secured
    @BodyParser.Of(PostProtocol.Parser.class)
    @Override
    public Result update() {
        PostProtocol postProtocol = request().body().as(PostProtocol.class);
        if (shouldUpdate(postProtocol.toModel())) {
            return service.createOrUpdate(postProtocol.toModel())
                    .map(this::toJsonResult)
                    .orElse(badRequest());
        } else return forbidden();
    }

    @Override
    public Result create() {
        return notFound();
    }

    //@Secured
    //public Result getPosts(String fromId){

    //    Set<PostNode> postNodes = new HashSet<>();

    //    if (shouldReadPosts(fromId)){
    //        return userService.find(Long.valueOf(fromId))
    //                .map(UserNode::getPostings)
    //                .map(posteds -> {
    //                    posteds.forEach(posted ->
    //                            postNodes.add(posted.getPostNode()));
    //                    return toJsonResult(postNodes);
    //                })
    //                .orElse(badRequest());
    //    }else return forbidden();
    //}


    /**
     * @param fromId Id of the user which posts and pins should are requested
     * @return A List of all posts and pins of a specific user
     */
    public Result getPinboard(String fromId) {
        if (shouldReadPosts(fromId)) {
            return service.findAll(fromId)
                    .map(postNode -> toJsonResult(Lists.newArrayList(postNode)))
                    .orElse(badRequest());
        } else return forbidden();
    }

    @Override
    public boolean shouldRead(PostNode existing) {
        //TODO Or friend of user or pinned to users pinboard
        //return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().equals(existing)) || sessionService.hasRole(Role.ADMIN)).orElse(false);
        return true;
    }

    public boolean shouldReadPosts(String fromId) {
        //return userService.find(sessionService.getId()).map(userNode -> userNode.getFriendships().stream().anyMatch(friendship -> friendship.getId().equals(fromId)) || sessionService.getId().equals(Long.valueOf(fromId))).orElse(false);
        return true;
    }

    @Override
    public boolean shouldDelete(PostNode existing) {
        //return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().equals(existing)) || sessionService.hasRole(Role.ADMIN)).orElse(false);
        return true;
    }

    @Override
    public boolean shouldCreate(PostNode toCreate) {
        return true;
    }

    @Override
    public boolean shouldUpdate(PostNode toUpdate) {
        //return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().getId().equals(toUpdate.getId())) || sessionService.hasRole(Role.ADMIN)).orElse(false);
        return true;
    }

}
