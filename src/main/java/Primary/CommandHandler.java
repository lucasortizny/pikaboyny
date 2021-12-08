package Primary;

import discord4j.core.object.entity.Message;

import java.util.Objects;

public class CommandHandler extends Pikaboyny{

    public String prefix; //Default
    public String ownerID; //Default

    /**
     * Default Assignment
     */
    public CommandHandler(){
        this.prefix = super.predeterminedprefix;
        this.ownerID = "100903082652602368";

    }


    /**
     * Assignment of default settings with customized prefix.
     * @param prefix
     */
    public CommandHandler (String prefix){
        this.prefix = prefix; //Assign Prefix
        this.ownerID = "100903082652602368"; //Default Owner is Lucas aka Me

    }

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
    public boolean checkBotOwnership(String snowflake){
        return Objects.equals(snowflake, this.ownerID);
    }

    /**
     * Checks to confirm the this is in fact, the owner of the Guild.
     * @param message
     * @return
     */
    public static boolean checkGuildOwnership(Message message){
        try {
            message.getGuild().block().getOwnerId().equals(message.getAuthor().get().getId());
            return true;
        }
        catch (NullPointerException e){
            message.getChannel().block().createMessage("Guild ownership check failed. Check your code.").block();


        }
        return false;
    }


}
