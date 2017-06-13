package warpedrealities.core.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import warpedrealities.core.core.Tile_Int;
import warpedrealities.core.shared.Vertex;

public class Layer {
	int VBO, VAO, VIO, indiceCount;

	boolean draw;

	public Layer() {

		VBO = -1;
		VAO = -1;
		VIO = -1;
		indiceCount = 0;

		draw = false;
	}

	public boolean getDraw() {
		return draw;
	}

	public int getTileCount() {
		return indiceCount / 6;
	}

	public void Discard() {
		draw = false;

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

	public void Generate(Tile_Int[][] tiles, boolean background) {

		// discard elements if they already exist
		Discard();

		draw = true;
		// build new elements based on a specific level
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);

		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);

		int count = 0;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (tiles[i][j].getBackground() > 0 && background) {
					count++;
				}
				if (tiles[i][j].getForeground() > 0 && !background) {
					count++;
				}
			}
		}

		// build the vertex buffer here
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(4 * 6 * count);
		// build indice buffer, it's not complex its a repeating pattern of six
		// offset through the sequence
		IntBuffer indiceBuffer = BufferUtils.createIntBuffer(6 * count);
		int index = 0; // use this to keep track of where we're placing things
						// in the list

		// build squares, such squares
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int image = tiles[i][j].getForeground();
				if (background) {
					image = tiles[i][j].getBackground();
				}
				if (image > 0) {
					// generate square,

					// precalc UV based on value
					int V = (image - 1) / 4;
					int U = (image - 1) % 4;
					float Uf = U * 0.25f;
					float Vf = V * 0.25f;

					Vertex v[] = new Vertex[4];
					v[0] = new Vertex(i, j, 0, Uf, Vf + 0.25f);
					v[1] = new Vertex(i + 1, j, 0, Uf + 0.25f, Vf + 0.25f);
					v[2] = new Vertex(i + 1, j + 1, 0, Uf + 0.25f, Vf);
					v[3] = new Vertex(i, j + 1, 0, Uf, Vf);
					for (int k = 0; k < 4; k++) {
						verticesBuffer.put(v[k].pos);
						verticesBuffer.put(v[k].tex);
					}
					// generate indices offset by 6*index
					int buffer[] = new int[6];
					buffer[0] = index * 4;
					buffer[1] = (index * 4) + 1;
					buffer[2] = (index * 4) + 3;
					buffer[3] = (index * 4) + 1;
					buffer[4] = (index * 4) + 2;
					buffer[5] = (index * 4) + 3;
					indiceBuffer.put(buffer);
					// increment index for purposes of alignment
					index++;
				}
			}
		}

		verticesBuffer.flip();
		indiceBuffer.flip();
		indiceCount = count * 6;
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STREAM_DRAW);

		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.size, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.size, 4 * 4);

		VIO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VIO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiceBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

	}

	public void draw() {
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

}
