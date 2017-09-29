package controllers;

import neo4j.nodes.PostNode;
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
    @BodyParser.Of(PostProtocol.Parser.class)
    public Result postTo(String toId) {
        PostProtocol postProtocol = request().body().as(PostProtocol.class);
        if(shouldCreate(postProtocol.toModel())) {
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
        if(shouldUpdate(postProtocol.toModel())){
            return service.createOrUpdate(postProtocol.toModel())
                    .map(this::toJsonResult)
                    .orElse(badRequest());
        } else return forbidden();
    }

    @Override
    public Result create() {
        return notFound();
    }

    public Result getPinnboard(){
        return null;
    }

    @Override
    public boolean shouldRead(PostNode existing) {
        //TODO Or friend of user or pinned to users pinboard
        return userService.find(sessionService.getId()).map(user -> user.getPostings().stream().anyMatch(posted -> posted.getPostNode().equals(existing)) || sessionService.hasRole(Role.ADMIN)).orElse(false);
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
