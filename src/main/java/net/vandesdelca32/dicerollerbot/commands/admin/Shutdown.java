package net.vandesdelca32.dicerollerbot.commands.admin;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

public class Shutdown implements Command {

    @Override
    public String exec(IMessage message) {

        if(!(message.getAuthor() == Main.client.getApplicationOwner())) return "Command can only be run by app owner.";

        MessageBuilder messageBuilder = new MessageBuilder(Main.client);

        // let people know.
        messageBuilder.appendContent("Shutting down...");
        messageBuilder.withChannel(message.getChannel());
        messageBuilder.build();

        // shutdown bot
        System.exit(0);
        return null;
    }

    @Override
    public String[] name() {
        return new String[]{"Shutdown"};
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
