package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LanguageService;
import services.SessionService;

import javax.inject.Inject;
import java.util.Optional;

public abstract class AbstractController extends Controller {


    @Inject
    protected LanguageService languageService;

    @Inject
    protected SessionService sessionService;

    public Result toJsonResult(Object o){
        return ok(Json.toJson(o));
    }

    public Result toOptionalJsonResult(Optional<?> optional){
        return toOptionalJsonResult(optional, badRequest(languageService.get("somethingWentWrong")));
    }

    public Result toOptionalJsonResult(Optional<?> optional, Result fault){
        return optional.map(this::toJsonResult).orElse(fault);
    }
}
