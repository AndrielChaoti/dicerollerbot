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
 ******************************************************************************/

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


    private Pattern diceFormat = Pattern.compile("^(\\d*)d((?:F)|(?:[1-9]\\d*))(.*)$", Pattern.CASE_INSENSITIVE);
    private String[] fateFaces = {"-1", "-1", "0", "0", "+1", "+1"};


    @Override
    public String exec(String args, IMessage message) {
        // TODO: This can be so much better. This code is cancer.
        Matcher m = diceFormat.matcher(args);
        boolean fateMode = false;

        //Create the randomizer
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        if (!m.matches()) return ">> `" + args + "` is not a valid dice code.";

        // parse a dice code...
        int qty, faces;
        //try {
        logger.debug("qty: {} \tfcs: {}\trest: {}", m.group(1), m.group(2), m.group(3));
        if (m.group(1).isEmpty()) {
            qty = 1;
        } else {
            qty = Integer.parseInt(m.group(1));
        }
        if (m.group(2).equalsIgnoreCase("F")) {
            fateMode = true;
            faces = 6;
        } else {
            faces = Integer.parseInt(m.group(2));
        }
        //} catch (NumberFormatException e) {
        //return "**Error:** Could not parse dice code...";
        //}

        if (qty > 100) return ">> **Error**: Trying to roll too many dice. *Keep the number of dice under 100.*";
        if (faces > 1000) return ">> **Error**: Dice are too big. *Keep the number of faces under 1000.*";

        //does group 3 match another dice code?
        boolean hasMatches;
        if (m.group(3).trim().startsWith("+") || m.group(3).trim().startsWith("-")) {
            String nextm = m.group(3).trim().substring(1);
            Matcher rm = diceFormat.matcher(nextm);
            if (rm.matches()) {
                logger.debug("has multiple matches");
                // handle multiple dice codes... (this has to be recursive down to so many levels)
            }
        }

        String[] output = new String[qty];
        long sum = 0;
        long sum2 = 0;
        for (int i = 0; i < qty; i++) {
            output[i] = rollDie(faces, fateMode);
            sum = sum + Long.parseLong(output[i]);
        }

        // handle math
        long val = 0;
        String oper = "";

        try {
            // Attempt to get number
            if (!m.group(3).isEmpty()) {
                val = Long.parseLong(m.group(3).trim().substring(1).trim());
                switch (m.group(3).trim().charAt(0)) {
                    case '+':
                        sum2 = sum + val;
                        oper = "+";
                        break;
                    case '-':
                        sum2 = sum - val;
                        oper = "-";
                        break;
                    case '*':
                        sum2 = sum * val;
                        oper = "*";
                        break;
                    case '/':
                        sum2 = sum / val;
                        oper = "/";
                        break;
                    default:
                        sum2 = sum; // no math to do here, we only want basic math for now.
                        oper = "";
                        break;
                }
            } else {
                // extra info omitted, no math
                oper = "";
                sum2 = sum;
            }
        } catch (NumberFormatException e) {
            // not a valid number, stop.
            val = 0;
        }

        // build response
        StringBuilder r = new StringBuilder();
        r.append(sum2);
        // append the mathy bit:
        r.append(" = `(");
        for (int i = 0; i < output.length; i++) {
            r.append(output[i]);
            if (i != output.length - 1) {
                r.append(" + ");
            }
        }
        r.append(")");

        // did we do addl math?
        if (!oper.isEmpty()) {
            r.append(" ").append(oper).append(" ").append(val);
        }

        r.append("`");

        // normal chat output
        return String.format(">> Rolling `%s`\n\n**Result:** %s",
                m.group(0), r.toString());
    }

    /**
     * Roll a given `faces`-sided dice.
     *
     * @param faces The number of faces on the die.
     * @param fate  Whether or not you are rolling a Fate die. Output will be changed to show either a -1, 0, or +1 instead of the face number.
     * @return The random number between 1 and `faces` that was chosen.
     */
    private String rollDie(int faces, boolean fate) {
        Random random = new Random();

        int result = random.nextInt(faces);
        result++;

        String output = String.valueOf(result);
        if (fate) {
            output = this.fateFaces[result - 1];
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
