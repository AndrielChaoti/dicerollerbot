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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Arrays;

public class Utility {

    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    /**
     * Takes in an arbitrary string and prepends the backslash (\) character to any markdown formatting.
     *
     * @param input The string to parse for Markdown.
     * @return A string with all markdown sequences escaped.
     */
    public static String escapeMarkdown(String input) {
        input = input.replace("*", "\\*");
        input = input.replace("_", "\\_");
        input = input.replace("`", "\\`");
        input = input.replace("|", "\\|");
        input = input.replace("~", "\\~");
        return input;
    }


    public static RequestBuffer.RequestFuture<IMessage> sendMessage(String message, IChannel channel) {
        return RequestBuffer.request(() -> {
            // sanity checking.
            if (message == null || channel == null) return null;
            // don't send empty messages.
            if (message.isEmpty()) return null;
            if (StringUtils.containsOnly(message, "\n")) return null;
            if (StringUtils.isBlank(message)) return null;
            // don't send messages that are too long.
            if (message.length() > 2000) {
                logger.error("Message is too long: {} is bigger than 2000 characters", message.length());
                return null;
            }
            // send the message
            try {
                return channel.sendMessage(message);
            } catch (RateLimitException e) {
                throw e;
            } catch (MissingPermissionsException e) {
                logger.error("Missing permissions to send message in {}! {}", channel, e.getMissingPermissions());
                return null;
            } catch (DiscordException e) {
                String stackContents = Arrays.toString(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray());
                if (stackContents.contains("sx.blah.discord.handle.impl.obj.PrivateChannel.sendMessage(PrivateChannel.java") &&
                        e.getMessage().contains("Message was unable to be sent")) {
                    logger.error("DM message failed. User DMs disabled or blocked. {}", channel);
                } else {
                    throw e;
                }
                return null;
            }
        });
    }

    public static RequestBuffer.RequestFuture<IMessage> sendChatResponse(String message, IMessage command, boolean mention) {
        StringBuilder sb = new StringBuilder();
        sb.append(">> ");
        if (mention) sb.append(command.getAuthor().mention()).append("\n");
        sb.append(message);
        return sendMessage(sb.toString(), command.getChannel());
    }
}
