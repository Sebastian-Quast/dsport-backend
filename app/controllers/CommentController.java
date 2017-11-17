package controllers;

import neo4j.nodes.CommentNode;
import neo4j.nodes.SocialNode;
import neo4j.nodes.resultnodes.SocialResultPair;
import neo4j.relationships.Refers;
import neo4j.services.*;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import protocols.CommentProtocol;
import sercurity.Secured;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

public class CommentController extends AbstractController {

    private UserService userService;
    private SocialService socialService;
    private CommentService commentService;
    private DataService dataService;

    @Inject
    public CommentController(UserService userService, SocialService socialService, CommentService commentService, DataService dataService) {
        this.userService = userService;
        this.socialService = socialService;
        this.commentService = commentService;
        this.dataService = dataService;
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

    @Secured
    @BodyParser.Of(CommentProtocol.Parser.class)
    public Result update() {
        CommentProtocol commentProtocol = request().body().as(CommentProtocol.class);
        return commentService.createOrUpdate(commentProtocol.toModel())
                .map(this::toJsonResult)
                .orElse(badRequest());
    }


    @Secured
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result updateCommentPicture(String id) {

        //Get image file from MultiPartFOrmData
        Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> image = multipartFormData.getFile("file");

        if (image != null) {
            String fileName = image.getFilename();
            String fileExtension = fileName.split("\\.")[1];
            File file = image.getFile();

            try {

                String url = dataService.move(file, fileExtension, DataService.Resource.COMMENT_IMAGE);

                return toOptionalJsonResult(commentService.find(Long.valueOf(id)).map(commentNode -> {
                    try {
                        if (commentNode.getPicture() != null) {
                            dataService.deleteWithURL(commentNode.getPicture(), DataService.Resource.COMMENT_IMAGE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    commentNode.setPicture(url);
                    return commentService.createOrUpdate(commentNode);
                }));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return badRequest();

    }

    @Secured
    public Result getComments(String id) {
        return toJsonResult(socialService.find(Long.valueOf(id), 2)
                .map(SocialNode::getComments).map(refers -> refers.stream().map(refers1 ->
                        new SocialResultPair("COMMENTNODE", refers1.getComment(), refers1.getComment().getCommented().getUserNode(),
                                userService.find(sessionService.getId()).map(userNode -> userNode.getLikes().stream().anyMatch(like -> like.getNode().getId().equals(refers1.getComment().getId()))).orElse(false)))));
    }


}
