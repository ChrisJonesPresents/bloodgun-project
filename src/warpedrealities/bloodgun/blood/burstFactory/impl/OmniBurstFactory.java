package warpedrealities.bloodgun.blood.burstFactory.impl;

import warpedrealities.bloodgun.blood.ParticleRenderer;
import warpedrealities.bloodgun.blood.burstFactory.BurstFactory;
import warpedrealities.bloodgun.blood.bursts.DirectionalBurst;
import warpedrealities.bloodgun.blood.bursts.OmniBurst;
import warpedrealities.bloodgun.blood.bursts.ParticleBurst;

public class OmniBurstFactory implements BurstFactory {

	private ParticleRenderer particleRenderer;

	public OmniBurstFactory(ParticleRenderer renderer)
	{
		this.particleRenderer=renderer;
	}
	
	@Override
	public ParticleBurst buildBurst() {
		return new OmniBurst(particleRenderer);
	}

}
