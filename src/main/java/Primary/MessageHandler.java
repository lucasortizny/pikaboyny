package Primary;

import Primary.Commands.CommandFetch;
import Primary.Commands.CommandPermUpdate;
import Primary.Commands.CommandShutdown;
import discord4j.core.object.entity.Message;

import java.util.Objects;


public class MessageHandler extends Pikaboyny{

    public void handleMessage(Message msg){
        boolean prefixvalid = false;
        prefixvalid = msg.getContent().substring(0,1).equals(Pikaboyny.configuration.getPredeterminedprefix());
        String[] messagesplit = msg.getContent().split(" ");
        if (prefixvalid){
            try {
                switch(messagesplit[0].substring(1)){
                    case "shutdown" -> CommandShutdown.execute(msg, Pikaboyny.handlecmd, configuration);
                    case "tallow" -> CommandPermUpdate.memberAddToTextchannel(msg, msg.getClient(), configuration.mooOptions);
                    case "registerchannels" -> CommandFetch.fetchChannels(configuration, gson, msg);
                    case "cf" -> CommandFetch.clearAndFetch(configuration, gson, msg);

                }}catch(Exception e){
                Objects.requireNonNull(msg.getChannel().block()).createMessage("Method invocation failed. Please refer to the console for more information.").block();
                e.printStackTrace();
            }
        }



    }


}
