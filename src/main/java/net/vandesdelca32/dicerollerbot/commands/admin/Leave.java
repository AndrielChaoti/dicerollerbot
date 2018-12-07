package net.vandesdelca32.dicerollerbot.commands.admin;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

/**
 * Command used to leave a guild.
 */
public class Leave implements Command {
    @Override
    public String exec(IMessage message) {

        MessageBuilder resp = new MessageBuilder(Main.client);
        resp.appendContent("Goodbye.");
        resp.build();

        message.getGuild().leave();

        return null;
    }

    @Override
    public String[] names() {
        return new String[]{"Leave"};
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[]{Permissions.MANAGE_SERVER};
    }
}
