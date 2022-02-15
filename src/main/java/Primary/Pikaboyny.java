package Primary;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

/**
 * NOTE TO SELF: Eventually, there is going to be a need to transition to an asynchronized style of coding this...
 */
public class Pikaboyny{
    protected static CommandHandler handlecmd;
    protected static String token;
    protected static String predeterminedprefix = "!";
    protected static MessageHandler handlemsg;
    //javac ProgramName token prefix
    //The word "none" is used for denoting empty field.
    public static void main(String[] args){

        token = "OTE3NjA5NDczNzY0MzYwMjkz.Ya7Mag.k7KdkqMF9HCrndt8EIPl1nuGCII"; //Defaulted, I need to do something about this
        if (args.length == 4) {
            token = args[2];
            predeterminedprefix = args[3];
        }
        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();
        handlemsg = new MessageHandler();
        handlecmd = new CommandHandler(predeterminedprefix);


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            //System.out.println(message.);
            try{
                handlemsg.handleMessage(message);
            } catch (Exception e){
                System.out.println("Something failed");
            }


        });

        gateway.onDisconnect().block();

    }
}