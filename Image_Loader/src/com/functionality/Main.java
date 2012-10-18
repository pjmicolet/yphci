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

		String path = "./res/help.jpeg";
		MainFrame mainFrame = new MainFrame(path, labels);
		mainFrame.setVisible(true);
	}
}
