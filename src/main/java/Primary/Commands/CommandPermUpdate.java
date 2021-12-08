package Primary.Commands;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.util.PermissionUtil;
import discord4j.discordjson.Id;
import discord4j.discordjson.json.ChannelModifyRequest;
import discord4j.discordjson.json.OverwriteData;
import discord4j.discordjson.json.PermissionsEditRequest;
import discord4j.discordjson.json.gateway.ChannelPinsUpdate;
import discord4j.discordjson.json.gateway.ChannelUpdate;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import io.netty.channel.ChannelConfig;

import java.util.ArrayList;

/**
 * This is going to be the main class where permissions are updated for a channel.
 */
public class CommandPermUpdate {

    public static boolean memberAddToChannel (Message msg) {
        if (!Primary.CommandHandler.checkGuildOwnership(msg)){
            return false;
        }
        else {

            msg.getUserMentions().forEach(user ->{
                ArrayList<Snowflake> arrangedids = processSnowflakeChannels(msg);
                msg.getGuild().block().getData().channels().forEach(channel ->{
                    if (arrangedids.contains(Snowflake.of(channel))){
                        msg.getClient().getChannelById(Snowflake.of(channel)).block();
                        //ChannelModifyRequest.builder().addAllPermissionOverwrites() <-- FOR OVERWRITING CHANNEL PERMS
                        OverwriteData permsetup =
                        ChannelModifyRequest.builder().permissionOverwrites(new OverwriteData.builder().allow((1 << 10) | (1 << 11)).build());


                    }
                });
            });
            return false;

        }

    }

    /**
     * Process the linked channel snowflakes for this command. Assuming this is done correctly, it will return an
     * ArrayList of channel snowflakes for processing.
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
            snowflakelist.add(Snowflake.of(snowflakes));
        }
        return snowflakelist;
    }


}
