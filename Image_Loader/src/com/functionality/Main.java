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
		
		MainFrame mainFrame = new MainFrame("C:\\Users\\Yordan\\Desktop\\arthurs_seat_edinburgh.jpg");
		
		//imageFrame.setContentPane(mainPanel);
		//toolsFrame.setContentPane(sidePanel);
		mainFrame.setVisible(true);
	}
}
