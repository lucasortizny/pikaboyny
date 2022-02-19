package Primary.Datatypes;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.VoiceChannel;

public class VoiceChannelDT {
    public String internal;
    public Snowflake id;

    public VoiceChannelDT(String internalname, Snowflake id){
        this.internal = internalname;
        this.id = id;

    }

    public String getInternal() {
        return internal;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }

    public VoiceChannel getVoiceChannelDTAsEntity(GatewayDiscordClient client){
        //Casting done here but that is because it will be verified Voice Channels
        VoiceChannel cnvch = (VoiceChannel) client.getChannelById(this.id).block();
        assert cnvch != null;
        if (cnvch.getType().equals(Channel.Type.GUILD_VOICE)){
            return cnvch;
        }
        return null;
    }
}
