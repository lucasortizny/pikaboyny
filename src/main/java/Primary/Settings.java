package Primary;

/**
 * This is going to be the setup for the future settings export as a Json serializable/deserializable file. More to come.
 * Most likely will use another library like Gson.
 */
public class Settings {
    private String token;
    private String ownerID;


    public String getToken(){
        return this.token;
    }
    public String getOwnerID(){
        return this.ownerID;
    }



}
