package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

/**
 * The obligatory help command, lists existing commands.
 */
public class Help
        implements Command {
    @Override
    public String exec(IMessage message) {
        return "There will be a help command here soon.";
    }

    @Override
    public String[] names() {
        return new String[]{"Help", "Commands"};
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
