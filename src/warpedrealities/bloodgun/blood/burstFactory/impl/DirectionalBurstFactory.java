package warpedrealities.bloodgun.blood.burstFactory.impl;

import warpedrealities.bloodgun.blood.ParticleRenderer;
import warpedrealities.bloodgun.blood.burstFactory.BurstFactory;
import warpedrealities.bloodgun.blood.bursts.DirectionalBurst;
import warpedrealities.bloodgun.blood.bursts.ParticleBurst;

public class DirectionalBurstFactory implements BurstFactory {
	
	private ParticleRenderer particleRenderer;

	public DirectionalBurstFactory(ParticleRenderer renderer)
	{
		this.particleRenderer=renderer;
	}
	
	@Override
	public ParticleBurst buildBurst() {
		return new DirectionalBurst(particleRenderer);
	}

}
