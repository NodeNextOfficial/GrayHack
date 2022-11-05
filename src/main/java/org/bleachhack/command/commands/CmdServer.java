/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import net.minecraft.SharedConstants;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringUtils;
import org.grayhack.GrayHack;
import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.event.events.EventPacket;
import org.grayhack.eventbus.GraySubscribe;
import org.grayhack.util.GrayLogger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CmdServer extends Command {

	public CmdServer() {
		super("server", "Server things.", "server address | server brand | server day | server difficulty | server ip | server motd | server ping | server permissions | server plugins | server protocol | server version", CommandCategory.MISC);
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		boolean sp = mc.isIntegratedServerRunning();

		if (!sp && mc.getCurrentServerEntry() == null) {
			GrayLogger.error("Unable to get server info.");
			return;
		}

		GrayLogger.info("Server Info");

		if (args.length == 0) {
			GrayLogger.noPrefix(createText("Address", getAddress(sp)));
			GrayLogger.noPrefix(createText("Brand", getBrand(sp)));
			GrayLogger.noPrefix(createText("Day", getDay(sp)));
			GrayLogger.noPrefix(createText("Difficulty", getDifficulty(sp)));
			GrayLogger.noPrefix(createText("IP", getIP(sp)));
			GrayLogger.noPrefix(createText("Motd", getMotd(sp)));
			GrayLogger.noPrefix(createText("Ping", getPing(sp)));
			GrayLogger.noPrefix(createText("Permission Level", getPerms(sp)));
			GrayLogger.noPrefix(createText("Protocol", getProtocol(sp)));
			GrayLogger.noPrefix(createText("Version", getVersion(sp)));
			checkForPlugins();
		} else if (args[0].equalsIgnoreCase("address")) {
			GrayLogger.noPrefix(createText("Address", getAddress(sp)));
		} else if (args[0].equalsIgnoreCase("brand")) {
			GrayLogger.noPrefix(createText("Brand", getBrand(sp)));
		} else if (args[0].equalsIgnoreCase("day")) {
			GrayLogger.noPrefix(createText("Day", getDay(sp)));
		} else if (args[0].equalsIgnoreCase("difficulty")) {
			GrayLogger.noPrefix(createText("Difficulty", getDifficulty(sp)));
		} else if (args[0].equalsIgnoreCase("ip")) {
			GrayLogger.noPrefix(createText("IP", getIP(sp)));
		} else if (args[0].equalsIgnoreCase("motd")) {
			GrayLogger.noPrefix(createText("Motd", getMotd(sp)));
		} else if (args[0].equalsIgnoreCase("ping")) {
			GrayLogger.noPrefix(createText("Ping", getPing(sp)));
		} else if (args[0].equalsIgnoreCase("permissions")) {
			GrayLogger.noPrefix(createText("Permission Level", getPerms(sp)));
		} else if (args[0].equalsIgnoreCase("plugins")) {
			checkForPlugins();
		} else if (args[0].equalsIgnoreCase("protocol")) {
			GrayLogger.noPrefix(createText("Protocol", getProtocol(sp)));
		} else if (args[0].equalsIgnoreCase("version")) {
			GrayLogger.noPrefix(createText("Version", getVersion(sp)));
		} else {
			throw new CmdSyntaxException("Invalid server bruh.");
		}
	}

	@GraySubscribe
	public void onReadPacket(EventPacket.Read event) {
		if (event.getPacket() instanceof CommandSuggestionsS2CPacket) {
			GrayHack.eventBus.unsubscribe(this);

			CommandSuggestionsS2CPacket packet = (CommandSuggestionsS2CPacket) event.getPacket();
			List<String> plugins = packet.getSuggestions().getList().stream()
					.map(s -> {
						String[] split = s.getText().split(":");
						return split.length != 1 ? split[0].replace("/", "") : null;
					})
					.filter(Objects::nonNull)
					.distinct()
					.sorted()
					.collect(Collectors.toList());

			if (!plugins.isEmpty()) {
				GrayLogger.noPrefix(createText("Plugins \u00a7f(" + plugins.size() + ")", "\u00a7a" + String.join("\u00a7f, \u00a7a", plugins)));
			} else {
				GrayLogger.noPrefix("\u00a7cNo plugins found");
			}
		}
	}

	public Text createText(String name, String value) {
		boolean newlines = value.contains("\n");
		return Text.literal("\u00a77" + name + "\u00a7f:" + (newlines ? "\n" : " " ) + "\u00a7a" + value).styled(style -> style
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to copy to clipboard")))
				.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, Formatting.strip(value))));
	}

	public void checkForPlugins() {
		GrayHack.eventBus.subscribe(this); // Plugins
		mc.player.networkHandler.sendPacket(new RequestCommandCompletionsC2SPacket(0, "/"));

		Thread timeoutThread = new Thread(() -> {
			try {
				Thread.sleep(5000);
				if (GrayHack.eventBus.unsubscribe(this))
					GrayLogger.noPrefix("\u00a7cPlugin check timed out");
			} catch (InterruptedException ignored) {
			}
		});
		timeoutThread.setDaemon(true);
		timeoutThread.start();
	}

	public String getAddress(boolean singleplayer) {
		if (singleplayer)
			return "Singleplayer";

		return mc.getCurrentServerEntry().address != null ? mc.getCurrentServerEntry().address : "Unknown";
	}

	public String getBrand(boolean singleplayer) {
		if (singleplayer)
			return "Integrated Server";

		return mc.player.getServerBrand() != null ? mc.player.getServerBrand() : "Unknown";
	}

	public String getDay(boolean singleplayer) {
		return "Day " + (mc.world.getTimeOfDay() / 24000L);
	}

	public String getDifficulty(boolean singleplayer) {
		return StringUtils.capitalize(mc.world.getDifficulty().getName()) + " (Local: " + mc.world.getLocalDifficulty(mc.player.getBlockPos()).getLocalDifficulty() + ")";
	}

	public String getIP(boolean singleplayer) {
		try {
			if (singleplayer)
				return InetAddress.getLocalHost().getHostAddress();

			return mc.getCurrentServerEntry().address != null ? InetAddress.getByName(mc.getCurrentServerEntry().address).getHostAddress() : "Unknown";
		} catch (UnknownHostException e) {
			return "Unknown";
		}
	}

	public String getMotd(boolean singleplayer) {
		if (singleplayer)
			return "-";

		return mc.getCurrentServerEntry().label != null ? mc.getCurrentServerEntry().label.getString() : "Unknown";
	}

	public String getPing(boolean singleplayer) {
		PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
		return playerEntry == null ? "0" : Integer.toString(playerEntry.getLatency());
	}

	public String getPerms(boolean singleplayer) {
		int p = 0;
		while (mc.player.hasPermissionLevel(p + 1) && p < 5) p++;

		return switch (p) {
			case 0 -> "0 (No Perms)";
			case 1 -> "1 (No Perms)";
			case 2 -> "2 (Player Command Access)";
			case 3 -> "3 (Server Command Access)";
			case 4 -> "4 (Operator)";
			default -> p + " (Unknown)";
		};
	}

	public String getProtocol(boolean singleplayer) {
		if (singleplayer)
			return Integer.toString(SharedConstants.getProtocolVersion());

		return Integer.toString(mc.getCurrentServerEntry().protocolVersion);
	}

	public String getVersion(boolean singleplayer) {
		if (singleplayer)
			return SharedConstants.getGameVersion().getName();

		return mc.getCurrentServerEntry().version != null ? mc.getCurrentServerEntry().version.getString() : "Unknown (" + SharedConstants.getGameVersion().getName() + ")";
	}
}
