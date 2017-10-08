package controllers;


import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.LoginProtocol;
import sercurity.Role;
import services.JwtService;

import javax.inject.Inject;

public class SessionController extends AbstractController {


    @Inject
    private JwtService jwtService;

    @Inject
    private UserService userService;


    @BodyParser.Of(LoginProtocol.Parser.class)
    public Result login() {
        LoginProtocol loginProtocol = request().body().as(LoginProtocol.class);
        return userService.findByCredentials(loginProtocol.getUsername(), loginProtocol.getPassword())
                .flatMap(user -> jwtService.createAndSaveJwt(ctx(), user.getId(), Role.USER.name()).map(jwt -> user))
                .map(this::toJsonResult)
                .orElseGet(() -> badRequest("Login failed"));
    }


}
