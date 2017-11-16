package controllers;

import neo4j.nodes.CommentNode;
import neo4j.nodes.SocialNode;
import neo4j.relationships.Refers;
import neo4j.services.*;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.CommentProtocol;
import sercurity.Secured;

import javax.inject.Inject;

public class CommentController extends AbstractCRUDController<CommentNode, CommentService> {

    private UserService userService;
    private SocialService socialService;
    private CommentService commentService;

    @Inject
    public CommentController(CommentService service, UserService userService, SocialService socialService, CommentService commentService) {
        super(service);
        this.userService = userService;
        this.socialService = socialService;
        this.commentService = commentService;
    }

    @Secured
    @BodyParser.Of(CommentProtocol.Parser.class)
    public Result comment(String id) {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(userNode -> socialService.find(Long.valueOf(id)).map(socialNode -> F.Tuple(userNode, socialNode)))
                .flatMap(ust -> {
                    CommentNode commentNode = request().body().as(CommentProtocol.class).toModel();
                    commentNode.setCommented(ust._1);
                    commentNode.setRefers(ust._2);
                    return commentService.createOrUpdate(commentNode);
                }));
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
    public Result getComments(String id) {
        return toJsonResult(socialService.find(Long.valueOf(id), 1)
                .map(SocialNode::getComments)
                .map(refers -> refers.stream().map(Refers::getComment)));
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
