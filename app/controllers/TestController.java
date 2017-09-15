package controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtBuilder;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sercurityAlt.Role;
import sercurityAlt.Secured;
import services.JwtService;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    @Secured({Role.USER})
    public Result testVerify(){

        String userId = (String) ctx().args.get("id");

        return ok("Access for "+userId);
    }

}
