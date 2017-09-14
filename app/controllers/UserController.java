package controllers;

import neo4j.nodes.Post;
import neo4j.nodes.User;
import neo4j.services.UserService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class UserController extends Controller {

    @Inject
    private UserService userService;

    public Result add(){
        Post post = new Post("Neuer Post 1");

        User user = new User("Peter");
        user.addPost(post);
        userService.createOrUpdate(user);

        User user3 = new User("Lümmel");
        userService.createOrUpdate(user3);

        Post post2 = new Post("Neuer Post 2");

        User user1 = new User("Igon");
        user1.addFriend(user);
        user1.addFriend(user3);
        user1.addPost(post2);

        return ok(Json.toJson(userService.createOrUpdate(user1)));

    }

}
