/*
 * Copyright (C) 2020 Nan1t
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.nanit.limbo.server;

import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Logger {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss");
    private static int debugLevel = Level.INFO.getIndex();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger("NanoLimbo");

    private Logger() {}

    public static int getLevel() {
        return debugLevel;
    }

    public static void info(String msg, Object... args) {
        print(Level.INFO, msg, null, args);
    }

    public static void debug(String msg, Object... args) {
        print(Level.DEBUG, msg, null, args);
    }

    public static void warning(String msg, Object... args) {
        print(Level.WARNING, msg, null, args);
    }

    public static void warning(String msg, Throwable t, Object... args) {
        print(Level.WARNING, msg, t, args);
    }

    public static void error(String msg, Object... args) {
        print(Level.ERROR, msg, null, args);
    }

    public static void error(String msg, Throwable t, Object... args) {
        print(Level.ERROR, msg, t, args);
    }

    public static void print(Level level, String msg, Throwable t, Object... args) {
        switch (level) {
            case DEBUG:
                logger.debug(String.format(msg, args), t);
                break;
            case INFO:
                logger.info(String.format(msg, args), t);
                break;
            case ERROR:
                logger.error(String.format(msg, args), t);
                break;
            case WARNING:
                logger.info(String.format(msg, args), t);
                break;
        }
    }

    private static String getPrefix(Level level) {
        return String.format("[%s] [%s]", getTime(), level.getDisplay());
    }

    private static String getTime() {
        return LocalTime.now().format(FORMATTER);
    }

    static void setLevel(int level) {
        debugLevel = level;
    }

    public enum Level {

        ERROR("ERROR", 0),
        WARNING("WARNING", 1),
        INFO("INFO", 2),
        DEBUG("DEBUG", 3);

        private final String display;
        private final int index;

        Level(String display, int index) {
            this.display = display;
            this.index = index;
        }

        public String getDisplay() {
            return display;
        }

        public int getIndex() {
            return index;
        }
    }
}
