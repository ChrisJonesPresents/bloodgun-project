package warpedrealities.bloodgun.blood;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import warpedrealities.core.rendering.Renderable;
import warpedrealities.core.shared.Tools;
import warpedrealities.core.shared.Vertex;

public class ParticleRenderer implements Renderable {

	private int texture,VBO,VAO,VIO,indiceCount;
	private float scale=0.1F;
	protected Matrix4f matrix;
	private List<Particle> particles;
	
	public ParticleRenderer()
	{
		particles=new ArrayList<Particle>();
		generate();
	}
	
	public void addParticles(List<Particle> particleCollection)
	{
		this.particles.addAll(particleCollection);
	}
	
	public void removeParticles(Particle [] particles)
	{
		
	}
	
	private void generate()
	{
		matrix = new Matrix4f();
		Matrix4f.setIdentity(matrix);

		texture=Tools.loadPNGTexture("assets/art/blood.png", GL13.GL_TEXTURE0);
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);

		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		// build the vertex buffer here
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(4 * 6);
		// build indice buffer, it's not complex its a repeating pattern of six
		// offset through the sequence
		IntBuffer indiceBuffer = BufferUtils.createIntBuffer(6);

		// build the four vertexes for the square
		warpedrealities.core.shared.Vertex v[] = new warpedrealities.core.shared.Vertex[4];

		v[0] = new Vertex(-0.5F * scale, -0.5F * scale, 0, 0, 1);
		v[1] = new Vertex(-0.5F * scale + scale, -0.5F * scale, 0, 1, 1);
		v[2] = new Vertex(-0.5F * scale + scale, -0.5F * scale + scale, 0, 1, 0);
		v[3] = new Vertex(-0.5F * scale, -0.5F * scale + scale, 0, 0, 0);

		for (int k = 0; k < 4; k++) {
			verticesBuffer.put(v[k].pos);
			verticesBuffer.put(v[k].tex);
		}

		int buffer[] = new int[6];
		buffer[0] = 0;
		buffer[1] = 1;
		buffer[2] = 3;
		buffer[3] = 1;
		buffer[4] = 2;
		buffer[5] = 3;
		indiceBuffer.put(buffer);

		verticesBuffer.flip();
		indiceBuffer.flip();
		indiceCount = 6;
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.size, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.size, 4 * 4);

		VIO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VIO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiceBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	@Override
	public void draw(int objmatrix, int tintvar, FloatBuffer matrix44fbuffer) {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL20.glUniform4f(tintvar, 1,1,1, 1);
		// link mesh
		if (indiceCount > 0) {
			// bind all the links to chunk elements
			GL30.glBindVertexArray(VAO);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VIO);

			
			for (int i = 0; i < particles.size(); i++) {
				if (particles.get(i).isAlive() == true) {
					// adjust obj matrix
					matrix.m30 = particles.get(i).getPosition().x;
					matrix.m31 = particles.get(i).getPosition().y;
					// set tint
;
					// set objmatrix
					matrix.store(matrix44fbuffer);
					matrix44fbuffer.flip();
					GL20.glUniformMatrix4fv(objmatrix, false, matrix44fbuffer);
					GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);

				}
			}
			

			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
	}

	@Override
	public void discard() {
		GL11.glDeleteTextures(texture);
		if (VAO != -1) {
			GL30.glBindVertexArray(VAO);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			if (VBO != -1) {
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				GL15.glDeleteBuffers(VBO);
			}

			if (VIO != -1) {
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
				GL15.glDeleteBuffers(VIO);
			}
			GL30.glBindVertexArray(0);
			GL30.glDeleteVertexArrays(VAO);
		}

		
	}
}
