package com.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel appPanel = new JPanel();

	JPanel imagePanel = new JPanel();
	
	JPanel toolPanel = new JPanel();

	BufferedImage image = null;
	
	private JButton newLabel = new JButton("New Label");
	private JButton editLabel = new JButton("Edit Label");
	private JButton deleteLabel = new JButton("Delete Label");
	private JButton undo = new JButton("Undo");
	private JButton redo = new JButton("Redo");

	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu file = new JMenu("File");
	
	private JMenuItem newImage = new JMenuItem("New Image");
	private JMenuItem loadLabel = new JMenuItem("Load Label");
	private JMenuItem saveLabel = new JMenuItem("Save Label");


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
		  		System.out.println("Exiting...");
		    	System.exit(0);
		  	}
		});

		//Sets up the buttons.
		
		toolPanel.setLayout(new GridLayout(0,2));
		toolPanel.add(newLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(editLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(deleteLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(undo);
		toolPanel.add(redo);	

		menuBar.add(file);
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
				
		this.setJMenuBar(menuBar);
		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BorderLayout());
		this.setContentPane(appPanel);

        //Create and set up the image panel.
		imagePanel = new MainPanel(imageFilename);
		imagePanel.setOpaque(true); //content panes must be opaque

		appPanel.add(toolPanel, BorderLayout.WEST);
		appPanel.add(imagePanel, BorderLayout.CENTER);

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
