package warpedrealities.bloodgun.rendering.collisionOverlay;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.bloodgun.collisionHandling.graph.chunkGraph.ChunkGraph;
import warpedrealities.core.shared.Tools;
import warpedrealities.core.shared.Vec2f;
import warpedrealities.core.shared.Vertex;

public class CollisionRenderer {

	private int VBO, VAO, VIO, textureID;
	private ChunkGraph graph;
	protected Matrix4f matrix;
	protected int indiceCount;

	public CollisionRenderer(ChunkGraph graph, Vec2f position) {

		matrix = new Matrix4f();
		matrix.m00 = 1;
		matrix.m11 = 1;
		matrix.m22 = 1;
		matrix.m33 = 1;
		matrix.m30 = 0;
		matrix.m31 = 0;

		this.graph = graph;
		generate();
	}

	private void generate() {
		textureID = Tools.loadPNGTexture("assets/art/red.png", GL13.GL_TEXTURE0);
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);

		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);

		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(4 * 6 * graph.getLines().size());
		IntBuffer indiceBuffer = BufferUtils.createIntBuffer(6 * graph.getLines().size());

		for (int i = 0; i < graph.getLines().size(); i++) {
			Line l = graph.getLines().get(i);
			Vertex v[] = new Vertex[4];

			v[0] = new Vertex(l.getStart().x + 0.1F, l.getStart().y + 0.1F, 0, 0, 0);
			v[1] = new Vertex(l.getStart().x, l.getStart().y, 0, 1, 0);
			v[2] = new Vertex(l.getEnd().x + 0.1F, l.getEnd().y + 0.1F, 0, 0, 1);
			v[3] = new Vertex(l.getEnd().x, l.getEnd().y, 0, 1, 1);

			for (int k = 0; k < 4; k++) {
				verticesBuffer.put(v[k].pos);
				verticesBuffer.put(v[k].tex);
			}

			int buffer[] = new int[6];
			buffer[0] = (i * 4) + 0;
			buffer[1] = (i * 4) + 1;
			buffer[2] = (i * 4) + 3;
			buffer[3] = (i * 4) + 1;
			buffer[4] = (i * 4) + 2;
			buffer[5] = (i * 4) + 3;
			indiceBuffer.put(buffer);

		}

		verticesBuffer.flip();
		indiceBuffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_DYNAMIC_DRAW);

		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.size, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.size, 4 * 4);

		indiceCount = 6 * graph.getLines().size();
		VIO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VIO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiceBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void draw(int objmatrix, int tintvar, FloatBuffer matrix44fbuffer) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		matrix.store(matrix44fbuffer);
		matrix44fbuffer.flip();
		GL20.glUniformMatrix4fv(objmatrix, false, matrix44fbuffer);
		// link mesh
		if (indiceCount > 0) {
			// bind all the links to chunk elements
			GL30.glBindVertexArray(VAO);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VIO);
			GL11.glDrawElements(GL11.GL_TRIANGLES, indiceCount, GL11.GL_UNSIGNED_INT, 0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
	}

	public void discard() {
		GL11.glDeleteTextures(textureID);
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
