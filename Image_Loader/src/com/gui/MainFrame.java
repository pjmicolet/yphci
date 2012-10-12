package com.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.utils.ImageLabels;
import com.utils.PointsLabelPair;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	JPanel appPanel = new JPanel();

	MainPanel imagePanel = new MainPanel();

	JPanel toolPanel = new JPanel();
	JPanel secondPanel = new JPanel();

	String imageFilename;

	BufferedImage image = null;

	private JButton newLabel = new JButton("New Label");
	private JButton editLabel = new JButton("Edit Label");
	private JButton deleteLabel = new JButton("Delete Label");
	
	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");
	private JMenu edit = new JMenu("Edit");
	
	private LabelList labelList;
	
	private JMenuItem newImage = new JMenuItem("New Image");
	private JMenuItem loadLabel = new JMenuItem("Load Label");
	private JMenuItem saveLabel = new JMenuItem("Save Label");

	private JMenuItem undoLabel = new JMenuItem("Undo");
	private JMenuItem redoLabel = new JMenuItem("Redo");
	
	private ImageLabels labels;

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

		  	public void windowActivated(WindowEvent event){
		  		System.out.println("Activate");
		  	}
		});
		
		//Sets up the buttons.
		labelList = new LabelList(this.labels);
		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 1) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		            Object o = theList.getModel().getElementAt(index);
		            labelList.setIsSelected(true);
		            labelList.setSelectedIndex(index);
		            imagePanel.paint(imagePanel.getGraphics());
		            System.out.println("Clicked on: " + index);
		          }
		        }
		      }
		    };
		ListSelectionListener selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
		        JList theList = (JList) listEvent.getSource();
		        //if (listEvent.getClickCount() == 1) {
		          //int index = theList.locationToIndex(listEvent.);
		          	int index = theList.getSelectedIndex();
		          	System.out.println("index:" + index);
		        	if (index >= 0) {
		            Object o = theList.getModel().getElementAt(index);
		            labelList.setIsSelected(true);
		            labelList.setSelectedIndex(index);
		            imagePanel.paint(imagePanel.getGraphics());
		            System.out.println("Clicked on: " + index);
		          //}
		        }				
				
			}
			
		};
		this.labelList.getJList().addListSelectionListener(selectionListener);
//		this.labelList.getJList().addMouseListener(mouseListener);
		
		toolPanel.setLayout(new GridLayout(0,2));
		toolPanel.add(newLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(editLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(deleteLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(labelList);
		
		secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));
		secondPanel.add(toolPanel);
		secondPanel.add(labelList);

		newLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				finishLabel();
			}
		});
		
		menuBar.add(file);
		menuBar.add(edit);
		
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
		
		edit.add(undoLabel);
		edit.add(redoLabel);
		
		newImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					loadNewImage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
        //Create and set up the image panel.

		imagePanel = new MainPanel(imageFilename, labels, labelList);
		imagePanel.setOpaque(true); //content panes must be opaque

		appPanel.add(secondPanel);
		appPanel.add(imagePanel);
		this.setJMenuBar(menuBar);

		//display all the stuff
		this.pack();
	}


	@Override
	public void paint(Graphics g){
		//super.paint(g);
		super.paintComponents(g);
		imagePanel.paintComponents(imagePanel.getGraphics());
		
		//TODO: what is this ?
//		if (labels.getPoints().size() != 0) {
//			for (PointsLabelPair label : labels.getPoints()) {
//				//labelsList.add
//			}
//		}
	}
	
	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public MainFrame(String imageFilename, ImageLabels labels) {
		try {
			this.labels = labels;
			this.imageFilename = imageFilename;
			setupGUI(imageFilename);
		} catch (Exception e) {
			System.err.println("Image: " + imageFilename);
			e.printStackTrace();
		}
	}
	
	public void finishLabel(){
		imagePanel.finishLabel(true);
	}
	
	public void loadNewImage() throws Exception{
		FileChooser fc = new FileChooser();
		labels = new ImageLabels();
		imagePanel.resetImage(fc.getPath(), labels);
		imagePanel.repaint();
	}

}