package net.vandesdelca32.dicerollerbot.main;

import net.vandesdelca32.dicerollerbot.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.PermissionUtils;

public class AnnotationListener {

    private final static Logger logger = LoggerFactory.getLogger(AnnotationListener.class);

    /**
     * Fired when the bot is fully loaded.
     *
     * @param event ReadyEvent data.
     */
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        Main.commands = Command.init();

        logger.info("Bot Ready!");
    }

    /**
     * Handler for MessageReceivedEvent.
     * <p>
     * Does simple Command parsing.
     *
     * @param event the MessageReceivedEvent data.
     */
    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        String message = event.getMessage().getContent().toLowerCase();

        // ignore message if not prefixed or from an actual user.
        if (event.getAuthor().isBot()) return;
        if (!message.startsWith(Main.commandPrefix)) return;


        // trim the message to the first word only for comparison
        String[] cmd = message.split("\\s+", 2);
        String args = "";
        if (cmd.length > 1 && cmd[1] != null) args = cmd[1];
        logger.info("Command {} called by {}", cmd[0], event.getAuthor().mention());

        for (Command command : Main.commands) {
            for (String name : command.names()) {
                if (cmd[0].equals(Main.commandPrefix + name.toLowerCase())) {
                    MessageBuilder resp = new MessageBuilder(event.getClient());
                    if (PermissionUtils.hasPermissions(event.getGuild(), event.getAuthor(), command.requiredPerms())) {
                        try {
                            resp.appendContent(command.exec(args, event.getMessage()));
                        } catch (Exception e) {
                            resp.appendContent("***Oops!*** Something broke and the command couldn't be completed.\n");
                            resp.appendContent(e.toString(), MessageBuilder.Styles.CODE);
                        }
                    } else {
                        resp.appendContent("You don't have the permission to do that, " + event.getAuthor().mention());
                    }
                    resp.withChannel(event.getChannel());
                    resp.build();
                    return;
                }
            }
        }
    }
}
