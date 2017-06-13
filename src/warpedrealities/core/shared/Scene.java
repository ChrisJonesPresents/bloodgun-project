package warpedrealities.core.shared;

import warpedrealities.core.input.MouseHook;

public interface Scene {

	// use arrays to pass ints to the scenes
	// 0 is tint variable
	// 1 view matrix
	// 2 object matrix

	public void update(float dt);

	public void draw();

	public void start(MouseHook mouse);

	public void end();

}
