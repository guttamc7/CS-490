package finalproject.com.getfit;

/**
 * Created by Gurumukh on 2/27/15.
 */
public class BaseWorkout {

    private String baseWorkoutLevel;
    private String baseWorkoutDescription;
    private String baseWorkoutName;

    public String getWorkoutLevel(){
        return baseWorkoutLevel;
    }

    public String getWorkoutDescription(){
        return baseWorkoutDescription;
    }

    public String getWorkoutName(){
        return baseWorkoutName;
    }

    public void setBaseWorkoutLevel(String baseWorkoutLevel) {
        this.baseWorkoutLevel = baseWorkoutLevel;
    }

    public void setBaseWorkoutDescription(String baseWorkoutDescription) {
        this.baseWorkoutDescription = baseWorkoutDescription;
    }

    public void setBaseWorkoutName(String baseWorkoutName){
        this.baseWorkoutName = baseWorkoutName;
    }

}
