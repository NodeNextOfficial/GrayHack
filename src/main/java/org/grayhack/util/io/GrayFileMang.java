/*
 * This file is part of the GrayHack distribution (https://github.com/GrayDrinker420/GrayHack/).
 * Copyright (c) 2021 Gray and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.grayhack.util.io;

import net.minecraft.client.MinecraftClient;
import org.grayhack.util.GrayLogger;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GrayFileMang {

	private static Path dir;

	public static void init() {
		dir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "gray/");
		if (!dir.toFile().exists()) {
			dir.toFile().mkdirs();
		}
	}

	/** Gets the gray directory in your minecraft folder. **/
	public static Path getDir() {
		return dir;
	}

	/** Reads a file and returns a string of its contents. **/
	public static String readFile(String path) {
		try {
			return Files.readString(getDir().resolve(path));
		} catch (NoSuchFileException ignored) {

		} catch (Exception e) {
			GrayLogger.logger.error("Error Reading File: " + path, e);
		}

		return "";
	}

	/** Reads a file and returns a list of the lines. **/
	public static List<String> readFileLines(String path) {
		try {
			return Files.readAllLines(getDir().resolve(path));
		} catch (NoSuchFileException ignored) {

		} catch (Exception e) {
			GrayLogger.logger.error("Error Reading File: " + path, e);
		}

		return new ArrayList<>();
	}

	/** Creates a file, doesn't do anything if the file already exists. **/
	public static void createFile(String path) {
		try {
			if (!fileExists(path)) {
				getDir().resolve(path).getParent().toFile().mkdirs();
				Files.createFile(getDir().resolve(path));
			}
		} catch (Exception e) {
			GrayLogger.logger.error("Error Creating File: " + path, e);
		}
	}

	/** Creates a file, clears it if it already exists **/
	public static void createEmptyFile(String path) {
		try {
			createFile(path);

			FileWriter writer = new FileWriter(getDir().resolve(path).toFile());
			writer.write("");
			writer.close();
		} catch (Exception e) {
			GrayLogger.logger.error("Error Clearing/Creating File: " + path, e);
		}
	}

	/** Adds a line to a file. **/
	public static void appendFile(String path, String content) {
		try {
			String fileContent = new String(Files.readAllBytes(getDir().resolve(path)));
			FileWriter writer = new FileWriter(getDir().resolve(path).toFile(), true);
			writer.write(
					(fileContent.endsWith("\n") || !fileContent.contains("\n") ? "" : "\n")
					+ content
					+ (content.endsWith("\n") ? "" : "\n"));
			writer.close();
		} catch (Exception e) {
			GrayLogger.logger.error("Error Appending File: " + path, e);
		}
	}

	/** Returns true if a file exists, returns false otherwise **/
	public static boolean fileExists(String path) {
		try {
			return getDir().resolve(path).toFile().exists();
		} catch (Exception e) {
			return false;
		}
	}

	/** Deletes a file if it exists. **/
	public static void deleteFile(String path) {
		try {
			Files.deleteIfExists(getDir().resolve(path));
		} catch (Exception e) {
			GrayLogger.logger.error("Error Deleting File: " + path, e);
		}
	}
}
