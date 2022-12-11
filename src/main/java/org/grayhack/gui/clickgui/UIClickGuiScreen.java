/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.gui.clickgui;

import java.util.List;

import org.grayhack.gui.clickgui.window.ModuleWindow;
import org.grayhack.gui.clickgui.window.UIContainer;
import org.grayhack.gui.clickgui.window.UIWindow;
import org.grayhack.module.ModuleManager;
import org.grayhack.module.mods.UI;
import org.grayhack.util.io.GrayFileHelper;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;


public class UIClickGuiScreen extends ClickGuiScreen {

	public static UIClickGuiScreen INSTANCE = new UIClickGuiScreen();

	private UIContainer uiContainer;

	public UIClickGuiScreen() {
		this(new UIContainer());
	}

	public UIClickGuiScreen(UIContainer uiContainer) {
		super(Text.literal("UI Editor"));
		this.uiContainer = uiContainer;
	}

	public void init() {
		super.init();

		clearWindows();
		uiContainer.windows.values().forEach(this::addWindow);

		addWindow(new ModuleWindow(List.of(ModuleManager.getModule(UI.class)),
				200, 200, 75, "Render", new ItemStack(Items.YELLOW_STAINED_GLASS)));
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		GrayFileHelper.SCHEDULE_SAVE_UI.set(true);

		uiContainer.updatePositions(width, height);

		for (UIWindow w: uiContainer.windows.values()) {
			boolean shouldClose = w.shouldClose();
			if (shouldClose && !w.closed)
				w.detachFromOthers(false);

			w.closed = shouldClose;
		}

		super.render(matrices, mouseX, mouseY, delta);
	}

	public UIContainer getUIContainer() {
		return uiContainer;
	}
}
