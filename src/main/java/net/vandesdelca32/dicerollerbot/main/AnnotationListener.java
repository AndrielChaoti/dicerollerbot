package net.vandesdelca32.dicerollerbot.main;

import net.vandesdelca32.dicerollerbot.commands.admin.Shutdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class AnnotationListener {

    final static Logger logger = LoggerFactory.getLogger(AnnotationListener.class);

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        logger.info("Bot Ready!");
    }

    @EventSubscriber
    public void onMessageRecievedEvent(MessageReceivedEvent event){
        String message = event.getMessage().getContent().toLowerCase();

        // check if message is from bot user
        if(event.getAuthor().isBot()) return;

        if(message.startsWith("!")) {
            // this is a command message, try to call command
            if(message.endsWith("shutdown")){
                Shutdown shutdown = new Shutdown();
                MessageBuilder response = new MessageBuilder(Main.client);
                response.withChannel(event.getChannel());
                response.appendContent(shutdown.exec(event.getMessage()));
                response.build();
            }
        }
    }
}
