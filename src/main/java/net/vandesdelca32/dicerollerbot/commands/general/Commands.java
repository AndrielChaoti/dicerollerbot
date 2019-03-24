/*******************************************************************************
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
 ******************************************************************************/

package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

import static net.vandesdelca32.dicerollerbot.main.Main.client;
import static net.vandesdelca32.dicerollerbot.main.Main.commands;

public class Commands
        implements Command {

    // the maximum length for a command name.
    private static final int MAX_CMD_LENGTH = 15;

    @Override
    public String exec(String args, IMessage message) {
        MessageBuilder mb = new MessageBuilder(client);
        EmbedBuilder eb = new EmbedBuilder();

        eb.withTitle("Command List");
        eb.withColor(client.getOurUser().getColorForGuild(message.getGuild()));
        eb.withDesc("__Currently available commands:__\n");
        //eb.appendDesc("```\n");
        for (Command command : commands) {
            eb.appendDesc(">\u00A0**");
            String commandName = command.names()[0];
            eb.appendDesc(commandName);
            eb.appendDesc("**\n");
        }
        //eb.appendDesc("```");
        mb.withEmbed(eb.build());
        mb.withChannel(message.getChannel());
        mb.build();
        return null;
    }

    @Override
    public String[] names() {
        return new String[]{"Commands", "list"};
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String helpText() {
        return "Gets the list of commands.";
    }
}
