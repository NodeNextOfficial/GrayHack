/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import org.apache.commons.lang3.tuple.Triple;
import org.grayhack.GrayHack;
import org.grayhack.gui.AccountManagerScreen;
import org.grayhack.gui.GrayCreditsScreen;
import org.grayhack.gui.GrayOptionsScreen;
import org.grayhack.gui.GrayTitleScreen;
import org.grayhack.gui.UpdateScreen;
import org.grayhack.gui.clickgui.ModuleClickGuiScreen;
import org.grayhack.gui.window.WindowManagerScreen;
import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.ClickGui;
import org.grayhack.setting.option.Option;
import org.grayhack.util.io.GrayFileHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import net.minecraft.text.Text;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {

	@Unique private static boolean firstLoad = true;

	private MixinTitleScreen(Text title) {
		super(title);
	}

	@Inject(method = "init()V", at = @At("HEAD"))
	private void init(CallbackInfo info) {
		if (firstLoad) {
			if (Option.GENERAL_SHOW_UPDATE_SCREEN.getValue()) {
				JsonObject updateJson = GrayHack.getUpdateJson();
				if (updateJson != null && updateJson.has("version") && updateJson.get("version").getAsInt() > GrayHack.INTVERSION)
					client.setScreen(new UpdateScreen(null, updateJson));
			}

			firstLoad = false;
			return;
		}

		if (GrayTitleScreen.customTitleScreen) {
			MinecraftClient.getInstance().setScreen(
					new WindowManagerScreen(
							Triple.of(new GrayTitleScreen(), "GrayHack", new ItemStack(Items.MUSIC_DISC_CAT)),
							Triple.of(new AccountManagerScreen(), "Accounts", new ItemStack(Items.PAPER)),
							Triple.of(ModuleClickGuiScreen.INSTANCE, "ClickGui", new ItemStack(Items.TOTEM_OF_UNDYING)),
							Triple.of(new GrayOptionsScreen(null), "Options", new ItemStack(Items.REDSTONE)),
							Triple.of(new GrayCreditsScreen(), "Credits", new ItemStack(Items.DRAGON_HEAD))) {

						public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
							if (keyCode == ModuleManager.getModule(ClickGui.class).getKey()) {
								selectWindow(2);
							}

							return super.keyPressed(keyCode, scanCode, modifiers);
						}
					});
		} else {
			addDrawableChild(new ButtonWidget(width / 2 - 124, height / 4 + 96, 20, 20, Text.literal("BH"), button -> {
				GrayTitleScreen.customTitleScreen = !GrayTitleScreen.customTitleScreen;
				GrayFileHelper.saveMiscSetting("customTitleScreen", new JsonPrimitive(true));
				client.setScreen(new TitleScreen(false));
			}));
		}
	}
}
