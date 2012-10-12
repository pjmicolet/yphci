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
		FileChooser fc = new FileChooser();
		String path = fc.getPath();
=======
		
		String path = "/afs/inf.ed.ac.uk/user/s09/s0939895/Desktop/HCI-cw/yphci/help.jpeg";
>>>>>>> 27afdded557ee1cf083b4875af696717672f019e
		MainFrame mainFrame = new MainFrame(path, labels);
		mainFrame.setVisible(true);
	}
}
