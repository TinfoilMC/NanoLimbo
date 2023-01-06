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
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import ua.nanit.limbo.util.Colors;
import ua.nanit.limbo.server.data.BossBar;
import ua.nanit.limbo.server.data.InfoForwarding;
import ua.nanit.limbo.server.data.PingData;
import ua.nanit.limbo.server.data.Title;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LimboConfig {

    private final Path root;

    private SocketAddress address;
    private int maxPlayers;
    private PingData pingData;

    private String dimensionType;
    private int gameMode;

    private boolean useBrandName;
    private boolean useJoinMessage;
    private boolean useBossBar;
    private boolean useTitle;
    private boolean usePlayerList;
    private boolean useHeaderAndFooter;

    private String brandName;
    private String joinMessage;
    private BossBar bossBar;
    private Title title;

    private String playerListUsername;
    private String playerListHeader;
    private String playerListFooter;

    private InfoForwarding infoForwarding;
    private long readTimeout;
    private int debugLevel;

    private boolean useEpoll;
    private int bossGroupSize;
    private int workerGroupSize;

    public LimboConfig(Path root) {
        this.root = root;
    }

    public void load() throws Exception {
        ConfigurationOptions options = ConfigurationOptions.defaults().withSerializers(getSerializers());
        YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
                .setSource(this::getReader)
                .setDefaultOptions(options)
                .build();

        ConfigurationNode conf = loader.load();

        address = conf.getNode("bind").getValue(TypeToken.of(SocketAddress.class));
        maxPlayers = conf.getNode("maxPlayers").getInt();
        pingData = conf.getNode("ping").getValue(TypeToken.of(PingData.class));
        dimensionType = conf.getNode("dimension").getString("the_end");
        if (dimensionType.equalsIgnoreCase("nether")) {
            dimensionType = "the_nether";
        }
        if (dimensionType.equalsIgnoreCase("end")) {
            dimensionType = "the_end";
        }
        gameMode = conf.getNode("gameMode").getInt();
        useBrandName = conf.getNode("brandName", "enable").getBoolean();
        useJoinMessage = conf.getNode("joinMessage", "enable").getBoolean();
        useBossBar = conf.getNode("bossBar", "enable").getBoolean();
        useTitle = conf.getNode("title", "enable").getBoolean();
        usePlayerList = conf.getNode("playerList", "enable").getBoolean();
        playerListUsername = conf.getNode("playerList", "username").getString();
        useHeaderAndFooter = conf.getNode("headerAndFooter", "enable").getBoolean();

        if (useBrandName)
            brandName = conf.getNode("brandName", "content").getString();

        if (useJoinMessage)
            joinMessage = Colors.of(conf.getNode("joinMessage", "text").getString(""));

        if (useBossBar)
            bossBar = conf.getNode("bossBar").getValue(TypeToken.of(BossBar.class));

        if (useTitle)
            title = conf.getNode("title").getValue(TypeToken.of(Title.class));

        if (useHeaderAndFooter) {
            playerListHeader = Colors.of(conf.getNode("headerAndFooter", "header").getString());
            playerListFooter = Colors.of(conf.getNode("headerAndFooter", "footer").getString());
        }

        infoForwarding = conf.getNode("infoForwarding").getValue(TypeToken.of(InfoForwarding.class));
        readTimeout = conf.getNode("readTimeout").getLong();
        debugLevel = conf.getNode("debugLevel").getInt();

        useEpoll = conf.getNode("netty", "useEpoll").getBoolean(true);
        bossGroupSize = conf.getNode("netty", "threads", "bossGroup").getInt(1);
        workerGroupSize = conf.getNode("netty", "threads", "workerGroup").getInt(4);
    }

    private BufferedReader getReader() throws IOException {
        String name = "settings.yml";
        Path filePath = Paths.get(root.toString(), name);

        if (!Files.exists(filePath)) {
            InputStream stream = getClass().getResourceAsStream( "/" + name);

            if (stream == null)
                throw new FileNotFoundException("Cannot find settings resource file");

            Files.copy(stream, filePath);
        }

        return Files.newBufferedReader(filePath);
    }

    private TypeSerializerCollection getSerializers() {
        return TypeSerializerCollection.create()
                .register(TypeToken.of(SocketAddress.class), new SocketAddressSerializer())
                .register(TypeToken.of(InfoForwarding.class), new InfoForwarding.Serializer())
                .register(TypeToken.of(PingData.class), new PingData.Serializer())
                .register(TypeToken.of(BossBar.class), new BossBar.Serializer())
                .register(TypeToken.of(Title.class), new Title.Serializer());
    }

    public SocketAddress getAddress() {
        return address;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public PingData getPingData() {
        return pingData;
    }

    public String getDimensionType() {
        return dimensionType;
    }

    public int getGameMode() {
        return gameMode;
    }

    public InfoForwarding getInfoForwarding() {
        return infoForwarding;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public boolean isUseBrandName() {
        return useBrandName;
    }

    public boolean isUseJoinMessage() {
        return useJoinMessage;
    }

    public boolean isUseBossBar() {
        return useBossBar;
    }

    public boolean isUseTitle() {
        return useTitle;
    }

    public boolean isUsePlayerList() {
        return usePlayerList;
    }

    public boolean isUseHeaderAndFooter() {
        return useHeaderAndFooter;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public Title getTitle() {
        return title;
    }

    public String getPlayerListUsername() {
        return playerListUsername;
    }

    public String getPlayerListHeader() {
        return playerListHeader;
    }

    public String getPlayerListFooter() {
        return playerListFooter;
    }

    public boolean isUseEpoll() {
        return useEpoll;
    }

    public int getBossGroupSize() {
        return bossGroupSize;
    }

    public int getWorkerGroupSize() {
        return workerGroupSize;
    }
}
