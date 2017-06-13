package warpedrealities.bloodgun.rendering;

import java.nio.FloatBuffer;
import java.util.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import warpedrealities.core.shared.Tools;
import warpedrealities.bloodgun.level.Level;
import warpedrealities.bloodgun.level.LevelChunk;

public class Level_Renderer {

	private Chunk_Renderer[] chunkList;
	private int[] tilesetIDs;

	public Level_Renderer() {
		tilesetIDs = new int[2];
		tilesetIDs[0] = Tools.loadPNGTexture("assets/art/tilesets/foreground.png", GL13.GL_TEXTURE0);
		tilesetIDs[1] = Tools.loadPNGTexture("assets/art/tilesets/background.png", GL13.GL_TEXTURE0);
		chunkList = new Chunk_Renderer[3];
	}

	private void shiftLeft(Level level) {

		if (chunkList[2] != null) {
			chunkList[2].discard();
		}
		for (int i = 2; i > 0; i--) {

			chunkList[i] = chunkList[i - 1];
		}
		int index = level.getChunks().indexOf(chunkList[1].getChunk());
		if (index > 0) {
			LevelChunk chunk = level.getChunks().get(index - 1);
			if (chunk != null) {
				chunkList[0] = new Chunk_Renderer(chunk);
			}
		}

	}

	private void shiftRight(Level level) {

		if (chunkList[0] != null) {
			chunkList[0].discard();
		}
		for (int i = 0; i < 2; i++) {
			chunkList[i] = chunkList[i + 1];
		}
		int index = level.getChunks().indexOf(chunkList[1].getChunk());
		LevelChunk chunk = null;
		if (level.getChunks().size() > index + 1) {
			chunk = level.getChunks().get(index + 1);
		}
		if (chunk != null) {
			chunkList[2] = new Chunk_Renderer(chunk);
		} else {
			level.generateChunk(false);
			chunk = level.getChunks().get(level.getChunks().size() - 1);
			chunkList[2] = new Chunk_Renderer(chunk);
		}
	}

	private void firstGenerate(int position, Level level) {
		chunkList[1] = new Chunk_Renderer(level.getChunks().get(0));
		chunkList[2] = new Chunk_Renderer(level.getChunks().get(1));
	}

	private void regenerate(int position, Level level) {

		if (position < chunkList[1].getPosition() - 0 && position > 16) {
			shiftLeft(level);
		}
		if (position > chunkList[1].getPosition() + 16) {
			shiftRight(level);
		}
	}

	public void generate(int position, Level level) {
		if (chunkList[1] == null) {
			firstGenerate(position, level);
		} else {
			regenerate(position, level);
		}
	}

	public void drawForeground(int objectMatrix, int tintvar, FloatBuffer floatBuffer) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tilesetIDs[0]);
		for (int i = 0; i < 3; i++) {
			if (chunkList[i] != null) {
				chunkList[i].drawForeground(objectMatrix, tintvar, floatBuffer);
			}
		}
	}

	public void drawBackground(float x, float y, int objectMatrix, int tintvar, FloatBuffer floatBuffer) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tilesetIDs[1]);
		for (int i = 0; i < 3; i++) {
			if (chunkList[i] != null) {
				chunkList[i].drawBackground(x - chunkList[i].getPosition(), y, objectMatrix, tintvar, floatBuffer);
			}
		}
	}

	public void drawCollision(int objectMatrix, int tintvar, FloatBuffer floatBuffer) {
		for (int i = 0; i < 3; i++) {
			if (chunkList[i] != null) {
				chunkList[i].drawCollision(objectMatrix, tintvar, floatBuffer);
			}
		}
	}

	public void discard() {
		GL11.glDeleteTextures(tilesetIDs[0]);
		GL11.glDeleteTextures(tilesetIDs[1]);
		for (int i = 0; i < 3; i++) {
			if (chunkList[i] != null) {
				chunkList[i].discard();
			}
		}
	}

}
