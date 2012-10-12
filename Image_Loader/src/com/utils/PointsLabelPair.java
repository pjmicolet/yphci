package com.utils;

import java.awt.Polygon;
import java.util.ArrayList;
import com.utils.Point;

public class PointsLabelPair {

	private ArrayList<Point> points;
	private Polygon polygon;
	private String label;
<<<<<<< HEAD
	
=======
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
>>>>>>> 0ef14b09658806a00356f2da02d293d134aa773a
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
<<<<<<< HEAD
	public int size() {
		return points.size();
	}
	public Point get(int index) {
		return points.get(index);
	}
}
=======
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

	public void updatePolygon() {
		int npoints = this.points.size();
		int[] xpoints = new int[npoints];
		int[] ypoints = new int[npoints];

		for (int i = 0; i < npoints; i++) {
			xpoints[i] = this.points.get(i).getX();
			ypoints[i] = this.points.get(i).getY();
		}

		this.polygon = new Polygon(xpoints, ypoints, npoints);
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
>>>>>>> 0ef14b09658806a00356f2da02d293d134aa773a
