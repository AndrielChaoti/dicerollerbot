package net.vandesdelca32.dicerollerbot.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

public interface Command {
    String exec(IMessage message);
    String[] name();
    Permissions[] requiredPerms();
}
