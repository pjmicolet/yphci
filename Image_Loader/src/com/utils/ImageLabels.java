package com.utils;

import java.util.ArrayList;

public class ImageLabels {


	private ArrayList<PointsLabelPair> pointsAndLabels = null;
	
	private PointsLabelPair currentLabel = null;

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
	
	//public void 
}
