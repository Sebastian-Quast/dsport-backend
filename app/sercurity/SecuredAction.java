package sercurity;

import com.auth0.jwt.interfaces.DecodedJWT;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.JwtService;
import services.SessionService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class SecuredAction extends Action<Secured> {

    @Inject
    private JwtService jwtService;

    @Inject
    private SessionService sessionService;

    public CompletionStage<Result> call(Http.Context ctx) {

        List<Role> roles = Arrays.asList(configuration.value());

        Optional<DecodedJWT> jwt = jwtService.extractJwt(ctx, roles.stream().map(Enum::name).toArray(String[]::new));

        return jwt.map(decodedJWT -> {
            sessionService.setId(Long.parseLong(decodedJWT.getSubject()));
            sessionService.setRoles(decodedJWT.getAudience().stream().map(Role::valueOf).collect(Collectors.toList()));
            sessionService.setToken(decodedJWT.getToken());
            return delegate.call(ctx);
        }).orElse(CompletableFuture.completedFuture(unauthorized()));
    }
}
