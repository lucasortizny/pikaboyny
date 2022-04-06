package Primary;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.rest.util.Permission;

import java.security.Permissions;
import java.util.Objects;

public class CommandHandler extends Pikaboyny{

    public String prefix; //Default
    public String ownerID; //Default

    /**
     * Default Assignment
     */
    public CommandHandler(){
        this.prefix = Pikaboyny.configuration.getPredeterminedprefix();
        this.ownerID = Pikaboyny.configuration.getOwnerID();

    }
    public CommandHandler(Settings configuration){
        this.prefix = configuration.getPredeterminedprefix();
        this.ownerID = configuration.getOwnerID();
    }

    /**
     * Assignment of default settings with customized prefix.
     * @param prefix
     */


    /**
     * Custom Assignment of Owner and Subsequent Prefixes
     * @param prefix
     * @param ownerID
     */
    public CommandHandler (String prefix, String ownerID){
        this.prefix = prefix;
        this.ownerID = ownerID;
    }

    /**
     * Modify the prefix adjustment for Primary.Commands executed.
     * @param prefixadj The adjusted prefix.
     * @return A boolean that shows the success of executing the function.
     */
    public boolean modifyPrefix(String prefixadj){
        try {
            this.prefix = prefixadj;

            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Takes the Snowflake of the invoked user and returns if it is owner of the bot.
     * @param snowflake
     * @return
     */
    public static boolean checkBotOwnership(String snowflake){
        return Objects.equals(snowflake, configuration.ownerID);
    }

    /**
     * Checks to confirm that this is in fact, the owner of the Guild.
     * @param message
     * @return
     */
    public static boolean checkGuildOwnership(Message message){
        try {
            return (Objects.requireNonNull(message.getGuild().block()).getOwnerId().equals(message.getAuthor().get().getId()));
        }
        catch (NullPointerException e){
            Objects.requireNonNull(message.getChannel().block()).createMessage("Guild ownership check failed. Check your code.").subscribe();


        }
        return false;
    }
    public static boolean giveCheck (Message msg){
        try {
            msg.getRestMessage().createReaction("✅").subscribe();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean giveEx (Message msg){
        try {
            msg.getRestMessage().createReaction("\u274e").subscribe();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean revokeInvokation(Message msg){
        try{
            Objects.requireNonNull(msg.getChannel().block()).createMessage("You're not allowed to invoke this command").subscribe();
            giveEx(msg);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkAdminStatus(Message message){
        try {
            return ((Objects.requireNonNull(message.getAuthorAsMember().block().getRoles().blockFirst()).getPermissions().contains(Permission.ADMINISTRATOR)));
        } catch (NullPointerException e){
            return false;
        }
        //Catch All for any undesired intentions.
        catch (Exception e){
            e.printStackTrace();
            Objects.requireNonNull(message.getChannel().block()).createMessage("Guild admin check failed. Check your code.").subscribe();

        }

        return false;
    }

    /**
     * Checks to confirm that this is in fact, Mooismyusername (custom command for moousey).
     * @param msg
     * @return
     */
    public static boolean mooCheck (Message msg){
        try {
            return (msg.getAuthor().get().getId().equals(Snowflake.of("185756266621173760")));
        }catch (Exception e){
            Objects.requireNonNull(msg.getChannel().block()).createMessage("Moousey check failed. Check your code.").block();
        }
        return false;
    }

}
