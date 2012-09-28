package com.functionality;

import javax.swing.JFrame;

import com.gui.MainPanel;
import com.gui.SidePanel;


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
		
		MainPanel mainPanel = new MainPanel();
		SidePanel sidePanel = new SidePanel();
		
		imageFrame.setContentPane(mainPanel);
		toolsFrame.setContentPane(sidePanel);
		
		imageFrame.setVisible(true);
		toolsFrame.setVisible(true);
	}
}
