package Primary.Datatypes;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.entity.channel.VoiceChannel;

public class ChannelDT {
    public String channeltype;
    public String name;
    public String id;

    public ChannelDT (String channeltype, String name, String id){
        this.channeltype = channeltype;
        this.name = name;
        this.id = id;
    }

    public String getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Return the associated Channel Object using the ID from the ChannelDT object.
     * @param client
     * @return
     */
    public Channel getChannelDTAsEntity(GatewayDiscordClient client){
        return client.getChannelById(Snowflake.of(this.id)).block();

    }

    /**
     * [DEPRECATED] Function to return a Voice Channel using information of this class.
     * @param client
     * @return
     */
    public VoiceChannel getVoiceChannelDTAsEntity(GatewayDiscordClient client){
        //Casting done here but that is because it will be verified Voice Channels
        VoiceChannel cnvch = (VoiceChannel) client.getChannelById(Snowflake.of(this.id)).block();
        assert cnvch != null;
        if (cnvch.getType().equals(Channel.Type.GUILD_VOICE)){
            return cnvch;
        }
        return null;
    }
}
