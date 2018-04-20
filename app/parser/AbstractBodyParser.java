package parser;


import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F;
import play.libs.streams.Accumulator;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.Executor;

public abstract class AbstractBodyParser<T> implements BodyParser<T> {

    @Inject
    private Json jsonParser;

    @Inject
    private Executor executor;

    @Override
    public Accumulator<ByteString, F.Either<Result, T>> apply(Http.RequestHeader request) {
        Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);

        return jsonAccumulator.map(resultOrJson -> {
            if (resultOrJson.left.isPresent()) {
                return F.Either.Left(resultOrJson.left.get());
            } else {
                JsonNode json = resultOrJson.right.get();
                try {
                    return F.Either.Right(play.libs.Json.fromJson(json, getType()));
                } catch (Exception e) {
                    return F.Either.Left(Results.badRequest(
                            "Missing Path: "+e.getLocalizedMessage()));
                }
            }
        }, executor);
    }

    public abstract Class<T> getType();
}


