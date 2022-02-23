package Primary;

import Primary.Datatypes.VoiceChannelDT;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * This is going to be the Primary Settings Configuration
 */
public class Settings {
    protected String token = "";
    protected String devToken = "";
    protected String ownerID = "";
    protected String predeterminedprefix = "!";
    protected boolean developmentEnvironment = false;
    protected HashMap<String, VoiceChannelDT> vchm;
    protected MooOptions mooOptions = new MooOptions();
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
    public static boolean saveSettings(Gson gson, Settings config){
        try{
            FileWriter fw = new FileWriter(new File(FILEPATH));
            String towrite = gson.toJson(config, Settings.class);
            fw.write(towrite);
            fw.flush();
            fw.close();
            return true;

        } catch (Exception e){
            System.out.println("Unable to save configuration. Please check file permissions. Skipping...");
            return false;
        }
    }

    /**
     * This class will represent a special person you may designate. This functionality is named after Moo.
     */
    public class MooOptions{
        public String nickname = "";
        public String mooID = "";
        public boolean enableMooMode;
        public String mooGreeting = "";

        public String getMooGreeting() {
            return mooGreeting;
        }

        public void setMooGreeting(String mooGreeting) {
            this.mooGreeting = mooGreeting;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMooID() {
            return mooID;
        }

        public void setMooID(String mooID) {
            this.mooID = mooID;
        }

        public boolean isEnableMooMode() {
            return enableMooMode;
        }

        public void setEnableMooMode(boolean enableMooMode) {
            this.enableMooMode = enableMooMode;
        }
    }


}
