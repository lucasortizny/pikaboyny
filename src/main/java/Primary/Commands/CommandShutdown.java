package Primary.Commands;

import Primary.Pikaboyny;
import Primary.Settings;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;
;import java.util.concurrent.CompletableFuture;

/**
 * This is going to be the shutdown command.
 */
public class CommandShutdown {

    public static boolean execute(Message msg, Primary.CommandHandler cmdhandle, Settings configuration){
        if (msg.getAuthor().isPresent()){
            if (cmdhandle.checkBotOwnership(msg.getAuthor().get().getId().asString())){
                msg.getChannel().block().createMessage("Roger, roger. Shutting down...").subscribe();
                msg.getClient().logout().subscribe();
                Settings.saveSettings(Pikaboyny.gson, configuration);

            }

        }
        return false;
    }


}
