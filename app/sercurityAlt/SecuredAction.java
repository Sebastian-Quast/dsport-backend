package sercurityAlt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.typesafe.config.Config;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.JwtService;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SecuredAction extends Action<Secured> {

    @Inject
    private JwtService jwtService;

    public CompletionStage<Result> call(Http.Context ctx) {

        List<Role> roles = Arrays.asList(configuration.value());

        Optional<DecodedJWT> jwt = jwtService.extractJwt(ctx, roles.stream().map(Enum::name).toArray(String[]::new));

        return jwt.map(decodedJWT -> {
            //jwtService.setJwtHeader(ctx, decodedJWT.getToken());
            ctx.args.put("id", decodedJWT.getSubject());
            return delegate.call(ctx);
        }).orElse(CompletableFuture.completedFuture(unauthorized()));
    }
}
