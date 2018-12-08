package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

/**
 * A... not so basic dice roller.
 */
public class Roll
        implements Command {
    @Override
    public String exec(IMessage message) {
        return null;
    }

    @Override
    public String[] names() {
        return new String[0];
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
