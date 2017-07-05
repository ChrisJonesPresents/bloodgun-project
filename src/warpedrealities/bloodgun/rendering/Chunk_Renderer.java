package warpedrealities.bloodgun.rendering;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import warpedrealities.bloodgun.level.LevelChunk;
import warpedrealities.bloodgun.rendering.collisionOverlay.CollisionRenderer;
import warpedrealities.core.rendering.Layer;
import warpedrealities.core.shared.Vec2f;

public class Chunk_Renderer {

	private LevelChunk chunk;
	private Layer[] layers;
	private Matrix4f foregroundMatrix;
	private Matrix4f backgroundMatrix;
	// private CollisionRenderer collisionRender;

	public Chunk_Renderer(LevelChunk chunk) {
		this.chunk = chunk;
		layers = new Layer[2];
		generate();
		foregroundMatrix = new Matrix4f();
		foregroundMatrix = Matrix4f.setIdentity(foregroundMatrix);
		foregroundMatrix.m30 = chunk.getPosition();
		backgroundMatrix = new Matrix4f();
		backgroundMatrix = Matrix4f.setIdentity(backgroundMatrix);
		backgroundMatrix.m30 = chunk.getPosition();
		// collisionRender=new CollisionRenderer(this.chunk.getGraph(),new
		// Vec2f(chunk.getPosition(),0));
	}

	private void generate() {
		layers[0] = new Layer();
		layers[1] = new Layer();
		layers[0].Generate(this.chunk.getTiles(), false);
		layers[1].Generate(this.chunk.getTiles(), true);
	}

	public void drawForeground(int objectMatrix, int tintvar, FloatBuffer floatBuffer) {

		// need to move the matrix to position the layer properly
		foregroundMatrix.store(floatBuffer);
		floatBuffer.flip();
		GL20.glUniformMatrix4fv(objectMatrix, false, floatBuffer);
		layers[0].draw();
	}

	private void calcBackground(float x, float y) {
		backgroundMatrix.m30 = chunk.getPosition() + (x * 0.05F);// *0.05F;
		backgroundMatrix.m31 = 0 + (y * 0.05F);// *0.05F;
	}

	public void drawBackground(float x, float y, int objectMatrix, int tintvar, FloatBuffer floatBuffer) {
		calcBackground(x, y);
		backgroundMatrix.store(floatBuffer);
		floatBuffer.flip();
		GL20.glUniformMatrix4fv(objectMatrix, false, floatBuffer);
		layers[1].draw();

	}

	public void drawCollision(int objectMatrix, int tintvar, FloatBuffer floatBuffer) {
		// collisionRender.draw(objectMatrix, tintvar, floatBuffer);
	}

	public void discard() {

		for (int i = 0; i < 2; i++) {
			layers[i].discard();
		}
		// collisionRender.discard();
	}

	public int getPosition() {
		return chunk.getPosition();
	}

	public LevelChunk getChunk() {
		return chunk;
	}

}
