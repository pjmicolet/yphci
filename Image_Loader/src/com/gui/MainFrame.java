package com.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.undo.UndoManager;

import com.utils.ImageLabels;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel appPanel = new JPanel();
	private JPanel toolPanel = new JPanel();
	private JPanel labelTools = new JPanel();
	private JPanel imagePanel = new JPanel();
	private JPanel imageTool = new JPanel();

	private JLabel imageLabel = new JLabel();

	private JButton newLabel = new JButton("New Label");
	private JButton editLabel = new JButton("Edit Label");
	private JButton deleteLabel = new JButton("Delete Label");

	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");
	private JMenu edit = new JMenu("Edit");

	private JMenuItem newImage = new JMenuItem("New Image");
	private JMenuItem loadLabel = new JMenuItem("Load Label");
	private JMenuItem saveLabel = new JMenuItem("Save Label");
	private JMenuItem undoLabel = new JMenuItem("Undo");
	private JMenuItem redoLabel = new JMenuItem("Redo");

	private MainPanel imageLabeler = new MainPanel();

	private ImageLabels labels;

	private LabelList labelList;

	private String imageFilename;

	private BufferedImage image;
	
	private ImageIcon imageIcon;

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


	//TODO: Make this pretty.
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
		  	}
		});

		//Get the image and scale it.
		getImage();

		labelList = new LabelList(this.labels);

		menuBar.add(file);
		menuBar.add(edit);

		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);

		edit.add(undoLabel);
		edit.add(redoLabel);

		imagePanel.add(imageLabel);
		imagePanel.setOpaque(true);	

		/*
		 * We add all the buttons to the toolPanel
		 */
		toolPanel.setLayout(new GridLayout(0,2));
		toolPanel.add(newLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(editLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(deleteLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(labelList);

		/*
		 * This is is the panel that contains the list of labels and the buttons.
		 */
		labelTools.setLayout(new BoxLayout(labelTools, BoxLayout.Y_AXIS));
		labelTools.add(toolPanel);
		labelTools.add(labelList);

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
       
		//Create and set up the image panel.

		imageLabeler = new MainPanel(labels, labelList);
		imageLabeler.setOpaque(false); //content panes must be opaque
		imageTool.setLayout(new BorderLayout());

		imageTool.add(imageLabeler, BorderLayout.CENTER);
		imageTool.add(imagePanel, BorderLayout.CENTER);

		appPanel.add(labelTools);
		appPanel.add(imageTool);
		this.setJMenuBar(menuBar);

		//display all the stuff
		this.pack();	

		ListSelectionListener selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
		        JList theList = (JList) listEvent.getSource();
		          	int index = theList.getSelectedIndex();
		        	if (index >= 0) {
		            labelList.setIsSelected(true);
		            labelList.setSelectedIndex(index);
		            imageLabeler.repaint();
		        }				

			}

		};
		this.labelList.getJList().addListSelectionListener(selectionListener);


		newLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labels.getCurrentLabel().size() == 0) {
					JOptionPane.showMessageDialog(null, 
							"You can start drawing a new label by clicking on the image", "New Label", JOptionPane.OK_CANCEL_OPTION);					
				}
				else if (labels.getCurrentLabel().size() < 3) {
					JOptionPane.showMessageDialog(null, 
							"You need to draw at least 3 points to finish a label", "New Label", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					finishLabel();
				}
			}
		});

		editLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (labelList.getIsSelected()) {
					String newName = JOptionPane.showInputDialog(null, 
							"Enter a new name for this label", "Edit Label", 1);
					if (!newName.isEmpty()) {
						int index = labelList.getSelectedIndex();
						labels.getPoints().get(index).setLabel(newName);
						labelList.updateName(newName);
						JOptionPane.showMessageDialog(null, 
								"Name was successfully changed!", "Edit Label", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if (labels.getCurrentLabel().size() < 3) {
					JOptionPane.showMessageDialog(null, 
							"You need to draw at least 3 points to finish a label", "New Label", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					finishLabel();
				}
			}
		});

		deleteLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (labelList.getIsSelected()){
					int index = labelList.getSelectedIndex();
					System.out.println(index);
					labelList.deleteElement(index);
					labels.removeLabel(index);
					labelList.setIsSelected(false);
				}

				imageTool.repaint();
			}
		});

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
		
		undoLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UndoManager undo = new UndoManager();
				undo.undo();
			}
		});
	}


	@Override
	public void paint(Graphics g){
		//super.paint(g);
		super.paint(g);
		imageTool.paint(imageTool.getGraphics());
	}

	/**
	 * Finishes the current label.
	 */
	public void finishLabel(){
		imageLabeler.finishLabel(true);
	}

	/**
	 * Starts a FileChooser window to choose a new image and then sets that image as the
	 * current JLabel.
	 * @throws Exception
	 */
	public void loadNewImage() throws Exception{
		FileChooser fc = new FileChooser();
		this.imageFilename = fc.getPath();
		imageIcon = new ImageIcon(imageFilename);
		Image img = imageIcon.getImage();
		img = img.getScaledInstance(800, 600,  java.awt.Image.SCALE_SMOOTH);
		imageIcon.setImage(img);
     	imageLabel.setIcon(imageIcon);
		resetLabels();
		imageTool.repaint();
		
		
	}

	/**
	 * Uses the image path to set the image, rescale it and put it in a JLabel.
	 * @throws Exception
	 */
	public void getImage() throws Exception{

		this.image = ImageIO.read(new File(imageFilename));

		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}
		this.imageLabel = new JLabel(new ImageIcon( this.image ));
	}
	
	/**
	 * Resets the current labels and deletes all elements within the labelList
	 * @throws Exception
	 */
	public void resetLabels() throws Exception{
		this.labels = new ImageLabels();
		imageLabeler.setLabels(labels);
		this.labelList.deleteAllElements();
		imageLabeler.setLabelsList(labelList);
	}
}