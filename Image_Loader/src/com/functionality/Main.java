package com.functionality;

import javax.swing.JFrame;

import com.gui.MainFrame;

public class Main {

	private static JFrame toolsFrame;
	private static JFrame imageFrame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainFrame mainFrame = new MainFrame("/Users/paul-julesmicolet/Pictures/4chanfile/1348777794239.jpeg");
		
		//imageFrame.setContentPane(mainPanel);
		//toolsFrame.setContentPane(sidePanel);
		mainFrame.setVisible(true);
	}
}
