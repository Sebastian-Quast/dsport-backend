package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.services.UserService;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import protocols.LoginProtocol;
import sercurity.Role;
import sercurity.Secured;
import services.JwtService;

import javax.inject.Inject;

public class TestController extends Controller {


    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    UserService userService;

    @Inject
    private JwtService jwtService;

    public Result login(){

        return jwtService.createJwt("Sebastian", Role.USER.name())
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

    @BodyParser.Of(LoginProtocol.Parser.class)
    public Result save() {
        Http.RequestBody body = request().body();
        LoginProtocol user = body.as(LoginProtocol.class);

        return ok(Json.toJson(user));
    }

}
