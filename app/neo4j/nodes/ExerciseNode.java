package neo4j.nodes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import neo4j.nodes.sets.DistanceBasedSet;
import neo4j.nodes.sets.RepeatBasedSet;
import neo4j.nodes.sets.TimeBasedSet;
import neo4j.relationships.exercise.CompletedDistanceBased;
import neo4j.relationships.exercise.CompletedRepeadBased;
import neo4j.relationships.exercise.CompletedTimeBased;
import neo4j.relationships.exercise.Exercised;
import neo4j.relationships.like.LikeExercise;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "ExerciseNode")
public class ExerciseNode extends AbstractNode {

    @JsonProperty
    private String title;

    @JsonProperty
    private String type;

    @Relationship(type = CompletedDistanceBased.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Set<CompletedDistanceBased> distanceBasedSets;

    @Relationship(type = CompletedRepeadBased.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Set<CompletedRepeadBased> repeatBasedSets;

    @Relationship(type = CompletedTimeBased.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Set<CompletedTimeBased> timeBasedSets;

    @Relationship(type = Exercised.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Exercised exercised;

    @Relationship(type = LikeExercise.TYPE, direction = Relationship.INCOMING)
    @JsonIgnore
    @JsonBackReference
    private Set<LikeExercise> likes;


    public ExerciseNode() {
        this.distanceBasedSets = new HashSet<>();
        this.repeatBasedSets = new HashSet<>();
        this.timeBasedSets = new HashSet<>();
    }

    public ExerciseNode(Long id, String title, String type) {
        this.title = title;
        this.type = type;
        this.setId(id);
    }

    public void addExercised(UserNode userNode) {
        Exercised exercised = new Exercised(userNode, this);
        this.exercised = exercised;
    }

    public void addRepeatBased(RepeatBasedSet repeatBasedSet){
        CompletedRepeadBased completedDistanceBased = new CompletedRepeadBased(this, repeatBasedSet);
        this.repeatBasedSets.add(completedDistanceBased);
    }

    public void addTimeBased(TimeBasedSet timeBasedSet){
        CompletedTimeBased completedTimeBased = new CompletedTimeBased(this, timeBasedSet);
        this.timeBasedSets.add(completedTimeBased);
    }

    public void addDistanceBased(DistanceBasedSet distanceBasedSet){
        CompletedDistanceBased completedDistanceBased = new CompletedDistanceBased(this, distanceBasedSet);
        this.distanceBasedSets.add(completedDistanceBased);
    }

}
