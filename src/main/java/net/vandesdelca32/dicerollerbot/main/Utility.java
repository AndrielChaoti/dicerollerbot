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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

    static final Logger logger = LoggerFactory.getLogger(Utility.class);

    /**
     * Takes in an arbitrary string and prepends the backslash (\) character to any markdown formatting.
     *
     * @param input The string to parse for Markdown.
     * @return A string with all markdown sequences escaped.
     */
    public static String escapeMarkdown(String input) {
        input = input.replace("*", "\\*");
        input = input.replace("_", "\\_");
        input = input.replace("`", "\\`");
        input = input.replace("|", "\\|");
        input = input.replace("~", "\\~");
        return input;
    }
}
