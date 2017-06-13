package warpedrealities.core.gui;

import java.nio.FloatBuffer;

import warpedrealities.core.core.Game;
import warpedrealities.core.font.NuFont;
import warpedrealities.core.shared.MyListener;
import warpedrealities.core.shared.NinePatch;
import warpedrealities.core.shared.Vec2f;

public class Button extends GUIBase {

	NinePatch m_patch;
	String m_string;
	NuFont m_font;
	// encapsulation of patch, means gui elements don't inherit boxes
	Vec2f m_pos;
	Vec2f m_size;
	int m_ID;
	boolean m_active;

	public Button(Vec2f pos, Vec2f size, int texture, MyListener listener, String string, int ID) {
		m_active = true;
		m_pos = pos;
		m_size = size;
		m_listener = listener;
		m_string = string;
		// build ninepatch
		m_patch = new NinePatch(pos, size.x, size.y, texture);
		m_ID = ID;
		Vec2f p = new Vec2f(pos.x, pos.y);
		float sy = (size.y) * 0.65F;

		if (size.y <= 1) {
			m_font = new NuFont(new Vec2f(pos.x + (0.6F * size.y), pos.y + sy + 0.2F), 64, 1.0F * size.y);
		} else {
			m_font = new NuFont(new Vec2f(pos.x + 0.6F, pos.y + sy + 0.2F), 64, 1.0F);
		}

		m_font.setString(m_string);

	}

	public Button(Vec2f pos, Vec2f size, int texture, MyListener listener, String string, int ID, float s) {
		m_active = true;
		m_pos = pos;
		m_size = size;
		m_listener = listener;
		m_string = string;
		// build ninepatch
		float sy = (size.y) * 0.35F;
		m_patch = new NinePatch(pos, size.x, size.y, texture);
		m_ID = ID;
		if (size.y < 2) {
			m_font = new NuFont(new Vec2f(pos.x + 0.25F, pos.y + (0.5F * size.y) + sy), 64, 0.8F * s);
		} else {
			m_font = new NuFont(new Vec2f(pos.x + 0.5F, pos.y + 1 + sy), 64, 0.8F * s);
		}

		m_font.setString(m_string);

	}

	public void setString(String string) {
		m_font.setString(string);
	}

	public void setActive(boolean a) {
		m_active = a;
	}

	@Override
	public void update(float DT) {

	}

	@Override
	public void Draw(FloatBuffer buffer, int matrixloc) {
		if (m_active == true) {
			m_patch.Draw(buffer, matrixloc);
			// draw text
			// m_font.drawString(m_pos.x, m_pos.y, m_string);
			m_font.Draw(buffer, matrixloc);
		}

	}

	@Override
	public boolean ClickEvent(Vec2f pos) {
		// TODO Auto-generated method stub
		// check inside
		if (m_active == false) {
			return false;
		}
		if (pos.x > m_pos.x && pos.x < m_pos.x + m_size.x) {
			if (pos.y > m_pos.y && pos.y < m_pos.y + m_size.y) {
				m_listener.ButtonCallback(m_ID, pos);
				// return true;
			}
		}

		return false;
	}

	@Override
	public void discard() {
		// TODO Auto-generated method stub
		m_patch.Discard();
		m_font.Discard();

	}

	public void AdjustPos(Vec2f p) {
		m_pos.x += p.x;
		m_pos.y += p.y;
		/*
		 * m_pos.x+=p.x; m_pos.y+=p.y; Vec2f pp=new Vec2f(m_pos.x+0.25F,
		 * m_pos.y+(m_size.y/2)-0.25F);
		 */
		m_font.AdjustPos(p);
		m_patch.AdjustPos(m_pos);
	}
}
