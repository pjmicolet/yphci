package com.functionality;

import com.gui.MainFrame;
import com.utils.ImageLabels;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageLabels labels = new ImageLabels();
		MainFrame mainFrame = new MainFrame("/Users/paul-julesmicolet/Pictures/4chanfile/1347745104415.png", labels);
		mainFrame.setVisible(true);
	}
}
