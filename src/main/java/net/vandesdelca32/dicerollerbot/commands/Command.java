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
 ******************************************************************************/

package net.vandesdelca32.dicerollerbot.commands;

import net.vandesdelca32.dicerollerbot.commands.admin.Leave;
import net.vandesdelca32.dicerollerbot.commands.admin.Shutdown;
import net.vandesdelca32.dicerollerbot.commands.general.Help;
import net.vandesdelca32.dicerollerbot.commands.general.Info;
import net.vandesdelca32.dicerollerbot.commands.general.Roll;
import net.vandesdelca32.dicerollerbot.commands.tools.GameChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

import java.util.LinkedList;
import java.util.List;

public interface Command {

    Logger logger = LoggerFactory.getLogger(Command.class);

    /**
     * Generate and load all of the commands into memory.
     *
     * @return A @LinkedList of commands that the bot has.
     */
    static List<Command> init() {
        List<Command> commands = new LinkedList<>();

        commands.add(new Shutdown());
        commands.add(new Leave());
        commands.add(new Roll());
        commands.add(new GameChannel());
        commands.add(new Help());
        commands.add(new Info());

        logger.info("Initialized {} commands.", commands.size());
        return commands;
    }

    /**
     * This is the code that the command will execute.
     *
     * @param args
     * @param message The IMessage object that the command is sent in.
     * @return Expects a String return as a message to be sent in response to the command.
     */
    String exec(String args, IMessage message);

    /**
     * This method gives the list of names that a command can use. The first name in the array is what will
     * be shown in the help window
     *
     * @return A list of String variables that are used as command names.
     */
    String[] names();

    /**
     * This method should return a list of permissions that is required to run the command.
     *
     * @return A Permissions array of every permission that should be present before running this command.
     */
    default Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
