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
import net.vandesdelca32.dicerollerbot.main.Main;
import net.vandesdelca32.dicerollerbot.main.Utility;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

public class Restart
        implements Command {

    @Override
    public String exec(String args, IMessage message) {

        if (!(message.getAuthor() == Main.client.getApplicationOwner())) {
            return "Command can only be run by app owner.";
        }

        Utility.sendChatResponse("Restarting...", message, true);

        // shutdown bot
        System.exit(2); // Indicate we want to restart for others.
        return null;
    }

    @Override
    public String[] names() {
        return new String[]{"Restart"};
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String helpText() {
        return "Restarts the bot.\n" +
                "***Please note that this only works when the bot is running as a service or wrapped in a script" +
                " that is listening for exit codes.***\n" +
                "This command shuts the bot down with __exit code 2__\n" +
                "Only the bot owner can use this command.";
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
