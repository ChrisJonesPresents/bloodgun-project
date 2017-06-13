package warpedrealities.core.rendering;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import warpedrealities.core.shared.Vec2f;

public class Sprite_Rotatable extends Sprite implements Square_Rotatable_Int {

	protected double rotation;

	public Sprite_Rotatable(Vec2f vec2f, float spriteSize, int numFrames) {
		super(vec2f, spriteSize, numFrames);

	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
		rotate();
	}

	@Override
	public void reposition(float x, float y) {
		spritePosition.x = x;
		spritePosition.y = y;

		matrix = Matrix4f.setIdentity(matrix);

		matrix.m30 = spritePosition.x;
		matrix.m31 = spritePosition.y;
		matrix.m32 = 0;
		rotate();
	}

	private void rotate() {
		float s = 1;
		if (flipped) {
			s = -1;
		}
		matrix.m00 = (float) (s * Math.cos(rotation)*spriteSize);
		matrix.m01 = (float) (s * -Math.sin(rotation)*spriteSize);
		matrix.m10 = (float) (1 * Math.sin(rotation)*spriteSize);
		matrix.m11 = (float) (1 * Math.cos(rotation)*spriteSize);

	}

}
