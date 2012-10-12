package com.functionality;

import com.gui.MainFrame;
import com.utils.ImageLabels;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		
		MainFrame mainFrame = new MainFrame("C:\\Users\\Yordan\\Desktop\\arthurs_seat_edinburgh.jpg");
		
		//imageFrame.setContentPane(mainPanel);
		//toolsFrame.setContentPane(sidePanel);
=======
		ImageLabels labels = new ImageLabels();
		String path = "/Users/paul-julesmicolet/Pictures/4chanfile/1341465359748.png";
		MainFrame mainFrame = new MainFrame(path, labels);
>>>>>>> 0ef14b09658806a00356f2da02d293d134aa773a
		mainFrame.setVisible(true);
	}
}
