package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.services.UserService;
import play.libs.Json;
import play.mvc.*;
import protocols.LoginProtocol;
import sercurity.Role;
import sercurity.Secured;
import services.JwtService;
import services.MailerService;

import javax.inject.Inject;
import java.util.UUID;

public class TestController extends Controller {


    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    UserService userService;

    @Inject
    private JwtService jwtService;

    @Inject
    private MailerService mailerService;

    public Result login(){

        return jwtService.createJwt(2L, Role.USER.name())
                .map(jwt -> {
                    jwtService.setJwtHeader(ctx(), jwt);
                    return ok("Logged In");
                }).orElse(badRequest());
    }

    @Secured
    public Result testVerify(){

        String userId = (String) ctx().args.get("id");

        return ok("Access for "+userId);
    }

    public Result testMail(String to){
        String link = "https://daily-sport.de:8002/register/"+ UUID.randomUUID().toString();
        return mailerService.send(to, "Herzlich willkommen bei dSport", "Bitte bestätige deine Registration.", "Damit du deinen Account vollständig nutzen kannst, ist es notwendig, " +
                "deine Registration zu bestätigen, dazu nutze den folgenden Link " +
                "<a href=\""+ link+"\">" + link + "</a>")
                .map(Results::ok)
                .orElse(badRequest());
    }

    @BodyParser.Of(LoginProtocol.Parser.class)
    public Result save() {
        Http.RequestBody body = request().body();
        LoginProtocol user = body.as(LoginProtocol.class);

        return ok(Json.toJson(user));
    }

}
