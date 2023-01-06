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

package ua.nanit.limbo.configuration;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SocketAddressSerializer implements TypeSerializer<SocketAddress> {

    @Override
    public SocketAddress deserialize(TypeToken<?> type, ConfigurationNode node) {
        String ip = node.getNode("ip").getString();
        int port = node.getNode("port").getInt();
        SocketAddress address;

        if (ip == null || ip.isEmpty()) {
            address = new InetSocketAddress(port);
        } else {
            address = new InetSocketAddress(ip, port);
        }

        return address;
    }

    @Override
    public void serialize(TypeToken type, @Nullable SocketAddress obj, ConfigurationNode node) {

    }
}
