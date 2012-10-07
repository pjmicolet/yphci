package com.utils;

import java.util.ArrayList;

public class ImageLabels {


	private ArrayList<PointsLabelPair> pointsAndLabels;
	
	private PointsLabelPair currentLabel;

	public ImageLabels() {
		pointsAndLabels = new ArrayList<PointsLabelPair>();
		currentLabel = new PointsLabelPair();
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
		return currentLabel;
	}

	public ArrayList<PointsLabelPair> getPoints() {
		return pointsAndLabels;
	}

	public void updateCurrentLabel(PointsLabelPair currentLabel2) {
		this.currentLabel = currentLabel2;
	}
	
}
