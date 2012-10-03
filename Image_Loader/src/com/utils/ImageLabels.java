package com.utils;

import java.util.ArrayList;

public class ImageLabels {


	private ArrayList<PointsLabelPair> pointsAndLabels = null;
	
	private PointsLabelPair currentLabel = null;

	public Point startPoint = null;
	public Point finalPoint = null;
	
	public ImageLabels() {
		pointsAndLabels = new ArrayList<PointsLabelPair>();
	}
	
	public ImageLabels(String fileName) {
		pointsAndLabels = parseXMLFile(fileName);
	}

	private ArrayList<PointsLabelPair> parseXMLFile(String fileName) {
		// TODO Complete method that parses an XML file and populates
		// the ArrayList with labels and the corresponding points for each of them.
		return null;
	}
	
	public void addNewLabel() {
		if (currentLabel != null) {
			finishCurrentLabel(currentLabel);
			pointsAndLabels.add(currentLabel);
		}
		
		currentLabel = new PointsLabelPair();
	}

	public void finishCurrentLabel(PointsLabelPair currentLabel) {
		if (currentLabel.size() >= 3) {
			Point startPoint = currentLabel.get(0);
			Point finalPoint = currentLabel.get(currentLabel.size() - 1);
		}
	}
	
	public PointsLabelPair getCurrentLabel() {
		return this.currentLabel;
	}
}
