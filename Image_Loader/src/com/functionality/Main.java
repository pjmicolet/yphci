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
		MainFrame mainFrame = new MainFrame("/afs/inf.ed.ac.uk/user/s09/s0939895/Desktop/hci-practical/images/U1003_0000.jpg", labels);
		mainFrame.setVisible(true);

	}
}
