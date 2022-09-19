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

package ru.nanit.limbo.server.data;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;
import ru.nanit.limbo.util.Colors;

public class PingData {

    private String version;
    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Serializer implements TypeSerializer<PingData> {

        @Override
        public PingData deserialize(TypeToken<?> type, ConfigurationNode node) {
            PingData pingData = new PingData();
            pingData.setDescription(Colors.of(node.getNode("description").getString("")));
            pingData.setVersion(Colors.of(node.getNode("version").getString("")));
            return pingData;
        }

        @Override
        public void serialize(TypeToken<?> type, @Nullable PingData obj, ConfigurationNode node) {

        }
    }
}