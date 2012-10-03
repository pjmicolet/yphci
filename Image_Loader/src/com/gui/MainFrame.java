package com.gui;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * main window panel
	 */
	JPanel appPanel = null;

	/**
	 * image panel - displays image and editing area
	 */
	JPanel imagePanel = null;

	BufferedImage image = null;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		imagePanel.paint(g); //update image panel
	}

	/**
	 * sets up application window
	 * @param imageFilename image to be loaded for editing
	 * @throws Exception
	 */
	public void setupGUI(String imageFilename) throws Exception {
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		//here we exit the program (maybe we should ask if the user really wants to do it?)
		  		//maybe we also want to store the polygons somewhere? and read them next time
		  		System.out.println("Bye bye!");
		    	System.exit(0);
		  	}
		});

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);

        //Create and set up the image panel.
		imagePanel = new MainPanel(imageFilename);
		imagePanel.setOpaque(true); //content panes must be opaque

        appPanel.add(imagePanel);

		//display all the stuff
		this.pack();
        this.setVisible(true);
	}

	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public MainFrame(String imageFilename) {
		try {
			setupGUI(imageFilename);
		} catch (Exception e) {
			System.err.println("Image: " + imageFilename);
			e.printStackTrace();
		}
	}

}