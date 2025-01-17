/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.mixin;

import org.grayhack.gui.GrayOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.minecraft.text.Text;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {

	private MixinOptionsScreen(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void init(CallbackInfo callback) {
		addDrawableChild(new ButtonWidget(this.width / 2 - 180, this.height / 6 + 120 - 6, 20, 20, Text.literal("BH"), button -> {
			client.setScreen(new GrayOptionsScreen(this));
		}));
	}
}
