package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

public class Info implements Command {
    @Override
    public String exec(String args, IMessage message) {
        EmbedBuilder builder = new EmbedBuilder();
        MessageBuilder o = new MessageBuilder(Main.client);
        builder.withColor(Main.client.getOurUser().getColorForGuild(message.getGuild()));
        builder.withTitle("DiceRollerBot v" +
                (getClass().getPackage().getImplementationVersion() != null ? getClass().getPackage().getImplementationVersion() : "DEV"));
        builder.withThumbnail(Main.client.getApplicationIconURL());
        builder.withDescription("The Dragonfish server management bot written by "
                + Main.client.getApplicationOwner().mention() + ".\n\n" +
                "This bot was written with **Java 8** and **[Discord4j](https://discord4j.com/)**.\n" +
                "The bot's source code is available on [its GitHub page](https://github.com/AndrielChaoti/dicerollerbot).");

        o.withChannel(message.getChannel());
        o.withEmbed(builder.build());
        o.build();
        return null;
    }

    @Override
    public String[] names() {
        return new String[] {"Info"};
    }
}
