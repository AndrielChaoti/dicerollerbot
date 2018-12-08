package net.vandesdelca32.dicerollerbot.commands.general;

import net.vandesdelca32.dicerollerbot.commands.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A... not so basic dice roller.
 */
public class Roll
        implements Command {

    private Pattern diceFormat = Pattern.compile("^(\\d*)d((?:F)|(?:[1-9]\\d*(.*)))$", Pattern.CASE_INSENSITIVE);
    private String[] fate = {"-1", "-1", "0", "0", "+1", "+1"};

    @Override
    public String exec(String args, IMessage message) {
        Matcher m = diceFormat.matcher(args);
        boolean fateMode = false;

        //Create the randomizer
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        if (!m.find()) return "Not a valid dice code...";

        int qty, faces;
        if (m.group(1).isEmpty()) {
            qty = 1;
        } else {
            qty = Integer.parseInt(m.group(1));
        }
        if (m.group(2).equals("f")) {
            fateMode = true;
            faces = 6;
        } else {
            faces = Integer.parseInt(m.group(2));
        }

        if (qty > 100) return "Too many dice!";
        if (faces > 1000) return "Dice are too big!";

        String[] output = new String[qty];
        for (int i = 0; i < qty; i++) {
            output[i] = rollDie(faces, fateMode);
        }

        StringBuilder r = new StringBuilder();
        r.append("[");
        for (String s : output) {
            r.append(s + ", ");
        }
        r.append("]");

        return String.format("dice code: %s\n" +
                        "qty: %s\n" +
                        "faces: %s\n" +
                        "rest: %s\n" +
                        "result! %s\n",
                m.group(0), qty, faces, m.group(3), r.toString());
    }

    /**
     * Roll a given `faces`-sided dice.
     *
     * @param faces The number of faces on the die.
     * @param fate  Whether or not you are rolling a Fate die. Output will be changed to show either a -1, 0, or +1 instead of the face number.
     * @return The random number between 1 and `faces` that was chosen.
     */
    private String rollDie(int faces, boolean fate) {
        String output = "";
        Random random = new Random();

        int result = random.nextInt(faces);
        result++;

        output = String.valueOf(result);
        if (fate) {
            output = this.fate[result - 1];
        }

        return output;
    }

    @Override
    public String[] names() {
        return new String[]{"Roll", "r"};
    }

    @Override
    public Permissions[] requiredPerms() {
        return new Permissions[0];
    }
}
