package Primary.Commands;
import Primary.CommandHandler;
import Primary.Pikaboyny;
import Primary.Settings;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.discordjson.json.PermissionsEditRequest;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is going to be the main class where permissions are updated for a channel.
 */
public class CommandPermUpdate {

    public static void memberAddToTextchannel(Message msg, GatewayDiscordClient client, Settings.MooOptions mooConfig){
        memberAddToTextchannel(msg, client, CommandHandler.mooCheck(msg), mooConfig);
    }
    public static void memberAddToVoicechannel(Message msg, GatewayDiscordClient client, boolean isMoo){

    }

    /**
     * This function takes the current message and applies the designated permissions while denying any channel NOT
     * specified otherwise. This will change the type of responses given based on whether it is authorized as an admin
     * or if it is authorized as guild owner. Also has a different type of response if the person is Moo.
     * @param msg
     * @param client
     * @param isMoo
     */
    public static void memberAddToTextchannel(Message msg, GatewayDiscordClient client, boolean isMoo, Settings.MooOptions mooOptions) {
        MessageChannel curchn = msg.getChannel().block();
        assert curchn != null;
        if (!Primary.CommandHandler.checkGuildOwnership(msg) && !Primary.CommandHandler.checkAdminStatus(msg)){

            curchn.createMessage("You're not allowed to invoke this command.").subscribe();
            msg.getRestMessage().createReaction("\u274e").subscribe();
        }
        else if (isMoo && mooOptions.isEnableMooMode()){
            curchn.createMessage(mooOptions.getMooGreeting().concat(" ").concat(mooOptions.getNickname()) + ", you're not allowed to invoke this command.").subscribe();
            msg.getRestMessage().createReaction("\u274e").subscribe();
        }
        else {
            //Currently reads all user mentions of a message.
            msg.getUserMentions().forEach(user ->{
                ArrayList<Snowflake> arrangedids = processSnowflakeChannels(msg);
                //Gets all channels within a server (the context in which the message was sent in).
                Objects.requireNonNull(msg.getGuild().block()).getData().channels().forEach(channel ->{
                    if (arrangedids.contains(Snowflake.of(channel))){
                        Channel tc = msg.getClient().getChannelById(Snowflake.of(channel)).block();
                        try {
                            if (tc.getType().equals(Channel.Type.GUILD_TEXT)){
                                //Get rest channel, edit permissions setting the user,
                                tc.getRestChannel().editChannelPermissions(user.getId(), PermissionsEditRequest.builder().allow(3148800).deny(0).type(1).build(), String.format("Invoked by %s", msg.getAuthor().get().getUsername())).subscribe();
                            }

                        } catch (IndexOutOfBoundsException e){
                            e.printStackTrace();
                            System.out.println("Continuing execution as if nothing happened...");
                            curchn.createMessage("Perm Update Failed. Check system log...").subscribe();
                        }
                    }
                    //This is the case where the Channels ARE NOT the ones listed.
                    else {
                        Channel chn = msg.getClient().getChannelById(Snowflake.of(channel)).block();
                        assert chn != null;
                        if (chn.getType().equals(Channel.Type.GUILD_TEXT)){
                            chn.getRestChannel().editChannelPermissions(user.getId(), PermissionsEditRequest.builder().deny(3148800).allow(0).type(1).build(), "Invoked by ".concat(msg.getAuthor().get().getUsername())).subscribe();
                        }
                    }
                });
                msg.getRestMessage().createReaction("✅").subscribe();

            });


        }

    }

    /**
     * Process the linked channel snowflakes for this command. Assuming this is done correctly, it will return an
     * ArrayList of channel snowflakes for processing. This function is prone to breaking, please handle this with care.
     * @param msg
     * @return An ArrayList with Channel Snowflakes.
     */

    public static ArrayList<Snowflake> processSnowflakeChannels (Message msg){
        String[] msgargs = msg.getContent().split(" ");
        String[] channelargs =  new String[msgargs.length-2];
        //Come up with a way to optimize this later...
        for (int i = 0; i < msgargs.length; i++){
            if (i == 0 || i == 1){
            }
            else{
                channelargs[i-2] = msgargs[i];
            }
        }

        ArrayList<Snowflake> snowflakelist = new ArrayList<Snowflake>();
        for (String snowflakes : channelargs){
            snowflakelist.add(Snowflake.of(snowflakes.substring(2,snowflakes.length()-1)));
        }
        return snowflakelist;
    }


}
