package finalproject.com.getfit.baseworkout;

/**
 * Created by Gurumukh on 2/27/15.
 */
public class BaseWorkout {

    private String baseWorkoutLevel;
    private String baseWorkoutDescription;
    private String baseWorkoutName;
    private String baseWorkoutUrl;
    public String getWorkoutLevel(){
        return baseWorkoutLevel;
    }

    public String getWorkoutDescription(){
        return baseWorkoutDescription;
    }

    public String getWorkoutName(){
        return baseWorkoutName;
    }

    public String getWorkoutUrl() {
        return baseWorkoutUrl;
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

    public void setBaseWorkoutUrl(String baseWorkoutUrl) {
        this.baseWorkoutUrl = baseWorkoutUrl;
    }

}
