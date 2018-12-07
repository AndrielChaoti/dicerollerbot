package net.vandesdelca32.dicerollerbot.main;

import net.vandesdelca32.dicerollerbot.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.util.List;

public class Main {

    static final Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * The IDiscordClient object used by the bot
     */
    public static IDiscordClient client;
    /**
     * Contains all of the commands currently in use by the bot.
     */
    public static List<Command> commands;
    public static String commandPrefix = "!";

    public static void main(String[] args) {
        // Attempt to load the bot token into memory:
        String token = "";
        List<String> tokenList = FileReader.readFile("token.txt");
        if (tokenList != null && tokenList.size() != 0) {
            token = tokenList.get(0);
        }

        // Log in the discord client
        client = Client.createClient(token, true);

        // This doesn't need to exist, but it does anyway, there's no bot running if there's no client
        if (client == null) {
            throw new IllegalStateException();
        }

        // Set up event listener
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new AnnotationListener());
        logger.info("Event Listener Initialized.");
    }
}
