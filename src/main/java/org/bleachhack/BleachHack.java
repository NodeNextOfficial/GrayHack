/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fabricmc.api.ModInitializer;
import net.minecraft.SharedConstants;

import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.Level;
import org.grayhack.command.CommandManager;
import org.grayhack.command.CommandSuggestor;
import org.grayhack.eventbus.GrayEventBus;
import org.grayhack.eventbus.handler.InexactEventHandler;
import org.grayhack.gui.GrayTitleScreen;
import org.grayhack.gui.clickgui.ModuleClickGuiScreen;
import org.grayhack.module.ModuleManager;
import org.grayhack.setting.option.Option;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.GrayPlayerManager;
import org.grayhack.util.FriendManager;
import org.grayhack.util.Watermark;
import org.grayhack.util.io.GrayFileHelper;
import org.grayhack.util.io.GrayFileMang;
import org.grayhack.util.io.GrayJsonHelper;
import org.grayhack.util.io.GrayOnlineMang;

public class GrayHack implements ModInitializer {

	private static GrayHack instance = null;

	public static final String VERSION = "1.2.6";
	public static final int INTVERSION = 40;
	public static Watermark watermark;

	public static GrayEventBus eventBus;

	public static FriendManager friendMang;
	public static GrayPlayerManager playerMang;

	private static CompletableFuture<JsonObject> updateJson;

	//private GrayFileMang grayFileManager;

	public static GrayHack getInstance() {
		return instance;
	}

	public GrayHack() {
		if (instance != null) {
			throw new RuntimeException("A GrayHack instance already exists.");
		}
	}

	// Phase 1
	// TODO: base-rewrite
	@Override
	public void onInitialize() {
		long initStartTime = System.currentTimeMillis();

		instance = this;
		watermark = new Watermark();
		eventBus = new GrayEventBus(new InexactEventHandler("grayhack"), GrayLogger.logger);

		friendMang = new FriendManager();
		playerMang = new GrayPlayerManager();

		//this.eventBus = new EventBus();
		//this.grayFileManager = new GrayFileMang();

		GrayFileMang.init();

		GrayFileHelper.readOptions();
		GrayFileHelper.readFriends();

		if (Option.PLAYERLIST_SHOW_AS_BH_USER.getValue()) {
			playerMang.startPinger();
		}

		if (Option.GENERAL_CHECK_FOR_UPDATES.getValue()) {
			updateJson = GrayOnlineMang.getResourceAsync("update/" + SharedConstants.getGameVersion().getName().replace(' ', '_') + ".json", BodyHandlers.ofString())
					.thenApply(s -> GrayJsonHelper.parseOrNull(s, JsonObject.class));
		}

		JsonElement mainMenu = GrayFileHelper.readMiscSetting("customTitleScreen");
		if (mainMenu != null && !mainMenu.getAsBoolean()) {
			GrayTitleScreen.customTitleScreen = false;
		}

		GrayLogger.logger.log(Level.INFO, "Loaded GrayHack (Phase 1) in %d ms.", System.currentTimeMillis() - initStartTime);
	}

	// Phase 2
	// Called after most of the game has been initialized in MixinMinecraftClient so all game resources can be accessed
	public void postInit() {
		long initStartTime = System.currentTimeMillis();

		ModuleManager.loadModules(this.getClass().getClassLoader().getResourceAsStream("grayhack.modules.json"));
		GrayFileHelper.readModules();

		// TODO: move ClickGui and UI to phase 1
		ModuleClickGuiScreen.INSTANCE.initWindows();
		GrayFileHelper.readClickGui();
		GrayFileHelper.readUI();

		CommandManager.loadCommands(this.getClass().getClassLoader().getResourceAsStream("grayhack.commands.json"));
		CommandSuggestor.start();

		GrayFileHelper.startSavingExecutor();

		GrayLogger.logger.log(Level.INFO, "Loaded GrayHack (Phase 2) in %d ms.", System.currentTimeMillis() - initStartTime);
	}

	public static JsonObject getUpdateJson() {
		try {
			return updateJson.get();
		} catch (Exception e) {
			return null;
		}
	}
}
