/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.setting.module;

import java.util.function.UnaryOperator;

import org.grayhack.gui.clickgui.window.ClickGuiWindow.Tooltip;
import org.grayhack.setting.Setting;
import org.grayhack.setting.SettingDataHandler;
import org.grayhack.gui.clickgui.window.ModuleWindow;
import org.grayhack.util.io.GrayFileHelper;

import net.minecraft.client.util.math.MatrixStack;

public abstract class ModuleSetting<T> extends Setting<T> {

	public ModuleSetting(String name, T value, SettingDataHandler<T> handler) {
		super(name, "", value, handler);
	}
	
	public ModuleSetting(String name, T value, UnaryOperator<T> defaultValue, SettingDataHandler<T> handler) {
		super(name, "", value, defaultValue, handler);
	}

	public SettingMode asMode() {
		try {
			return (SettingMode) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}

	public SettingToggle asToggle() {
		try {
			return (SettingToggle) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}

	public SettingSlider asSlider() {
		try {
			return (SettingSlider) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}

	public SettingColor asColor() {
		try {
			return (SettingColor) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}
	
	public SettingButton asButton() {
		try {
			return (SettingButton) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <E> SettingList<E> asList(Class<E> itemClass) {
		try {
			return (SettingList<E>) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}

	public SettingRotate asRotate() {
		try {
			return (SettingRotate) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}
	
	public SettingKey asBind() {
		try {
			return (SettingKey) this;
		} catch (Exception e) {
			throw new ClassCastException("Execption parsing setting: " + this);
		}
	}

	public Tooltip getTooltip(ModuleWindow window, int x, int y, int len) {
		return new Tooltip(x + len + 2, y, getTooltip());
	}
	
	@Override
	public void setValue(T value) {
		super.setValue(value);
		GrayFileHelper.SCHEDULE_SAVE_MODULES.set(true);
	}

	public abstract void render(ModuleWindow window, MatrixStack matrices, int x, int y, int len);

	public abstract int getHeight(int len);
}
