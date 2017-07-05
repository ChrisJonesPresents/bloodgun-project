package warpedrealities.bloodgun.blood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import warpedrealities.bloodgun.blood.burstFactory.impl.DirectionalBurstFactory;
import warpedrealities.bloodgun.blood.burstFactory.impl.OmniBurstFactory;
import warpedrealities.bloodgun.blood.bursts.DirectionalBurst;
import warpedrealities.bloodgun.blood.bursts.OmniBurst;
import warpedrealities.bloodgun.blood.bursts.ParticleBurst;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.shared.Vec2f;

public class ParticleManager {

	private Map<String,BurstHandler> burstMap;
	private List<BurstHandler> burstList;
	private List<ParticleEmitter> emitterList;
	private ParticleRenderer renderer;
	private CollisionHandler collisionHandler;
	public ParticleManager()
	{
		burstMap=new HashMap<String,BurstHandler>();
		burstList=new ArrayList<BurstHandler>();
		emitterList=new ArrayList<ParticleEmitter>();
		renderer=new ParticleRenderer();
		buildHandlers();
	}
	
	private void buildHandlers()
	{
		burstList=new ArrayList<BurstHandler>();
		burstMap=new HashMap<String,BurstHandler>();
		
		BurstHandler handler=new BurstHandler(new DirectionalBurstFactory(renderer));
		burstList.add(handler);
		burstMap.put("directional", handler);
		handler=new BurstHandler(new OmniBurstFactory(renderer));
		burstList.add(handler);
		burstMap.put("omni",handler);
		
	}
	
	
	public ParticleRenderer getRenderer()
	{
		return renderer;
	}


	public void update(float dt) {
	
		for (int i=0;i<burstList.size();i++)
		{
			burstList.get(i).update(dt);
		}
		for (int i=0;i<emitterList.size();i++)
		{
			emitterList.get(i).update(dt);
		}
	}
	
	public void addEmitter(ParticleEmitter emitter)
	{

		Particle [] particles=new Particle[32];
		for (int i=0;i<particles.length;i++)
		{
			particles[i]=new Particle();
		}
		renderer.addParticles(Arrays.asList(particles));
		emitter.setParticles(particles);
	}
	
	public void addBurst(String id,Vec2f position,Vec2f vector)
	{
		burstMap.get(id).addBurst(position, vector, collisionHandler);
	}


	public void setCollisionHandler(CollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
	}
	
	
}
