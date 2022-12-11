/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.util.io;

import org.grayhack.util.GrayLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class GrayJsonHelper {

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void addJsonElement(String path, String key, JsonElement element) {
		JsonObject json = new JsonObject();

		if (GrayFileMang.fileExists(path)) {
			String content = GrayFileMang.readFile(path);

			if (!content.isEmpty()) {
				try {
					json = JsonParser.parseString(content).getAsJsonObject();
				} catch (Exception e) {
					GrayLogger.logger.error("Error trying to read json file \"" + path + "\", Overwriting file to add element!", e);
				}
			}
		}
		
		json.add(key, element);
		GrayFileMang.createEmptyFile(path);
		GrayFileMang.appendFile(path, GSON.toJson(json));
	}

	public static void setJsonFile(String path, JsonObject element) {
		GrayFileMang.createEmptyFile(path);
		GrayFileMang.appendFile(path, GSON.toJson(element));
	}

	public static JsonElement readJsonElement(String path, String key) {
		JsonObject jo = readJsonFile(path);

		if (jo == null)
			return null;

		if (jo.has(key)) {
			return jo.get(key);
		}

		return null;
	}

	public static JsonObject readJsonFile(String path) {
		String content = GrayFileMang.readFile(path);

		if (content.isEmpty())
			return null;

		try {
			return JsonParser.parseString(content).getAsJsonObject();
		} catch (JsonParseException | IllegalStateException e) {
			GrayLogger.logger.error("Error trying to read json file \"" + path + "\", Deleting file!", e);

			GrayFileMang.deleteFile(path);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends JsonElement> T parseOrNull(String json, Class<T> type) {
		try {
			return (T) JsonParser.parseString(json);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatJson(String json) {
		return GSON.toJson(JsonParser.parseString(json));
	}

	public static String formatJson(JsonElement json) {
		return GSON.toJson(json);
	}
}
