/*
 * Copyright (c) 2019 Donald "AndrielChaoti" Granger.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.vandesdelca32.dicerollerbot.main;

import net.vandesdelca32.dicerollerbot.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.StatusType;
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
        Main.client.changePresence(StatusType.ONLINE, ActivityType.WATCHING, "the dice tumble.");
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
        String message = event.getMessage().getContent();

        // ignore message if not prefixed or from an actual user.
        if (event.getAuthor().isBot()) return;
        if (!message.startsWith(Main.commandPrefix)) return;

        // ignore the message if sent in a channel we do not have SEND_MESSAGES permissions for.
        if (!PermissionUtils.hasPermissions(event.getChannel(), Main.client.getOurUser(), Permissions.SEND_MESSAGES)) {
            return;
        }

        // trim the message to the first word only for comparison
        String[] cmd = message.split("\\s+", 2);
        String args = "";
        if (cmd.length > 1 && cmd[1] != null) args = cmd[1];

        StringBuilder response = new StringBuilder();
        //MessageBuilder resp = new MessageBuilder(event.getClient());
        for (Command command : Main.commands) {
            for (String name : command.names()) {
                if (cmd[0].equalsIgnoreCase(Main.commandPrefix + name.toLowerCase())) {
                    if (PermissionUtils.hasPermissions(event.getGuild(), event.getAuthor(), command.requiredPerms())) {
                        try {
                            logger.info("Command {} called.", cmd[0]);
                            String result = command.exec(args, event.getMessage());
                            if (result != null && !result.isEmpty()) {
                                response.append(result);
                            }
                        } catch (Exception e) {
                            response.append("Oops, something went horribly wrong while executing your command:");
                            response.append("```").append(e.toString()).append("```");
                            logger.error("Unhandled exception: ", e);
                        }
                    } else {
                        response.append("You don't have permission to do that.");
                    }
                    if (!response.toString().isEmpty()) {
                        Utility.sendChatResponse(response.toString(), event.getMessage(), true);
                    }
                    return;
                }
            }
        }
    }
}
