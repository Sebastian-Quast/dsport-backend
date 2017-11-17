package controllers;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import neo4j.nodes.ExerciseNode;
import neo4j.nodes.ExerciseUnitNode;
import neo4j.nodes.UserNode;
import neo4j.nodes.resultnodes.SetResult;
import neo4j.nodes.sets.AbstractSet;
import neo4j.relationships.exercise.OfType;
import neo4j.relationships.exercise.Owns;
import neo4j.relationships.exercise.Performed;
import neo4j.services.ExerciseService;
import neo4j.services.ExerciseUnitService;
import neo4j.services.UserService;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Result;
import protocols.ExerciseProtocol;
import protocols.ExerciseUnitProtocol;
import sercurity.Secured;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
                    eu._2.addPerformed(eu._1);
                    return userService.createOrUpdate(eu._2).map(u -> eu._1);
                }));
    }


    @Secured
    @BodyParser.Of(ExerciseProtocol.Parser.class)
    public Result createExercise() {

        return toOptionalJsonResult(userService.find(sessionService.getId())
                .flatMap(userNode -> {
                    ExerciseNode exerciseNode = request().body().as(ExerciseProtocol.class).toModel();
                    userNode.addExercise(exerciseNode);
                    return userService.createOrUpdate(userNode).map(userNode1 -> exerciseNode);
                }));
    }



    @Secured
    public Result addSet(String id) {
              return service.find(Long.valueOf(id))
                      .map(exerciseNode -> {
                          Type listType = $Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, exerciseNode.getSetType());
                          ArrayList<AbstractSet> set = new Gson().fromJson(request().body().asJson().toString(), listType);
                          for (AbstractSet s : set) {
                              if (s.checkCompleteness())
                                  exerciseNode.addSet(s);
                          }
                          return toJsonResult(service.createOrUpdate(exerciseNode));
                      })
                      .orElse(badRequest());

    }

    @Secured
    public Result deleteSet(String id) {
        try {
            service.delete(Long.valueOf(id));
            return ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badRequest();
    }

    @Secured
    public Result getExercises(String id) {
        return toOptionalJsonResult(userService.find(Long.valueOf(id)).map(userNode -> userNode.getOwns().stream().map(Owns::getExerciseNode)));
    }

    @Secured
    public Result getExerciseUnits(String id) {
        return toOptionalJsonResult(userService.find(Long.valueOf(id),2).map(userNode -> userNode.getPerformed().stream().map(Performed::getExerciseUnitNode).sorted()));
    }

    @Secured
    public Result getSets(String id) {
       return toOptionalJsonResult(service.find(Long.valueOf(id)).map(exerciseNode -> exerciseNode.getWith().stream().map(with -> new SetResult(exerciseNode.getOfType().getExerciseNode(),with.getAbstractSet()))));
    }


    public boolean shouldCreate(String id) {
        return userService.find(sessionService.getId())
                .map(UserNode::getPerformed)
                .map(exerciseds -> exerciseds.stream()
                        .anyMatch(exercised -> exercised.getUserNode().getId().equals(sessionService.getId())
                                && exercised.getExerciseUnitNode().getId().equals(Long.valueOf(id)))).orElse(false);
    }
}
