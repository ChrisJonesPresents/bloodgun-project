package warpedrealities.core.rendering;

import java.nio.FloatBuffer;

public interface Renderable {

	public void draw(int objmatrix, int tintvar, FloatBuffer matrix44fbuffer);
	
	public void discard();
}
