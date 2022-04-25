package Primary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.io.File;

/**
 * NOTE TO SELF: Eventually, there is going to be a need to transition to an asynchronized style of coding this...
 */
public class Pikaboyny{
    protected static CommandHandler handlecmd;
    protected static MessageHandler handlemsg;
    protected static Settings configuration;
    public static Gson gson;
    public static void main(String[] args){

        DiscordClient client;
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setPrettyPrinting();
        gson = gsonbuilder.create();
        configuration = Settings.importSettings(new File(Settings.FILEPATH), gson);
        //This is not going to produce NullPointerException because of System.exit() call.
        if(configuration.isDevelopmentEnvironment()){
            client = DiscordClient.create(configuration.getDevToken());
        } else {
            client = DiscordClient.create(configuration.getToken());
        }

        GatewayDiscordClient gateway = client.login().block();
        handlemsg = new MessageHandler();
        handlecmd = new CommandHandler(configuration.getPredeterminedprefix(), configuration.getOwnerID());


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            try{
                handlemsg.handleMessage(message);
            } catch (Exception e){
                System.out.println("Something failed");
            }


        });

        gateway.onDisconnect().block();

    }
}