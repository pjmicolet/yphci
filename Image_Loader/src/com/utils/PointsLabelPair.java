package com.utils;

import java.awt.Polygon;
import java.util.ArrayList;

public class PointsLabelPair {

	private ArrayList<Point> points;
	private Polygon polygon;
	private String label;
	private Point lastPoint;
	
	public PointsLabelPair() {
		this.points = new ArrayList<Point>();
		this.polygon = new Polygon();
	}
	
	public PointsLabelPair(String lbl) {
		this.points = new ArrayList<Point>();
		this.polygon = new Polygon();
		this.label = lbl;
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
		lastPoint = new Point(x, y);
		points.add(lastPoint);
		polygon.addPoint(x, y);
	}
	public Point getLastPoint() {
		return lastPoint;
	}
	public int size() {
		return points.size();
	}	
	public Point get(int index) {
		return points.get(index);
	}
	
	public Polygon getPolygon() {
		return polygon;
	}
	public int getClosest(Point point) {
		for (Point p : points) {
			if (p.distance(point.getX(), point.getY()) <= 5) {
				return points.indexOf(p);
			}
		}
		return 0;
	}

}
