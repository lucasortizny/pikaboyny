package Primary;

import Primary.Datatypes.VoiceChannelDT;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * This is going to be the setup for the future settings export as a Json serializable/deserializable file. More to come.
 * Most likely will use another library like Gson.
 */
public class Settings {
    protected String token = "";
    protected String devToken = "";
    protected String ownerID = "";
    protected String predeterminedprefix = "!";
    protected boolean developmentEnvironment = false;
    protected HashMap<String, VoiceChannelDT> vchm;
    protected static final String FILEPATH = "./settings.json";

    public String getPredeterminedprefix() {
        return predeterminedprefix;
    }
    public void setPredeterminedprefix(String predeterminedprefix) {
        this.predeterminedprefix = predeterminedprefix;
    }
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
    public String getDevToken() {
        return devToken;
    }
    public void setDevToken(String devToken) {
        this.devToken = devToken;
    }
    public boolean isDevelopmentEnvironment() {
        return developmentEnvironment;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setDevelopmentEnvironment(boolean developmentEnvironment) {
        this.developmentEnvironment = developmentEnvironment;
    }
    public String getToken(){
        return this.token;
    }
    public String getOwnerID(){
        return this.ownerID;
    }
    public static Settings importSettings(File file, Gson gson){
        try {
            if (file.exists()){
                return gson.fromJson(new FileReader(file), Settings.class);
            }
            return createSettings(gson);
        } catch (Exception e){
            //File Failed to be Read, redo settings.
            e.printStackTrace();
            System.out.println("File is corrupted/doesn't exist. Attempting to create new Settings...");
            return createSettings(gson);
        }

    }
    public static Settings createSettings(Gson gson){
        File newfile = new File(FILEPATH);
        try {
            if (newfile.exists()) {
                newfile.delete();
                newfile.createNewFile();
            }
            String towrite = gson.toJson(new Settings(), Settings.class);
            FileWriter fw = new FileWriter(FILEPATH);
            fw.write(towrite);
            fw.flush();
            fw.close();


            return gson.fromJson(new FileReader(FILEPATH), Settings.class);
        } catch (Exception e){
                e.printStackTrace();
                System.out.println("Unable to complete Settings configuration. Please check your file permissions. Exiting...");
                System.exit(4);
        }
        return null;
    }

}
