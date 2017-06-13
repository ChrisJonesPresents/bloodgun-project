package warpedrealities.core.rendering;

import warpedrealities.core.shared.Vec2f;

public interface Square_Int {
	public void reposition(Vec2f p);

	public void reposition(float x, float y);

	public void setVisible(boolean visible);
	
	public boolean getVisible();

	public void setFlashing(int bool);

	public void setImage(int value);

	public void setFlipped(boolean reversed);

	public void remove();
}
