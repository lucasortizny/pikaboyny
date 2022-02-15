package Primary;

import Primary.Commands.CommandPermUpdate;
import Primary.Commands.CommandShutdown;
import discord4j.core.object.entity.Message;

import java.util.Objects;


public class MessageHandler extends Pikaboyny{

    public void handleMessage(Message msg){
        boolean prefixvalid = false;
        prefixvalid = msg.getContent().substring(0,1).equals(predeterminedprefix);
        String[] messagesplit = msg.getContent().split(" ");
        if (prefixvalid){
            try {
                switch(messagesplit[0].substring(1)){
                    case "shutdown" -> CommandShutdown.execute(msg, Pikaboyny.handlecmd);
                    case "tallow" -> CommandPermUpdate.memberAddToTextchannel(msg, msg.getClient());

                }}catch(Exception e){
                Objects.requireNonNull(msg.getChannel().block()).createMessage("Method invocation failed. Please refer to the console for more information.").block();
                e.printStackTrace();
            }
        }



    }


}
