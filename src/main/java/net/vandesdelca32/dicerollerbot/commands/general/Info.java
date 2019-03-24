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

package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

public class Info
        implements Command {
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
        return new String[]{"Info"};
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String helpText() {
        return "Prints out additional bot information.";
    }
}
