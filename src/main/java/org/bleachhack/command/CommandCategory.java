/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.command;

public enum CommandCategory {

	/** Commands that only works in creative (like giving items with custom nbt). **/
	CREATIVE,

	/** Commands that alter modules. **/
	MODULES,

	/** Other Commands. **/
	MISC
}
