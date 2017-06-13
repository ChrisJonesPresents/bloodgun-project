package warpedrealities.core.font;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;

import warpedrealities.core.core.Game;
import warpedrealities.core.font.IOUtil;
import warpedrealities.core.shared.Tools;
import warpedrealities.core.shared.Vec2f;
import warpedrealities.core.shared.Vertex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.*;

public class FontSupport {

	private static FontSupport instance;
	private int textureID;

	private Map<Integer, Glyph> glyphData;

	public static FontSupport getInstance() {
		if (instance == null) {
			instance = new FontSupport();
		}
		return instance;
	}

	private FontSupport() {
		textureID = Tools.loadPNGTexture("assets/art/arial_0.png", GL13.GL_TEXTURE0);

		GlyphLoader loader = new GlyphLoader();
		loader.buildMap("assets/data/arial.fnt");
		glyphData = loader.getGlyphs();

	}

	public FloatBuffer generateGeometry(String text, int length) {
		FloatBuffer verticesbuffer = BufferUtils.createFloatBuffer(4 * 6 * length);

		float x = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c < 32 || 128 <= c)
				continue;

			Glyph glyph = glyphData.get((int) c);

			// build vertex pattern
			Vertex[] verts = new Vertex[4];
			verts[0] = new Vertex(x, 0, 0, glyph.getCorner().x, glyph.getCorner().y);
			verts[1] = new Vertex(x + glyph.getSize().x * 8, 0, 0, glyph.getCorner().x + glyph.getSize().x,
					glyph.getCorner().y);
			verts[2] = new Vertex(x + glyph.getSize().x * 8, glyph.getSize().y * -8, 0,
					glyph.getCorner().x + glyph.getSize().x, glyph.getCorner().y + glyph.getSize().y);
			verts[3] = new Vertex(x, glyph.getSize().y * -8, 0, glyph.getCorner().x,
					glyph.getCorner().y + glyph.getSize().y);

			for (int j = 0; j < 4; j++) {
				verts[j].pos[0] += glyph.getOffset().x;
				verts[j].pos[1] -= glyph.getOffset().y;

				verticesbuffer.put(verts[j].pos);
				verticesbuffer.put(verts[j].tex);
			}
			x += glyph.getAdvanceX();
		}
		for (int i = verticesbuffer.position(); i < verticesbuffer.capacity(); i++) {
			verticesbuffer.put(0);
			;
		}
		return verticesbuffer;
	}

	public FloatBuffer generateDebugGeometry(String text) {
		FloatBuffer verticesbuffer = BufferUtils.createFloatBuffer(4 * 6 * text.length());

		float x = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c < 32 || 128 <= c)
				continue;

			Glyph glyph = glyphData.get((int) c);

			// build vertex pattern
			Vertex[] verts = new Vertex[4];
			verts[0] = new Vertex(x, 0, 0, glyph.getCorner().x, glyph.getCorner().y);
			verts[1] = new Vertex(x + glyph.getSize().x * 8, 0, 0, glyph.getCorner().x + glyph.getSize().x,
					glyph.getCorner().y);
			verts[2] = new Vertex(x + glyph.getSize().x * 8, glyph.getSize().y * -8, 0,
					glyph.getCorner().x + glyph.getSize().x, glyph.getCorner().y + glyph.getSize().y);
			verts[3] = new Vertex(x, glyph.getSize().y * -8, 0, glyph.getCorner().x,
					glyph.getCorner().y + glyph.getSize().y);

			for (int j = 0; j < 4; j++) {
				verts[j].pos[0] += glyph.getOffset().x;
				verts[j].pos[1] -= glyph.getOffset().y;
				verts[j].pos[1] += 1;
				verticesbuffer.put(verts[j].pos);
				verticesbuffer.put(verts[j].tex);
			}
			x += glyph.getAdvanceX();
		}

		for (int i = verticesbuffer.position(); i < verticesbuffer.capacity(); i++) {
			verticesbuffer.put(0);
			;
		}
		return verticesbuffer;
	}

	public void setTexture() {

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		// int tex=GL20.glGetUniformLocation(Game.m_pshader, "texture_diffuse");
		// GL20.glUniform1i(tex, 0);

	}

	public static void discard() {
		if (instance != null) {
			// instance.q.free();
			// memFree(instance.cdata);
			GL11.glDeleteTextures(instance.textureID);
		}
	}

}
