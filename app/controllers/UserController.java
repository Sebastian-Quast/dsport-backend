package controllers;

import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.nodes.UserNode;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.UserProtocol;
import sercurity.Role;
import sercurity.Secured;
import services.SessionService;

import javax.inject.Inject;

public class UserController extends AbstractCRUDController<UserNode, UserService> {

    private SessionService sessionService;

    @Inject
    public UserController(UserService service, SessionService sessionService) {
        super(service);
        this.sessionService = sessionService;
    }


    @BodyParser.Of(UserProtocol.Parser.class)
    @Secured(Role.ADMIN)
    @Override
    public Result create() {

        UserProtocol protocol = request().body().as(UserProtocol.class);

        try {
            return toOptionalJsonResult(service.createOrUpdate(protocol.toModel()));
        } catch (UsernameAlreadyExistsException e) {
            return badRequest(languageService.get("usernameAlreadyExists"));
        } catch (EmailAlreadyExistsException e) {
            return badRequest(languageService.get("emailAlreadyExists"));
        }
    }

    @Override
    public boolean shouldRead(UserNode existing) {
        return false;
    }

    @Override
    public boolean shouldDelete(UserNode existing) {
        return false;
    }

    @Override
    public boolean shouldCreate(UserNode toCreate) {
        return false;
    }

    @Override
    public boolean shouldUpdate(UserNode toUpdate) {
        return false;
    }

    @Secured
    @BodyParser.Of(UserProtocol.Parser.class)
    @Override
    public Result update() {

        UserProtocol protocol = request().body().as(UserProtocol.class);

        if (!sessionService.isUser(protocol.id) && !sessionService.hasRole(Role.ADMIN)) {
            return unauthorized();
        }

        try {
            return toJsonResult(service.createOrUpdate(protocol.toModel()));
        } catch (UsernameAlreadyExistsException e) {
            return badRequest(languageService.get("usernameAlreadyExists"));
        } catch (EmailAlreadyExistsException e) {
            return badRequest(languageService.get("emailAlreadyExists"));
        }
    }

    @BodyParser.Of(UserProtocol.Parser.class)
    public Result updatetest() {

        UserProtocol protocol = request().body().as(UserProtocol.class);
        return toOptionalJsonResult(service.createOrUpdate(protocol.toModel()));

    }


    //test method, delete later to enable secured function
    @Override
    public Result byId(String id) {
        return service.find(Long.valueOf(id), 1)
                .map(this::toJsonResult)
                .orElse(badRequest(languageService.get("notFound")));
    }


    @Secured
    public Result getLikes() {
        return service.find(sessionService.getId(), 2).map(userNode -> toJsonResult(userNode.getLikes())).orElse(badRequest());
    }
}
