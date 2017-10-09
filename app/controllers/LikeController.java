package controllers;

import neo4j.nodes.UserNode;
import neo4j.relationships.like.LikeComment;
import neo4j.relationships.like.LikePost;
import neo4j.services.CommentService;
import neo4j.services.likeServices.LikeCommentService;
import neo4j.services.likeServices.LikePostService;
import neo4j.services.PostService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.Result;
import sercurity.Secured;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LikeController extends AbstractController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private LikePostService likePostService;
    private LikeCommentService likeCommentService;

    @Inject
    public LikeController(LikePostService likePostService, LikeCommentService likeCommentService, UserService userService, PostService postService, CommentService commentService) {
        this.likePostService = likePostService;
        this.likeCommentService = likeCommentService;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }


    @Secured
    public Result likePost(String postId) {
        if (!hasLikedPost(postId).isPresent()){
            return toOptionalJsonResult(userService.find(sessionService.getId())
                    .flatMap(userNode -> postService.find(Long.valueOf(postId)).map(postNode -> F.Tuple(userNode, postNode)))
                    .map(userNodePostNodeTuple -> likePostService.createOrUpdate(new LikePost(userNodePostNodeTuple._1, userNodePostNodeTuple._2))));
        }else return dislikePost(postId);

    }


    @Secured
    public Result dislikePost(String postId) {
        if (hasLikedPost(postId).isPresent()) {
            return userService.find(sessionService.getId())
                    .flatMap(userNode -> postService.find(Long.valueOf(postId)).map(postNode -> F.Tuple(userNode, postNode))
                            .flatMap(userNodePostNodeTuple -> likePostService.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2))
                            .map(like -> {
                                likePostService.delete(like.getId());
                                return ok();
                            })).orElse(notFound());
        }else return likePost(postId);
    }


    @Secured
    public Result likeComment(String commentId) {
        if (!hasLikedComment(commentId).isPresent()){
            return toOptionalJsonResult(userService.find(sessionService.getId())
                    .flatMap(userNode -> commentService.find(Long.valueOf(commentId)).map(commentNode -> F.Tuple(userNode, commentNode)))
                    .map(userNodeCommentNodeTuple -> likeCommentService.createOrUpdate(new LikeComment(userNodeCommentNodeTuple._1, userNodeCommentNodeTuple._2))));
        }else return dislikeComment(commentId);

    }

    @Secured
    public Result dislikeComment(String commentId) {
        if (hasLikedComment(commentId).isPresent()){
            return userService.find(sessionService.getId())
                    .flatMap(userNode -> commentService.find(Long.valueOf(commentId)).map(commentNode -> F.Tuple(userNode, commentNode))
                            .flatMap(userNodePostNodeTuple -> likeCommentService.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2))
                            .map(like -> {
                                likeCommentService.delete(like.getId());
                                return ok();
                            })).orElse(notFound());
        }else return likeComment(commentId);

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


    private Optional<LikePost> hasLikedPost(String id){
        return userService.find(sessionService.getId())
                .flatMap(userNode -> postService.find(Long.valueOf(id)).map(postNode -> F.Tuple(userNode, postNode))
                        .flatMap(userNodePostNodeTuple -> likePostService.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2)));
    }

    private Optional<LikeComment> hasLikedComment(String id){
        return userService.find(sessionService.getId())
                .flatMap(userNode -> commentService.find(Long.valueOf(id)).map(postNode -> F.Tuple(userNode, postNode))
                        .flatMap(userNodePostNodeTuple -> likeCommentService.findLike(userNodePostNodeTuple._1, userNodePostNodeTuple._2)));
    }
}
