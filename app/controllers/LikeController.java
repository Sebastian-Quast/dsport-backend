package controllers;

import neo4j.nodes.PostNode;
import neo4j.nodes.UserNode;
import neo4j.relationships.Like;
import neo4j.services.CommentService;
import neo4j.services.LikeService;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.Result;
import sercurity.Secured;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class LikeController extends AbstractController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private LikeService service;

    @Inject
    public LikeController(LikeService service, UserService userService, PostService postService, CommentService commentService) {
        this.service = service;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    //TODO Überprüfung ob Like existiert
    @Secured
    public Result likePost(String postId) {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(userNode -> postService.find(Long.valueOf(postId)).map(postNode -> F.Tuple(userNode, postNode)))
                .map(userNodePostNodeTuple -> service.createOrUpdate(new Like(userNodePostNodeTuple._1, userNodePostNodeTuple._2))));
    }


    @Secured
    public Result dislikePost(String postId) {
        session().clear();
        return userService.find(sessionService.getId())
                .flatMap(userNode -> postService.find(Long.valueOf(postId)).map(postNode -> F.Tuple(userNode, postNode))
                        .flatMap(userNodePostNodeTuple -> service.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2))
                        .map(like -> {
                            service.delete(like.getId());
                            return ok();
                        })).orElse(notFound());
    }


    @Secured
    public Result likeComment(String commentId) {
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(userNode -> commentService.find(Long.valueOf(commentId)).map(commentNode -> F.Tuple(userNode, commentNode)))
                .map(userNodeCommentNodeTuple -> service.createOrUpdate(new Like(userNodeCommentNodeTuple._1, userNodeCommentNodeTuple._2))));
    }

    @Secured
    public Result dislikeComment(String commentId) {
        return userService.find(sessionService.getId())
                .flatMap(userNode -> commentService.find(Long.valueOf(commentId)).map(commentNode -> F.Tuple(userNode, commentNode))
                        .flatMap(userNodePostNodeTuple -> service.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2))
                        .map(like -> {
                            service.delete(like.getId());
                            return toJsonResult(like);
                        })).orElse(notFound());
    }

    @Secured
    public Result getLikesByPost(String postId) {
        Set<UserNode> likesPost = new HashSet<>();
        return postService.find(Long.valueOf(postId))
                .map(postNode -> {
                    postNode.getLikes().forEach(like -> likesPost.add(like.getUserNode()));
                    return toJsonResult(likesPost);
                })
                .orElse(badRequest());
    }

    @Secured
    public Result getLikesByComment(String postId) {
        Set<UserNode> likesComment = new HashSet<>();
        return commentService.find(Long.valueOf(postId))
                .map(commentNode -> {
                    commentNode.getLikes().forEach(like -> likesComment.add(like.getUserNode()));
                    return toJsonResult(likesComment);
                })
                .orElse(badRequest());
    }

}
