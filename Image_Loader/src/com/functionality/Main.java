package com.functionality;

import javax.swing.SwingUtilities;

import com.gui.MainPanel;
import com.gui.SidePanel;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			
			public void run() {
				// TODO Auto-generated method stub
				SidePanel sp = new SidePanel();
				sp.setVisible(true);
			}
		});
	}

}
