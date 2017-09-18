package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import sercurity.Role;
import sercurity.Secured;
import services.JwtService;

import javax.inject.Inject;

public class TestController extends Controller {

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

}
