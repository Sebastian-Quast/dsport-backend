package controllers;

import neo4j.nodes.ExerciseNode;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.nodes.sets.AbstractSet;
import neo4j.relationships.exercise.OfType;
import neo4j.services.ExerciseService;
import neo4j.services.ExerciseUnitService;
import neo4j.services.UserService;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.ExerciseProtocol;
import protocols.ExerciseUnitProtocol;
import sercurity.Secured;

import javax.inject.Inject;

public class ExerciseController extends AbstractController {

    private UserService userService;
    private ExerciseUnitService service;
    private ExerciseService exerciseService;

    @Inject
    public ExerciseController(ExerciseUnitService service, ExerciseService exerciseService, UserService userService) {
        this.service = service;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }


    @Secured
    @BodyParser.Of(ExerciseUnitProtocol.Parser.class)
    public Result createExerciseUnit(String exerciseId) {

        ExerciseUnitProtocol exerciseUnitProtocol = request().body().as(ExerciseUnitProtocol.class);

        return toJsonResult(exerciseService.find(Long.valueOf(exerciseId))
                .flatMap(exerciseNode -> {
                    ExerciseUnitNode exerciseUnitNode = exerciseUnitProtocol.toModel();
                    exerciseUnitNode.setOfType(new OfType(exerciseUnitNode, exerciseNode));
                    return service.createOrUpdate(exerciseUnitNode);
                })
                .flatMap(exerciseUnitNode -> userService.find(sessionService.getId()).map(userNode -> F.Tuple(exerciseUnitNode, userNode)))
                .map(eu -> {
                    System.out.println(eu._1.getId());
                    System.out.println(eu._2.getId());
                    eu._2.addPerformed(eu._1);
                    return userService.createOrUpdate(eu._2).map(u -> eu._1);
                }));
    }

    @Secured
    @BodyParser.Of(ExerciseProtocol.Parser.class)
    public Result createExercise(){
        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(userNode -> {
                    ExerciseNode exerciseNode = request().body().as(ExerciseProtocol.class).toModel();
                    userNode.addExercise(exerciseNode);
                    return userService.createOrUpdate(userNode).map(userNode1 -> exerciseNode);
                }));
    }

    @Secured
    public Result addSet(String id) {

        if (shouldCreate(id)) {
            return service.find(Long.valueOf(id))
                    .map(exerciseNode -> {
                        AbstractSet set = (AbstractSet) Json.fromJson(request().body().asJson(), exerciseNode.getSetType());
                        if (!set.checkCompleteness()) return badRequest();
                        exerciseNode.addSet(set);
                        //exerciseNode.addSet(request().body().as(DistanceBasedProtocol.class).toModel());
                        return toJsonResult(service.createOrUpdate(exerciseNode));
                    })
                    .orElse(badRequest());
        } else return forbidden();
    }


    @Secured
    public Result getSets(String id) {
        return service.find(Long.valueOf(id)).map(exerciseNode -> toJsonResult(service.getSets(exerciseNode.getSetType(), exerciseNode))).orElse(badRequest());
    }


    public boolean shouldCreate(String id) {
        return userService.find(sessionService.getId())
                .map(userNode -> userNode.getPerformed())
                .map(exerciseds -> exerciseds.stream()
                        .anyMatch(exercised -> exercised.getUserNode().getId().equals(sessionService.getId())
                                && exercised.getExerciseUnitNode().getId().equals(Long.valueOf(id)))).orElse(false);
    }
}
