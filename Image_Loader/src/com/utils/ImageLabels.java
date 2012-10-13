package com.utils;

import java.util.ArrayList;

public class ImageLabels {


	private ArrayList<PointsLabelPair> pointsAndLabels;

	private PointsLabelPair currentLabel;

	private boolean currentLabelFlag;

	public ImageLabels() {
		pointsAndLabels = new ArrayList<PointsLabelPair>();
		currentLabel = new PointsLabelPair();
		currentLabelFlag = true;
	}

	public ImageLabels(ArrayList<PointsLabelPair> points) {
		pointsAndLabels = points;
		currentLabel = new PointsLabelPair();
		currentLabelFlag = true;
	}

	public ImageLabels(String fileName) {
		pointsAndLabels = parseXMLFile(fileName);
	}

	private ArrayList<PointsLabelPair> parseXMLFile(String fileName) {
		// TODO Complete method that parses an XML file and populates
		// the ArrayList with labels and the corresponding points for each of them.
		return null;
	}

	public PointsLabelPair getCurrentLabel() {
		if (currentLabelFlag) 
			return currentLabel;
		else { 
			currentLabel = new PointsLabelPair();
			currentLabelFlag = true;
			return currentLabel;
		}
	}

	public ArrayList<PointsLabelPair> getPoints() {
		return pointsAndLabels;
	}

	public void updateCurrentLabel(PointsLabelPair currentLabel2) {
		this.currentLabel = currentLabel2;
		this.currentLabel.updatePolygon();
	}

	public void closeCurrentLabel() {
		pointsAndLabels.add(currentLabel);
		currentLabelFlag = false;
	}
	
	public void removeLabel(int index){
		pointsAndLabels.remove(index);
	}

	public PointsLabelPair getPoint(Point clickedPoint) {
		int x = clickedPoint.getX();
		int y = clickedPoint.getY();
		for (PointsLabelPair pair : pointsAndLabels) {
			ArrayList<Point> pairPoints = pair.getPoints();

			for (Point p : pairPoints) {
				int pointX = p.getX();
				int pointY = p.getY();

				if (pointX == x && pointY == y) {
					return pair;
				}
			}

		}
		return null;
	}
	
	public int size(){
		return pointsAndLabels.size();
	}
	

}