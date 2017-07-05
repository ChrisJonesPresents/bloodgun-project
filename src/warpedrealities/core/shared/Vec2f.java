package warpedrealities.core.shared;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Vec2f {

	public float x;
	public float y;

	public Vec2f(float xin, float yin) {
		x = xin;
		y = yin;
	}

	public Vec2f(DataInputStream stream) {
		// TODO Auto-generated constructor stub
		try {
			x = stream.readFloat();
			y = stream.readFloat();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Vec2f(double xd, double yd) {
		x = (float) xd;
		y = (float) yd;
	}

	public void Save(DataOutputStream stream) throws IOException {
		stream.writeFloat(x);
		stream.writeFloat(y);
	}

	public float Distance(Vec2f v) {
		float cx = Math.abs(v.x - x);
		float cy = Math.abs(v.y - y);
		float sum = (cx * cx) + (cy * cy);

		return (float) Math.sqrt(sum);
	}

	public float getDistance(Vec2f p) {
		return getDistance(p.x, p.y);
	}

	public float getDistance(float x0, float y0) {
		float xe = Math.abs(x - x0);
		float ye = Math.abs(y - y0);
		xe = xe * xe;
		ye = ye * ye;
		float sqrt = (float) Math.sqrt(xe + ye);
		return sqrt;
	}

	public float length() {
		float xe = Math.abs(x);
		float ye = Math.abs(y);
		xe = xe * xe;
		ye = ye * ye;
		return (float) Math.sqrt(xe + ye);
	}

	public void modLength(float l) {
		float length = length();
		float ratio = (length+l)/length;
		x = x * ratio;
		y = y * ratio;		
		
	}	
	public void setLength(float l) {
		float length = length();
		float ratio = l / length;
		x = x * ratio;
		y = y * ratio;		
	}	
	
	public void normalize() {
		float length = length();
		float ratio = 1 / length;
		x = x * ratio;
		y = y * ratio;
	}

	public float getDistanceManHattan(float x0, float y0) {
		float xe = Math.abs(x - x);
		float ye = Math.abs(y - y);
		return xe + ye;
	}

	public Vec2f getVector(Double angle) {
		double x = (this.x * Math.cos(angle)) + (this.y * Math.sin(angle));
		double y = (-this.x * Math.sin(angle)) + (this.y * Math.cos(angle));

		return new Vec2f(x, y);
	}
	public void rotate(float r) {
		double x0 = (x * Math.cos(r)) + (y * Math.sin(r));
		double y0 = (x * -Math.sin(r)) + (y * Math.cos(r));
		x = (float) x0;
		y = (float) y0;
	}

	public Vec2f add(Vec2f a) {
		x+=a.x;
		y+=a.y;
		return this;
	}

	public Vec2f replicate() 
	{
		return new Vec2f(x,y);
	}

	public Vec2f subtract(Vec2f p) {
		x-=p.x;
		y-=p.y;
		return this;
	}

}
