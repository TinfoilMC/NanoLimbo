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

package ua.nanit.limbo;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import ua.nanit.limbo.server.LimboServer;
import ua.nanit.limbo.server.Logger;

@Plugin(id = "nanolimbo", authors = {"TinfoilMC", "Nan1t"})
public class NanoLimbo {
    LimboServer limboServer = new LimboServer();

    @Subscribe
    public void onStart(ProxyInitializeEvent e) {
        try {
            limboServer.start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Subscribe
    public void onStop(ProxyShutdownEvent e) {
        limboServer.stop();
    }

    public static void main(String[] args) {
        try {
            new LimboServer().start();
        } catch (Exception e) {
            Logger.error("Cannot start server: ", e);
        }
    }

}
