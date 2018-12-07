package net.vandesdelca32.dicerollerbot.commands;

import net.vandesdelca32.dicerollerbot.commands.admin.Shutdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

import java.util.LinkedList;
import java.util.List;

public interface Command {

    final static Logger logger = LoggerFactory.getLogger(Command.class);

    /**
     * Generate and load all of the commands into memory.
     *
     * @return A @LinkedList of commands that the bot has.
     */
    static List<Command> init() {
        List<Command> commands = new LinkedList<>();

        commands.add(new Shutdown());

        logger.info("Initialized {} commands.", commands.size());
        return commands;
    }

    /**
     * This is the code that the command will execute.
     *
     * @param message The IMessage object that the command is sent in.
     * @return Expects a String return as a message to be sent in response to the command.
     */
    String exec(IMessage message);

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
    Permissions[] requiredPerms();
}
