package Primary.Commands;

import Primary.CommandHandler;
import Primary.Settings;
import com.google.gson.Gson;
import discord4j.core.object.entity.Message;

import java.util.Objects;

/**
 * This class is going to represent the wrapper for fetching channels and categories and listing their ID's/information.
 */
public class CommandFetch {
    public static boolean fetchChannels(Settings configuration, Gson gson, Message msg){
        try {
            if (CommandHandler.checkAdminStatus(msg)){
                configuration.fetchChannels(msg.getClient(), msg, gson);
                CommandHandler.giveCheck(msg);
                return true;
            } else if (CommandHandler.checkBotOwnership(msg.getAuthor().get().getId().asString())){
                configuration.fetchChannels(msg.getClient(), msg, gson);
                CommandHandler.giveCheck(msg);
                return true;
            } else {
                CommandHandler.revokeInvokation(msg);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearAndFetch(Settings configuration, Gson gson, Message msg){
        try {
            if (CommandHandler.checkAdminStatus(msg)){
                return configuration.clearAndFetch(msg, gson);
            }
            else if (CommandHandler.checkBotOwnership(msg.getAuthor().get().getId().asString())){
                return configuration.clearAndFetch(msg, gson);
            }
            else {
                CommandHandler.revokeInvokation(msg);
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
