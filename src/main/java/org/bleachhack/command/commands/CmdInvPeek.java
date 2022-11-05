/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command.commands;

import org.grayhack.command.Command;
import org.grayhack.command.CommandCategory;
import org.grayhack.command.exception.CmdSyntaxException;
import org.grayhack.util.GrayLogger;
import org.grayhack.util.GrayQueue;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class CmdInvPeek extends Command {

	public CmdInvPeek() {
		super("invpeek", "Shows the inventory of another player in your render distance.", "invpeek <player>", CommandCategory.MISC,
				"playerpeek", "invsee", "inv");
	}

	@Override
	public void onCommand(String alias, String[] args) throws Exception {
		if (args.length == 0) {
			throw new CmdSyntaxException();
		}

		for (AbstractClientPlayerEntity e: mc.world.getPlayers()) {
			if (e.getDisplayName().getString().equalsIgnoreCase(args[0])) {
				GrayQueue.add(() -> {
					GrayLogger.info("Opened inventory for " + e.getDisplayName().getString());

					mc.setScreen(new InventoryScreen(e) {
						public boolean mouseClicked(double mouseX, double mouseY, int button) {
							return false;
						}

						protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
							RenderSystem.setShader(GameRenderer::getPositionTexShader);
							RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
							RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
							this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
							drawEntity(x + 51, y + 75, 30, (float) (x + 51) - mouseX, (float) (y + 75 - 50) - mouseY, this.client.player);
						}
					});
				});

				return;
			}
		}

		GrayLogger.error("Player " + args[0] + " not found!");
	}

}
