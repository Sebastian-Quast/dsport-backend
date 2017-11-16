package controllers;

import com.google.common.collect.Lists;
import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.post.Posted;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.PostProtocol;
import sercurity.Role;
import sercurity.Secured;

import javax.inject.Inject;

public class PostController extends AbstractCRUDController<PostNode, PostService> {

    private UserService userService;

    @Inject
    public PostController(PostService service, UserService userService) {
        super(service);
        this.userService = userService;
    }

    @Secured
    public Result findPosts(){
        return service.findPosts(request().body().asJson().get("query").asText())
                .map(postNodes -> toJsonResult(Lists.newArrayList(postNodes)))
                .orElse(badRequest());
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

    @Secured
    public Result getPosts(String fromId){
        if (shouldReadPosts(fromId)){
            return toOptionalJsonResult(userService.find(Long.valueOf(fromId))
                    .map(UserNode::getPostings)
                    .map(posteds -> posteds.stream().map(Posted::getPostNode)));
        }else return forbidden();
    }


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
        return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().equals(existing)) || sessionService.hasRole(Role.ADMIN)).orElse(false);
    }

    public boolean shouldReadPosts(String fromId) {
        return userService.find(sessionService.getId()).map(userNode -> userNode.getFriendships().stream().anyMatch(friendship -> friendship.getId().equals(fromId)) || sessionService.getId().equals(Long.valueOf(fromId))).orElse(false);
    }

    @Override
    public boolean shouldDelete(PostNode existing) {
        return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().equals(existing)) || sessionService.hasRole(Role.ADMIN)).orElse(false);
    }

    @Override
    public boolean shouldCreate(PostNode toCreate) {
        return true;
    }

    @Override
    public boolean shouldUpdate(PostNode toUpdate) {
        return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().getId().equals(toUpdate.getId())) || sessionService.hasRole(Role.ADMIN)).orElse(false);
    }

}
