package warpedrealities.bloodgun.rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import warpedrealities.bloodgun.level.Level;
import warpedrealities.core.core.Game;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.shared.Vec2f;

public class Platformer_Renderer {

	private SpriteManager spriteManager;
	private Level_Renderer levelRenderer;
	private FloatBuffer floatBuffer;
	protected Matrix4f viewmatrix;
	private Vec2f cameraPosition;

	public Platformer_Renderer() {
		cameraPosition = new Vec2f(1, 1);
		spriteManager = new SpriteManager();
		viewmatrix = new Matrix4f();
		viewmatrix.m00 = 0.1F;
		viewmatrix.m11 = 0.125F;
		viewmatrix.m22 = 1.0F;
		viewmatrix.m33 = 1.0F;
		viewmatrix.m30 = 0;
		viewmatrix.m31 = 0;
		viewmatrix.m32 = 0;
		floatBuffer = BufferUtils.createFloatBuffer(16);
		levelRenderer = new Level_Renderer();
	}

	public void centerCamera(float x, float y, Level level) {
		if (x < 10.5F) {
			x = 10.5F;
		}
		if (y < 7.5F) {
			y = 7.5F;
		}
		if (y > 8.0F) {
			y = 8.0F;
		}
		cameraPosition.x = x;
		cameraPosition.y = y;
		viewmatrix.m30 = -1 * (cameraPosition.x + 0.5F) / 10;
		viewmatrix.m31 = -1 * (cameraPosition.y + 0.5F) / 8;
		levelRenderer.generate((int) x, level);
	}

	public void drawSetup(int[] openglVariables) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL20.glUniformMatrix4fv(openglVariables[3], false, floatBuffer);
		GL20.glUseProgram(Game.pShader);
		GL20.glUniform4f(openglVariables[0], 1.0F, 1.0F, 1.0F, 1);
		viewmatrix.store(floatBuffer);
		floatBuffer.flip();
		GL20.glUniformMatrix4fv(openglVariables[1], false, floatBuffer);

	}

	public void draw(int objMatrix, int tintVar) {
		levelRenderer.drawBackground(cameraPosition.x, cameraPosition.y, objMatrix, tintVar, floatBuffer);
		levelRenderer.drawForeground(objMatrix, tintVar, floatBuffer);
		// levelRenderer.drawCollision(objMatrix, tintVar, floatBuffer);
		spriteManager.draw(objMatrix, tintVar, floatBuffer);
	}

	public void discard() {
		spriteManager.discard();
		levelRenderer.discard();
	}

	public void addSprite(Sprite sprite, String textureName) {
		spriteManager.addSprite(sprite, textureName);
	}

	public void removeSprite(Sprite sprite) {
		spriteManager.removeSprite(sprite);
	}

	public Vec2f getCameraPosition() {
		return cameraPosition;
	}

	public SpriteManager getSpriteManager() {
		return spriteManager;
	}

}
