/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.module.mods;

import org.grayhack.module.Module;
import org.grayhack.module.ModuleCategory;
import org.grayhack.module.ModuleManager;

public class Panic extends Module {

    public Panic() {
        super("Panic", KEY_UNBOUND, ModuleCategory.MISC, "Toggles off all modules at once.");
    }

    @Override
    public void onEnable(boolean inWorld) {
        super.onEnable(inWorld);

        for (Module m : ModuleManager.getModules()) {
            m.setEnabled(false);
        }
    }
}
