package controllers;

import neo4j.nodes.AbstractNode;
import neo4j.services.AbstractService;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.LanguageService;

import javax.inject.Inject;
import java.util.Optional;

public abstract class AbstractController<T extends AbstractNode, S extends AbstractService<T>> extends Controller {

    protected S service;

    @Inject
    protected LanguageService languageService;

    public AbstractController(S service){
        this.service = service;
    }



    public Result all(){
        return toJsonResult(service.findAll());
    }

    public Result byId(String id){
        return service.find(Long.valueOf(id)).map(this::toJsonResult).orElse(badRequest(languageService.get("notFound")));
    }

    public abstract Result update();
    public abstract Result create();

    public Result delete(String id){
        service.delete(Long.valueOf(id));
        return toJsonResult("Ok");
    }

    public Result toJsonResult(Object o){
        return ok(Json.toJson(o));
    }

    public Result toOptionalJsonResult(Optional<?> optional){
        return (Result) optional.map(this::toJsonResult).orElse(badRequest(languageService.get("somethingWentWrong")));
    }
}
