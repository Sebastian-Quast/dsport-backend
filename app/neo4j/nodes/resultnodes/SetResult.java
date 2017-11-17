package neo4j.nodes.resultnodes;

import neo4j.nodes.ExerciseNode;
import neo4j.nodes.sets.AbstractSet;

public class SetResult {

    private ExerciseNode exerciseNode;
    private AbstractSet set;

    public SetResult(ExerciseNode exerciseNode, AbstractSet set) {
        this.exerciseNode = exerciseNode;
        this.set = set;
    }

    public ExerciseNode getExerciseNode() {
        return exerciseNode;
    }

    public void setExerciseNode(ExerciseNode exerciseNode) {
        this.exerciseNode = exerciseNode;
    }

    public AbstractSet getSet() {
        return set;
    }

    public void setSet(AbstractSet set) {
        this.set = set;
    }
}
