package net.vandesdelca32.dicerollerbot.main;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.util.List;

public class Main {

    private static String token = "";
    public static IDiscordClient client;

    public static void main(String[] args){
        // get the bot's token from file.

        List<String> tokenList = FileReader.readFile("token.txt");
        if (tokenList != null && tokenList.size() != 0) {
            token = tokenList.get(0);
        }

        client = Client.createClient(token, true);

        if (client == null) {
            throw new IllegalStateException();
        }

        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new AnnotationListener());
    }
}
