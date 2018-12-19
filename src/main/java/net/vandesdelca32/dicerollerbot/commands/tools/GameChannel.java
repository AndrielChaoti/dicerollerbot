package net.vandesdelca32.dicerollerbot.commands.tools;

import net.vandesdelca32.dicerollerbot.commands.Command;
import net.vandesdelca32.dicerollerbot.main.Main;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MessageBuilder;

import java.util.List;

public class GameChannel implements Command {
    @Override
    public String exec(String args, IMessage message) {
        IGuild guild = message.getGuild();
        ICategory category = guild.getCategoryByID(441287810213740583L);
        IRole gm = guild.getRoleByID(447095868030582784L);
        IRole aspGM = guild.getRoleByID(450181556909375508L);

        if (category == null) return ">> **SERIOUS ERROR**. \"Games\" category could not be found!!!";
        if (gm == null) return ">> **SERIOUS ERROR**. \"Game Master\" role not found!!!";
        if (aspGM == null) return ">> **SERIOUS ERROR**. \"Aspiring GM\" role not found!!!";

        String[] channelDetails = args.split("\\n");
        /*
        [0] channel name
        [1] game host mention
        [2] game system
        [3] player count
        [4] fixed/flexible
        [5] additional info...*/

        //Sanity checking

        if (channelDetails.length < 5) return usage();
        if (!channelDetails[0].matches("[a-z\\-0-9]{2,32}")) return usage();
        if (!channelDetails[4].equalsIgnoreCase("fixed") &&
                !channelDetails[4].equalsIgnoreCase("flex") &&
                !channelDetails[4].equalsIgnoreCase("flexible")) {
            return usage();
        }

        List<IUser> hosts = message.getMentions();
        if (hosts == null || hosts.isEmpty()) return usage();

        // generate topic text.
        StringBuilder topic = new StringBuilder();

        topic.append("**Host**: ");
        topic.append(hosts.get(0).mention());
        topic.append("\n");
        topic.append("**Game**: ");
        topic.append(channelDetails[2]);
        topic.append("\n");
        topic.append("**Players**: ");
        topic.append(channelDetails[3]);
        if (channelDetails[4].equalsIgnoreCase("fixed")) topic.append(" *(Fixed)*");


        // we have all of the information we need for the channel, create it.
        IChannel gameChannel = category.createChannel(channelDetails[0]);
        gameChannel.changeTopic(topic.toString());

        MessageBuilder builder = new MessageBuilder(Main.client);
        builder.withChannel(gameChannel);
        builder.appendContent(hosts.get(0).mention());
        if (channelDetails.length == 6) {
            builder.appendContent("\n" + channelDetails[5]);
        }
        IMessage o = builder.build();
        gameChannel.pin(o);

        hosts.get(0).addRole(gm);
        hosts.get(0).removeRole(aspGM);

        return ">> Channel created and user " + hosts.get(0).mention() + " updated successfully.";
    }

    public String usage() {
        return "Invalid Syntax. Expecting\n" +
                "**[Channel Name]**\n" +
                "**[@Host]**\n" +
                "**[Game System]**\n" +
                "**[Player Count]**\n" +
                "**[__Fixed__|__Flex(ible)__]**\n" +
                "**(Additional Information)**";
    }

    @Override
    public String[] names() {
        return new String[]{"GameChannel", "NewChannel"};
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[]{Permissions.MANAGE_CHANNELS};
    }


}
