package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import neo4j.config.Neo4jKeys;
import neo4j.nodes.User;
import neo4j.services.UserService;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import protocols.LoginProtocol;
import sercurity.Role;
import services.JwtService;

import javax.inject.Inject;

public class SessionController extends Controller {


    @Inject
    private JwtService jwtService;

    @Inject
    private UserService userService;

    public Result signup(){
        JsonNode json = request().body().asJson();
        User user = new User(
                json.findPath(Neo4jKeys.USERNAME).textValue(),
                json.findPath(Neo4jKeys.NAME).textValue(),
                json.findPath(Neo4jKeys.LASTNAME).textValue(),
                json.findPath(Neo4jKeys.EMAIL).textValue(),
                json.findPath(Neo4jKeys.PASSWORD).textValue(),
                json.findPath(Neo4jKeys.PICTURE).textValue());
        return ok(Json.toJson(userService.createOrUpdate(user)));
    }

    public Result login() {

        JsonNode json = request().body().asJson();

        //Empfangenes Passwort hashen
        LoginProtocol protocol = new LoginProtocol(json.findPath(Neo4jKeys.USERNAME).textValue(), json.findPath(Neo4jKeys.PASSWORD).textValue());

        //Load requested user from Database
        //User user = userService.findByCredentials(json.findPath(Neo4jKeys.USERNAME).textValue()).get();
        return userService.findByCredentials(protocol.getUsername(), protocol.getPassword())
                .flatMap(user -> jwtService.createAndSaveJwt(ctx(), user.getId(), Role.USER.name()).map(jwt -> user))
                .map(user -> ok(Json.toJson(user)))
                .orElseGet(() -> badRequest("Login failed"));

        //Verify if User exists with given password
        //return userService.verifyPw(user, json.findPath(Neo4jKeys.PASSWORD).textValue())
        //        .filter(f -> f)
        //        .map(f -> {
        //            jwtService.createJwt(user.getUsername(), Role.USER.name())
        //                    .map(jwt -> {
        //                        jwtService.setJwtHeader(ctx(), jwt);
        //                        return ok("Logged In");
        //                    }).orElseGet(Results::badRequest);
        //            return ok("Password Correct");
        //        })
        //        .orElse(unauthorized("Password Incorrect"));
    }

    public void logout(){
        //Destroy token or add it to a blacklist
    }


}
