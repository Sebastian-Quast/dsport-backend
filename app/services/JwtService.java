package services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.typesafe.config.Config;
import play.mvc.Http;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public class JwtService {

    private Config config;


    @Inject
    public JwtService(Config config) {
        this.config = config;
    }

    public Optional<String> extractJwtHeader(Http.Context ctx){
        return ctx.request().getHeaders().get(config.getString("auth.header"));
    }

    public Optional<DecodedJWT> verify(String jwtString, String... audiences) {

        return getAlgorithm().flatMap(algorithm -> {
            DecodedJWT jwt = null;
            JWTVerifier verifier = JWT.require(algorithm)
                    .withAudience(audiences)
                    .build();
            try {
                jwt = verifier.verify(jwtString);
            } catch (JWTVerificationException exception){
                //Unauthorized
            }

            return Optional.ofNullable(jwt);
        });
    }

    public Optional<DecodedJWT> extractJwt(Http.Context ctx, String... audiences){
        return extractJwtHeader(ctx).flatMap(jwtString -> verify(jwtString, audiences));
    }

    public Optional<String> createJwt(String subject, String... audiences){

        return getAlgorithm().map(algorithm -> JWT.create()
                .withAudience(audiences)
                .withSubject(subject)
                .sign(algorithm));
    }

    public Optional<Algorithm> getAlgorithm(){
        Algorithm algorithm = null;
        try {
            algorithm =  Algorithm.HMAC256(config.getString("auth.secret"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(algorithm);
    }

    public void setJwtHeader(Http.Context ctx, String jwt){
        ctx.response().setHeader(config.getString("auth.header"), jwt);
    }

    public Optional<String> createAndSaveJwt(Http.Context ctx, String subject, String... audiences){
        return createJwt(subject, audiences).map(jwt -> {
            setJwtHeader(ctx, jwt);
            return jwt;
        });
    }
}
