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

package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.PermissionUtils;

import static net.vandesdelca32.dicerollerbot.main.Main.client;

/**
 * The obligatory help command, lists existing commands.
 */
public class Help
        implements Command {
    @Override
    public String exec(String args, IMessage message) {

        if (args.isEmpty()) args = "help";

        // attempt to find matching command name:
        Command command = null;
        boolean found = false;
        for (Command c : Main.commands) {
            for (String n : c.names()) {
                if (n.equalsIgnoreCase(args)) {
                    command = c;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        if (!found) return "❗**Error:** Command \"" + args + "\" not found.";

        // output help on match
        MessageBuilder mb = new MessageBuilder(client);
        EmbedBuilder eb = new EmbedBuilder();

        eb.withColor(client.getOurUser().getColorForGuild(message.getGuild()));
        eb.withTitle("Help for **" + command.names()[0] + "**");
        //helpText
        eb.appendField("Description", command.helpText(), false);
        //usage

        StringBuilder usage = new StringBuilder();
        usage.append("```").append(Main.commandPrefix).append(command.names()[0]);
        if (command.usage() != null) {
            usage.append(" ").append(command.usage());
        }
        usage.append("```");

        eb.appendField("Usage", usage.toString(), false);
        //aliases
        if (command.names().length > 1) {
            StringBuilder aliases = new StringBuilder();
            for (int i = 1; i < command.names().length; i++) {
                aliases.append("`").append(Main.commandPrefix).append(command.names()[i]).append("`");
                if (i != command.names().length - 1) {
                    aliases.append(", ");
                }
            }
            eb.appendField("Aliases", aliases.toString(), false);
        }

        //permissions
        if (command.requiredPerms().length != 0) {
            StringBuilder perms = new StringBuilder();
            perms.append("```");
            for (int i = 0; i < command.requiredPerms().length; i++) {
                // check if the user has the perm?
                if (PermissionUtils.hasPermissions(message.getGuild(), message.getAuthor(), command.requiredPerms()[i])) {
                    perms.append("✔");
                } else {
                    perms.append("❌");
                }
                perms.append(command.requiredPerms()[i].toString());

                if (i != command.requiredPerms().length - 1) {
                    perms.append(", ");
                }
            }
            perms.append("```");
            eb.appendField("Required Permissions", perms.toString(), false);
        }

        mb.withChannel(message.getChannel());
        mb.withEmbed(eb.build());
        mb.build();
        return null;
    }

    @Override
    public String[] names() {
        return new String[]{"Help", "?"};
    }

    @Override
    public String usage() {
        return "(command)";
    }

    @Override
    public String helpText() {
        return "Shows more detailed help about a command.\n" +
                "A list of commands can be shown by running `" + new Commands().showUsage() + "`.\n\n" +
                "Arguments written in square brackets `[arg]` are required.\n" +
                "Arguments written in parentheses `(arg)` are optional.\n" +
                "Arguments written in quotation marks `\"arg\"` are literals, put what is written inside the quotes here.";
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
