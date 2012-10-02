package com.functionality;

import javax.swing.JFrame;

import com.gui.MainFrame;
import com.gui.SideFrame;


public class Main {

	private static JFrame toolsFrame;
	private static JFrame imageFrame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		toolsFrame = new JFrame("Tools");
		imageFrame = new JFrame("Image");
		
		toolsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainFrame mainFrame = new MainFrame("/afs/inf.ed.ac.uk/user/s09/s0939895/Desktop/hci-practical/images/U1003_0000.jpg");
		SideFrame sideFrame = new SideFrame();
		
		//imageFrame.setContentPane(mainPanel);
		//toolsFrame.setContentPane(sidePanel);
		
		mainFrame.setVisible(true);
		sideFrame.setVisible(true);
	}
}
