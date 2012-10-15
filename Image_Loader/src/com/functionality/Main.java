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
		
<<<<<<< HEAD
		MainFrame mainFrame = new MainFrame("/afs/inf.ed.ac.uk/user/s09/s0939834/hci-practical/images/U1003_0000.jpg");
		
		//imageFrame.setContentPane(mainPanel);
		//toolsFrame.setContentPane(sidePanel);
=======
		String path = "/afs/inf.ed.ac.uk/user/s09/s0939895/Desktop/HCI-cw/yphci/help.jpeg";
		MainFrame mainFrame = new MainFrame(path, labels);
		mainFrame.setVisible(true);
>>>>>>> 2911ad05df610d7862471ac417cc8c276f229ba2
	}
}
