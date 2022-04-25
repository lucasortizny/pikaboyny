package Primary;

import Primary.Datatypes.ChannelDT;
import com.google.gson.Gson;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;

/**
 * This is going to be the Primary Settings Configuration
 */
public class Settings {
    protected String token = "";
    protected String devToken = "";
    protected String ownerID = "";
    protected String predeterminedprefix = "!";
    protected boolean developmentEnvironment = false;
    protected HashMap<String, HashMap<String, ChannelDT>> vchm = new HashMap<>();
    protected MooOptions mooOptions = new MooOptions();
    protected static final String FILEPATH = "./settings.json";
    protected HashMap<String, String> commandlist = new HashMap<>() {{
        put("shutdown", "Shutdown the bot using this command. Only available to the bot owner defined in settings.json.");
        put("tallow", "Allow a person access to the specified text channels. This command will implicitly deny the permissions" +
                " of channels that have not been specified");
        put("fc", "Register the channels  by obtaining them and writing to the Settings configuration.");
        put("cnf", "Clear and fetch the channels, giving it a nice fresh start in the Settings configuration.");
    }}; //List of commands and their descriptions.

    public boolean fetchChannels (GatewayDiscordClient client, Message msg, Gson gson){
        try {

            String guildID = Objects.requireNonNull(msg.getGuild().block()).getId().asString();
            if (!vchm.containsKey(guildID)){
                vchm.put(guildID, new HashMap<>());
            }
            client.getGuildChannels(Snowflake.of(guildID)).subscribe(guildChannel -> {
                if (vchm.get(guildID).containsKey(guildChannel.getId().asString())){
                    //Skip if the text channels are already existent.
                }
                else{
                    vchm.get(guildID).put(guildChannel.getId().asString(), new ChannelDT(guildChannel.getType().name(), guildChannel.getName(), guildChannel.getId().asString()));

                }
            });
            Settings.saveSettings(gson, this);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public boolean clearAndFetch( Message msg, Gson gson){
        try {
            this.vchm.clear();
            if (this.fetchChannels(msg.getClient(), msg, gson)){
                return true;
            }
            return false;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
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
