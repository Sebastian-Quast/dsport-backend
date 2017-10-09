package controllers;

import neo4j.services.ExerciseService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.DistanceBasedProtocol;
import protocols.ExerciseProtocol;
import protocols.RepeatBasedProtocol;
import protocols.TimeBasedProtocol;
import sercurity.Secured;

import javax.inject.Inject;

public class ExerciseController extends AbstractController {

    private UserService userService;
    private ExerciseService service;

    @Inject
    public ExerciseController(ExerciseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Secured
    @BodyParser.Of(ExerciseProtocol.Parser.class)
    public Result createExercise(){
        ExerciseProtocol exerciseProtocol = request().body().as(ExerciseProtocol.class);

        return service.createOrUpdate(exerciseProtocol.toModel())
                .flatMap(exerciseNode -> userService.find(sessionService.getId()).map(userNode -> F.Tuple(exerciseNode, userNode)))
                .map(exerciseNodeUserNodeTuple -> {
                    exerciseNodeUserNodeTuple._1.addExercised(exerciseNodeUserNodeTuple._2);
                    return toJsonResult(service.createOrUpdate(exerciseNodeUserNodeTuple._1));
                }).orElse(badRequest());
    }

    @Secured
    @BodyParser.Of(DistanceBasedProtocol.Parser.class)
    public Result addDistanceBasedSet(String id){
        if(shouldCreate(id)){
            return service.find(Long.valueOf(id))
                    .map(exerciseNode -> {
                        exerciseNode.addDistanceBased(request().body().as(DistanceBasedProtocol.class).toModel());
                        return toJsonResult(service.createOrUpdate(exerciseNode));
                    })
                    .orElse(badRequest());
        }else return forbidden();
    }

    @Secured
    @BodyParser.Of(TimeBasedProtocol.Parser.class)
    public Result addTimeBasedSet(String id){
        if(shouldCreate(id)){
            return service.find(Long.valueOf(id))
                    .map(exerciseNode -> {
                        exerciseNode.addTimeBased(request().body().as(TimeBasedProtocol.class).toModel());
                        return toJsonResult(service.createOrUpdate(exerciseNode));
                    })
                    .orElse(badRequest());
        }else return forbidden();
    }

    @Secured
    @BodyParser.Of(RepeatBasedProtocol.Parser.class)
    public Result AddRepeatBasedSet(String id){
        if(shouldCreate(id)){
            return service.find(Long.valueOf(id))
                    .map(exerciseNode -> {
                        exerciseNode.addRepeatBased(request().body().as(RepeatBasedProtocol.class).toModel());
                        return toJsonResult(service.createOrUpdate(exerciseNode));
                    })
                    .orElse(badRequest());
        }else return forbidden();
    }

    public boolean shouldCreate(String id){
        return userService.find(sessionService.getId())
                .map(userNode -> userNode.getExercised())
                .map(exerciseds -> exerciseds.stream()
                        .anyMatch(exercised -> exercised.getUserNode().getId().equals(sessionService.getId())
                                && exercised.getExerciseNode().getId().equals(Long.valueOf(id)))).orElse(false);
    }
}
