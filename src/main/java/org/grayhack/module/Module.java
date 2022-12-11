/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.grayhack.GrayHack;
import org.grayhack.setting.module.ModuleSetting;
import org.grayhack.setting.module.SettingKey;
import org.grayhack.util.io.GrayFileHelper;

import net.minecraft.client.MinecraftClient;

public class Module {

	public static final int KEY_UNBOUND = -1481058891;

	protected static final MinecraftClient mc = MinecraftClient.getInstance();
	private String name;
	private SettingKey key;

	private boolean enabled;
	private final boolean defaultEnabled;
	private boolean subscribed;

	private ModuleCategory category;
	private String desc;
	private List<ModuleSetting<?>> settings;

	public Module(String name, int key, ModuleCategory category, String desc, ModuleSetting<?>... settings) {
		this(name, key, category, false, desc, settings);
	}

	public Module(String name, int key, ModuleCategory category, boolean enabled, String desc, ModuleSetting<?>... settings) {
		this.name = name;
		this.category = category;
		this.desc = desc;
		this.settings = new ArrayList<>(Arrays.asList(settings));

		this.key = new SettingKey(key).withDesc("The bind for this module, press [DELETE] to unbind.");
		this.settings.add(this.key);

		defaultEnabled = enabled;
		if (enabled) {
			setEnabled(true);
		}
	}

	public void onEnable(boolean inWorld) {
		GrayFileHelper.SCHEDULE_SAVE_MODULES.set(true);

		subscribed = GrayHack.eventBus.subscribe(this);
	}

	public void onDisable(boolean inWorld) {
		if (subscribed) {
			GrayHack.eventBus.unsubscribe(this);
		}

		GrayFileHelper.SCHEDULE_SAVE_MODULES.set(true);
	}

	public String getName() {
		return name;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public String getDesc() {
		return desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKey() {
		return key.getValue();
	}

	public void setKey(int key) {
		this.key.setValue(key);

		GrayFileHelper.SCHEDULE_SAVE_MODULES.set(true);
	}

	public List<ModuleSetting<?>> getSettings() {
		return settings;
	}

	public ModuleSetting<?> getSetting(int s) {
		return settings.get(s);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if (this.enabled != enabled)
			toggle();
	}

	public boolean isDefaultEnabled() {
		return defaultEnabled;
	}

	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			onEnable(mc.world != null);
		} else {
			onDisable(mc.world != null);
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Module))
			return false;

		return name.equals(((Module) obj).name);
	}
}
