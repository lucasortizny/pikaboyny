package Primary.Commands;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;
;import java.util.concurrent.CompletableFuture;

/**
 * This is going to be the shutdown command.
 */
public class CommandShutdown {

    public static boolean execute(Message msg, Primary.CommandHandler cmdhandle){
        if (msg.getAuthor().isPresent()){
            if (cmdhandle.checkBotOwnership(msg.getAuthor().get().getId().asString())){
                //For future async functionality below \/
                //CompletableFuture<Message> something = msg.getChannel().block().createMessage("Roger, roger. Shutting down...").toFuture();
                msg.getChannel().block().createMessage("Roger, roger. Shutting down...").block();
                msg.getClient().logout().block();
            }

        }
        return false;
    }


}
