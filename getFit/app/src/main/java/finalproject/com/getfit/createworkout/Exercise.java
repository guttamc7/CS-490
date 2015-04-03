package finalproject.com.getfit.createworkout;

/**
 * Created by Gurumukh on 4/3/15.
 */
public class Exercise {
    private String exerciseName;
    private String exerciseSets;
    private String exerciseReps;
    private String exerciseImage1;
    private String exerciseImage2;
    public String getExerciseName(){
        return exerciseName;
    }

    public String getExerciseSets(){
        return exerciseSets;
    }

    public String getExerciseReps(){
        return exerciseReps;
    }

    public String getExerciseImage1(){
        return exerciseImage1;
    }

    public String getExerciseImage2(){
        return exerciseImage2;
    }

    public void setExerciseName(String exerciseName){
        this.exerciseName = exerciseName;
    }

    public void setExerciseSets(String exerciseSets) {
        this.exerciseSets  = exerciseSets;
    }

    public void setExerciseReps(String exerciseReps) {
        this.exerciseReps = exerciseReps;
    }

    public void setExerciseImage1(String exerciseImage1){
        this.exerciseImage1 = exerciseImage1;
    }

    public void setExerciseImage2(String exerciseImage2){
        this.exerciseImage2 = exerciseImage2;
    }
}
