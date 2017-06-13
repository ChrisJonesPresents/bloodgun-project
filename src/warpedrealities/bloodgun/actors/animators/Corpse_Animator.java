package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public class Corpse_Animator implements Animator {

	private Square_Int sprites[];
	private float clock;
	private int frame;
	
	public Corpse_Animator(Square_Int sprites[])
	{
		this.sprites=sprites;
		
	}
	
	private void playDying(float dt)
	{
		if (clock<0.375F)
		{
			clock+=dt;
			setFrame(4+(int)(clock*8));		
		}

	}
	
	private void setFrame(int index)
	{
		if (index!=frame)
		{
			frame=index;
			for (int i=0;i<6;i++)
			{
				sprites[i].setImage(16+(i*8)+(index));
			}
		}
	
	}
	
	@Override
	public void animate(float dT, Vec2f velocity, boolean supported, State state, AnimationState animationState) {
		if (supported)
		{
			playDying(dT);
		}
	}

	@Override
	public void setReversed(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReversed() {
		// TODO Auto-generated method stub
		return false;
	}

	private int removeUpper()
	{
		for (int i=3;i<6;i++)
		{
			if (sprites[i].getVisible())
			{
				sprites[i].setVisible(false);
				return i;
			}
		}
		return 0;
	}
	
	private int removeLower()
	{
		if (sprites[1].getVisible())
		{
			sprites[1].setVisible(false);
			return 1;
		}
		else
		{
			sprites[2].setVisible(false);
			return 2;
		}
	}
	
	public int applyWound(boolean upper) {

		if (upper)
		{
			return removeUpper();
		}
		else
		{
			return removeLower();
		}
	}

}
