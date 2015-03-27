package finalproject.com.getfit.findnearby;

/**
 * Created by Gurumukh on 3/26/15.
 */
public class FindNearbyUsers {

    private String nearbyUserName;
    private String nearbyUserProfile;
    private String nearbyUserActive;
    private String nearbyUserDistance;
    private String nearbyUserId;
    public String getNearbyUserName(){
        return nearbyUserName;
    }

    public String getNearbyUserProfile(){
        return nearbyUserProfile;
    }

    public String getNearbyUserActive(){
        return nearbyUserActive;
    }

    public String getNearbyUserDistance() {
        return nearbyUserDistance;
    }

    public String getNearbyUserId(){ return nearbyUserId;}

    public void setNearbyUserName(String nearbyUserName) {
        this.nearbyUserName = nearbyUserName;
    }
    public void setNearbyUserProfile(String nearbyUserProfile)
    {
        this.nearbyUserProfile = nearbyUserProfile;
    }

    public void setNearbyUserActive(String nearbyUserActive) {
        this.nearbyUserActive = nearbyUserActive;
    }

    public void setNearbyUserDistance(String nearbyUserDistance){
        this.nearbyUserDistance = nearbyUserDistance;
    }

    public void setNearbyUserId(String nearbyUserId) {
        this.nearbyUserId = nearbyUserId;
    }

}
