package warpedrealities.core.shared;

import warpedrealities.core.core.Game;
import warpedrealities.core.input.MouseHook;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

abstract public class SceneBase implements Scene {

	protected static int openGL_Variables[];

	protected FloatBuffer matrix44Buffer;
	protected int m_textureIds[];
	protected Matrix4f m_GUImatrix;

	public static int[] getVariables() {
		return openGL_Variables;
	}

	public SceneBase() {

	}

	public SceneBase(int variables[]) {
		openGL_Variables = variables;

	}

	@Override
	abstract public void update(float dt);

	@Override
	abstract public void draw();

	@Override
	abstract public void start(MouseHook mouse);

	@Override
	abstract public void end();

	public static void setVariables(int[] var) {
		openGL_Variables = var;
	}

}
