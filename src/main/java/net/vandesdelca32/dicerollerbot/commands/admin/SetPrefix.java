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

package net.vandesdelca32.dicerollerbot.commands.admin;

import net.vandesdelca32.dicerollerbot.commands.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

import static net.vandesdelca32.dicerollerbot.main.Main.*;

public class SetPrefix implements Command {

    @Override
    public String exec(String args, IMessage message) {
        if (!(message.getAuthor() == client.getApplicationOwner())) return "Command can only be run by app owner.";

        botConfig.properties.setProperty("chatPrefix", args);
        commandPrefix = args;
        return "Command prefix set to \"" + args + "\"";
    }

    @Override
    public String[] names() {
        return new String[]{"SetPrefix"};
    }

    @Override
    public String usage() {
        return "[new prefix]";
    }

    @Override
    public String helpText() {
        return "Changes the bot's command prefix.\n" +
                "Only the bot owner can use this command.";
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
