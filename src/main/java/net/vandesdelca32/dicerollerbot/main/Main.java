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

    public static BotConfig botConfig;

    /**
     * Contains all of the commands currently in use by the bot.
     */
    public static List<Command> commands;
    public static String commandPrefix = "!";

    public static void main(String[] args) {

        // Initialize shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            botConfig.saveConfig();
        }));

        // get and load bot configuration
        logger.info("Loading configuration...");
        botConfig = new BotConfig();
        String token = botConfig.properties.getProperty("token");
        if (token.isEmpty() || token.equals("insert token here")) {
            logger.error("TOKEN NOT FOUND. Please provide a valid Discord token in bot.properties and then restart this bot!");
            System.exit(1);
        }

        commandPrefix = botConfig.properties.getProperty("chatPrefix");

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
