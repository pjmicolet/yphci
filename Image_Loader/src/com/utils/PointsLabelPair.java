package com.utils;

import java.util.ArrayList;

public class PointsLabelPair {

	private ArrayList<Point> points;
	private String label;
	
	public PointsLabelPair() {
		this.points = new ArrayList<Point>();
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void addPoint(int x, int y) {
		Point p = new Point(x, y);
		points.add(p);
	}
	public Point getLastPoint() {
		return points.get(points.size()-1);
	}
	public int size() {
		return points.size();
	}	
	public Point get(int index) {
		return points.get(index);
	}

}
