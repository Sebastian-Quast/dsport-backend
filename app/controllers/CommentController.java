package controllers;

import neo4j.nodes.CommentNode;
import neo4j.services.CommentService;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.CommentProtocol;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;

public class CommentController extends AbstractCRUDController<CommentNode, CommentService> {

    private UserService userService;
    private PostService postService;

    @Inject
    public CommentController(CommentService service, UserService userService, PostService postService) {
        super(service);
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    @Secured
    @BodyParser.Of(CommentProtocol.Parser.class)
    public Result update() {
        CommentProtocol commentProtocol = request().body().as(CommentProtocol.class);
            return service.createOrUpdate(commentProtocol.toModel())
                    .map(this::toJsonResult)
                    .orElse(badRequest());
    }

    @Override
    public Result create() {
        return notFound();
    }

    @Secured
    @BodyParser.Of(CommentProtocol.Parser.class)
    public Result createComment(String toId) {
        CommentProtocol commentProtocol = request().body().as(CommentProtocol.class);
        return userService.find(sessionService.getId())
                .flatMap(from -> postService.find(Long.valueOf(toId)).map(to -> F.Tuple(from, to)))
                .flatMap(fromTo -> service.ifNotExists(commentProtocol.toModel()).map(comment -> F.Tuple3(fromTo._1, fromTo._2, comment)))
                .map(fromToComment -> {
                    fromToComment._3.addCommented(fromToComment._2);
                    fromToComment._3.addCommentedBy(fromToComment._1);
                    return toJsonResult(service.createOrUpdate(fromToComment._3));
                }).orElse(badRequest());
    }


    //test method, delete later to enable secured function
    @Override
    public Result delete(String id) {
        return service.find(Long.valueOf(id)).map(node -> {
                service.delete(node.getId());
                return toJsonResult(node);
        }).orElseGet(() -> badRequest(languageService.get("notFound")));
    }


    @Override
    public Result byId(String id){
        return service.find(Long.valueOf(id))
                .map( node -> toJsonResult(node))
                .orElse(badRequest(languageService.get("notFound")));
    }

    @Override
    public boolean shouldRead(CommentNode existing) {
        return false;
    }

    @Override
    public boolean shouldDelete(CommentNode existing) {
        return false;
    }

    @Override
    public boolean shouldCreate(CommentNode toCreate) {
        return false;
    }

    @Override
    public boolean shouldUpdate(CommentNode toUpdate) {
        return false;
    }

}
