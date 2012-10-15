package com.utils;

import java.util.ArrayList;

public class PolygonFunctions {
	/**
	 * list of current polygon's vertices 
	 */
	private ArrayList<Point> currentPolygon = null;
	
	/**
	 * list of polygons
	 */
	private ArrayList<ArrayList<Point>> polygonsList = null;

	public ArrayList<Point> getCurrentPolygon() {
		return currentPolygon;
	}

	public void setCurrentPolygon(ArrayList<Point> currentPolygon) {
		this.currentPolygon = currentPolygon;
	}

	public ArrayList<ArrayList<Point>> getPolygonsList() {
		return polygonsList;
	}

	public void setPolygonsList(ArrayList<ArrayList<Point>> polygonsList) {
		this.polygonsList = polygonsList;
	}
	
	
}
