package com.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;




public class MainFrame extends JFrame {

	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");

	private JMenuItem newImage = new JMenuItem("New Image");
	private JMenuItem loadLabel = new JMenuItem("Load Label");
	private JMenuItem saveLabel = new JMenuItem("Save Label");

	private JButton newLabel = new JButton("New Label");
	private JButton editLabel = new JButton("Edit Label");
	private JButton deleteLabel = new JButton("Delete Label");
	private JButton undo = new JButton("Undo");
	private JButton redo = new JButton("Redo");
	
	private JPanel sidePanel = new JPanel();
	private JPanel undoPanel = new JPanel();
	private JPanel overallPanel = new JPanel();
	
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

//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		imagePanel.paint(g); //update image panel
//
//	}

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

		//setup main window panel

		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
		
        //Create and set up the image panel.
		imagePanel = new MainPanel(imageFilename);
		imagePanel.setOpaque(true); //content panes must be opaque

		
        initSidePanel();
        initMenuBar();
        

        appPanel.add(overallPanel);
        appPanel.add(imagePanel);
        
        setJMenuBar(menuBar);
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
	

	public void initSidePanel()
	{
		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		newLabel.setToolTipText("Click to add new object");

		sidePanel.add(newLabel);
		sidePanel.add(editLabel);
		sidePanel.add(deleteLabel);

		undoPanel.setLayout(new BoxLayout(undoPanel, BoxLayout.X_AXIS));
		undoPanel.add(undo);
		undoPanel.add(redo);

		overallPanel.setLayout(new BorderLayout());
		overallPanel.add(sidePanel, BorderLayout.WEST);
		overallPanel.add(undoPanel, BorderLayout.SOUTH);
	}
	
	public void initMenuBar()
	{

		menuBar.add(file);
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
		
		
	}
	
	

}
