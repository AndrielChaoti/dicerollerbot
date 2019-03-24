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

import java.io.*;
import java.util.Properties;

public class BotConfig {

    public Properties properties;
    public static final String FILE_NAME = "bot.properties";

    static final Logger logger = LoggerFactory.getLogger(Utility.class);

    /**
     * Generate and load into memory the bot's configuration information.
     */
    public BotConfig() {
        // initialize default properties.
        final Properties defaults = new Properties();
        try (final InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            defaults.load(in);
            logger.info("Loaded default properties. " + defaults.size() + " settings found.");
        } catch (Exception e) {
            logger.error("Defaults not loaded!", e);
            return;
        }


        // initialize application properties.
        properties = new Properties(defaults);

        // load user properties
        logger.info("Getting user settings...");
        try (final InputStream in = new FileInputStream(FILE_NAME)){
            properties.load(in);
            logger.info("User config loaded!");
        } catch (FileNotFoundException e) {
            saveConfig(defaults);
            logger.warn("User settings not found, defaults loaded.");
        } catch (IOException e) {
            logger.error("User settings not loaded: " + e, e);
        }
    }

    /**
     * Save the current program config
     * @param properties The properties file to save to disk.
     */
    public void saveConfig(Properties properties) {
        try(final FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            properties.store(out, "");
            logger.info("Config saved.");
        } catch (Exception e) {
            logger.error("Settings save failed! " + e, e);
        }
    }

    /**
     * Save the current program config
     */
    public void saveConfig() {
        saveConfig(this.properties);
    }
}
