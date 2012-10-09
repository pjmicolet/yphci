package com.utils;
import java.lang.Math;

/**
 * simple class for handling points
 * @author Michal
 *
 */
public class Point {
	private int x = 0;
	private int y = 0;
	
	public Point() {
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int distance(int x1, int y1){
	return (int) Math.sqrt(((x-x1)*(x-x1) + (y-y1)*(y-y1)));
	}
}
