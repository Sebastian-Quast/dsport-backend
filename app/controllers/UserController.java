package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import neo4j.config.Neo4jKeys;
import neo4j.services.UserService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sercurity.Secured;

import javax.inject.Inject;

public class UserController extends Controller {

    @Inject
    private UserService userService;

    public Result load(){
        JsonNode json = request().body().asJson();
        return userService.find(json.get(Neo4jKeys.ID).asLong())
                .map(user -> ok(Json.toJson(user))) //Not Null
                .orElse(notFound("User not found"));// If Null
    }

    //@Secured
    public Result updateUser() {
        JsonNode json = request().body().asJson();
        return userService.find(json.get(Neo4jKeys.ID).asLong())
                .map(user -> {
                    userService.updateUser(user, json.findPath(Neo4jKeys.KEY).textValue(), json.findPath(Neo4jKeys.VALUE).textValue());
                    return ok(Json.toJson(user));
                })
                .orElse(notFound("User not Found"));// If Null)
    }

    //@Secured
    public Result deleteUser() {
        JsonNode json = request().body().asJson();

       return userService.find(json.get(Neo4jKeys.ID).asLong())
                .map(user -> {
                    userService.delete(user.getId());
                    return ok("User sucessfully deleted");
                })
                .orElse(notFound("User not found"));
    }


}
