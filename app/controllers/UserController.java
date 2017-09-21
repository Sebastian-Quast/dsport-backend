package controllers;

import neo4j.nodes.User;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.UserProtocol;
import sercurity.Role;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;

public class UserController extends AbstractController<User, UserService> {

    private SessionService sessionService;

    @Inject
    public UserController(UserService service, SessionService sessionService) {
        super(service);
        this.sessionService = sessionService;
    }


    @Override
    public Result create() {


        return null;
    }

    @Secured
    @BodyParser.Of(UserProtocol.Parser.class)
    @Override
    public Result update() {

        UserProtocol protocol = request().body().as(UserProtocol.class);

        if(!sessionService.isUser(protocol.getId()) && !sessionService.hasRole(Role.ADMIN)){
            return unauthorized();
        }

        return toJsonResult(service.createOrUpdate(protocol.toModel()));
    }


}
