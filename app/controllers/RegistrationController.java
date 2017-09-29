package controllers;

import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import neo4j.nodes.RegistrationNode;
import neo4j.services.RegistrationService;
import neo4j.services.UserService;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.RegistrationProtocol;
import services.MailerService;

import javax.inject.Inject;
import java.util.Optional;

public class RegistrationController extends AbstractController{

    private RegistrationService registrationService;
    private MailerService mailerService;
    private UserService userService;

    @Inject
    public RegistrationController(RegistrationService registrationService, MailerService mailerService, UserService userService) {
        this.registrationService = registrationService;
        this.mailerService = mailerService;
        this.userService = userService;
    }

    @BodyParser.Of(RegistrationProtocol.Parser.class)
    public Result register() {
        RegistrationProtocol protocol = request().body().as(RegistrationProtocol.class);
        try{
            Optional<RegistrationNode> node = registrationService.createOrUpdate(protocol.toModel());
            return node
                   .flatMap(registrationNode ->
                       mailerService.send(protocol.email,
                       languageService.get("registrationEmailTitle"),
                       languageService.get("registrationEmailSubject"),
                       languageService.get("registrationEmailText") + " <a href=\"https://" + getRegistrationLink(registrationNode.getHash()) + "\">"+ getRegistrationLink(registrationNode.getHash()) +"</a>")
                           .map(s -> registrationNode))
                   .map(this::toJsonResult)
                   .orElseGet(() -> node.map(registrationNode -> {

                       registrationService.delete(registrationNode.getId());
                       return badRequest(languageService.get("somethingWentWrong"));
                   })
                   .orElseGet(() -> badRequest("somethingWentWrong")));
        } catch (UsernameAlreadyExistsException e) {
            return badRequest(languageService.get("usernameAlreadyExists"));
        } catch (EmailAlreadyExistsException e) {
            return badRequest(languageService.get("emailAlreadyExists"));
        }
    }

    private String getRegistrationLink(String hash) {
        return request().host()+"/register/" + hash;
    }


    public Result confirmRegistration(String hash){
        return registrationService.findByHash(hash)
                .flatMap(registrationNode -> userService.createByRegistrationNode(registrationNode)
                        .map(userNode -> {
                            registrationService.delete(registrationNode.getId());
                            return ok(views.html.welcome.render());
                        })).orElse(badRequest(languageService.get("somethingWentWrong")));
    }
}
