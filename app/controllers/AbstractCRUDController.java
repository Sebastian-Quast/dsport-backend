package controllers;

import neo4j.nodes.AbstractNode;
import neo4j.services.AbstractService;
import play.mvc.Result;
import sercurity.Role;
import sercurity.Secured;

public abstract class AbstractCRUDController<NodeType extends AbstractNode, ServiceType extends AbstractService<NodeType>> extends AbstractController{

    protected final ServiceType service;

    public AbstractCRUDController(ServiceType service){
        this.service = service;
    }

    //@Secured(Role.ADMIN)
    public Result all(){
        return toJsonResult(service.findAll());
    }

    public Result byId(String id){
        return service.find(Long.valueOf(id)).map( node -> {
            if(shouldRead(node)) return toJsonResult(node);
            else return forbidden();
        }).orElse(badRequest(languageService.get("notFound")));
    }

    public abstract Result update();
    public abstract Result create();

    public Result delete(String id) {
        return service.find(Long.valueOf(id)).map(node -> {
            if(shouldDelete(node)){
                service.delete(node.getId());
                return toJsonResult(node);
            } else return forbidden();
        }).orElseGet(() -> badRequest(languageService.get("notFound")));

    }

    public abstract boolean shouldRead(NodeType existing);
    public abstract boolean shouldDelete(NodeType existing);
    public abstract boolean shouldCreate(NodeType toCreate);
    public abstract boolean shouldUpdate(NodeType toUpdate);
}
