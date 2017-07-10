/*
 * Copyright 2016-2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.util;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class ColorUtils {

	private ColorUtils() {}

	public static Color getColorFromInt(int color) {
		return new Color(color);
	}

	public static Color getColorFromIntArray(int[] color) {
		return new Color(getIntColorFromIntArray(color));
	}

	public static int getIntColorFromIntArray(int[] color) {
		return getIntFromColor(color[0], color[1], color[2]);
	}

	public static int getIntFromColor(Color color) {
		return getIntFromColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static int getIntFromColor(int Red, int Green, int Blue) {
		Red = (Red << 16) & 0x00FF0000;
		Green = (Green << 8) & 0x0000FF00;
		Blue = Blue & 0x000000FF;

		return 0xFF000000 | Red | Green | Blue;
	}

	public static int[] getIntArrayFromColor(int red, int green, int blue) {
		return getIntArrayFromColor(red, green, blue, 255);
	}

	public static int[] getIntArrayFromColor(int red, int green, int blue, int alpha) {
		return new int[] { red, green, blue };
	}

	public static int[] getIntArrayFromColor(Color color) {
		return getIntArrayFromColor(color.getRed(), color.getBlue(), color.getGreen());
	}

	public static int[] getIntArrayFromColorWithAlpha(Color color) {
		return getIntArrayFromColor(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha());
	}

	private static final int BYTES_PER_PIXEL = 4;

	private static final int DEFULT_WIDTH = 64, DEFULT_HEIGHT = 32;

	public static int loadTextureFromColour(Color color) {
		return loadTextureFromColour(color, DEFULT_WIDTH, DEFULT_HEIGHT);
	}

	public static int loadTextureFromColour(Color color, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, width, height);
		return loadTexture(image);
	}

	public static int loadTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}
}
