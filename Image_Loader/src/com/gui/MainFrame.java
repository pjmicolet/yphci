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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.HashMap;

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
	private static final String mappingsPathname = "./labels/mappings";

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
	private JMenuItem saveAsLabel = new JMenuItem("Save As Label");
	private JMenuItem undoLabel = new JMenuItem("Undo");
	private JMenuItem redoLabel = new JMenuItem("Redo");

	private MainPanel imageLabeler = new MainPanel();

	private ImageLabels labels;

	private LabelList labelList;

	private String imageFilename;

	private BufferedImage image;
	
	private ImageIcon imageIcon;
	
	private HashMap<String, String> mappings = new HashMap<String, String>();
	
	private boolean saveAs = false;
	
	private String currentPath;

	private Boolean needToSave = false;
	
	
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
		file.add(saveAsLabel);

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

		imageLabeler = new MainPanel(labels, labelList, needToSave);
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

				needToSave = true;
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

				needToSave = true;
			}
		});

		loadLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Wololo");
				loadLabels();
				imageTool.repaint();
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

					needToSave = true;
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
		
		saveLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					saveLabels(false);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		saveAsLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					saveLabels(true);
					needToSave = false;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		saveAsLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					saveAsLabels();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

			private void saveAsLabels() {
				saveLabels(true);
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
		
		if(needToSave){
			int answer = JOptionPane.showConfirmDialog(null, 
					"Do you want to save your labels first?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				//Call save
				try{
					saveLabels(false);
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
			else if (answer == JOptionPane.CANCEL_OPTION){
				//Don't do anything
				return;
			}
		}
		
		FileChooser fc = new FileChooser();
		String path = fc.getPath();
		
		/*
		 * this is to check if we've cancelled getting a new image.
		 */
		if(path.isEmpty()){
			return;
		}
		this.imageFilename = path;
		imageIcon = new ImageIcon(imageFilename);
		Image img = imageIcon.getImage();
		img = img.getScaledInstance(800, 600,  java.awt.Image.SCALE_SMOOTH);
		imageIcon.setImage(img);
     	imageLabel.setIcon(imageIcon);
     	resetLabels();
		loadLabels();
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
	
	/**
	 * Saves a label using serialization magic.
	 * TODO: Get it so that we don't always save under file called labels.
	 * TODO: Change button to Save, instead of Open
	 */
	public void saveLabels(boolean saveAs){
		try {
			FileInputStream map;
			try {
				System.out.println(mappingsPathname);
				map = new FileInputStream(mappingsPathname);
				ObjectInputStream mapOis = new ObjectInputStream(map);
				mappings = (HashMap<String, String>) mapOis.readObject();
				map.close();
			}
			catch (FileNotFoundException exc) {
				createNewMappings();
				map = new FileInputStream(mappingsPathname);
				ObjectInputStream mapOis = new ObjectInputStream(map);
				mappings = (HashMap<String, String>) mapOis.readObject();
				map.close();
			}
			
			
			File input = new File(this.imageFilename);
	        
	        BufferedImage buffImg = ImageIO.read(input);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ImageIO.write(buffImg, "png", outputStream);
	        byte[] data = outputStream.toByteArray();

	        System.out.println("Start MD5 Digest");
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(data);
	        byte[] hash = md.digest();
	        System.out.println(returnHex(hash));
	        
	        String hashString = returnHex(hash);
	        String labelsPathname;

	        if (saveAs || !mappings.containsKey(hashString) ) {
	        	FileChooser fc = new FileChooser();
				labelsPathname = fc.getPath();
				if (labelsPathname.isEmpty()) {
					return;
				}
				this.currentPath = labelsPathname;
			}
	        else {
	        	labelsPathname = this.currentPath;
	        }
	        mappings.put(hashString, labelsPathname);
	        System.out.println(mappings.keySet());
//	        if (mappings.containsKey(hashString)) {
//		        mappings.  labelsPathname = mappings.get(hashString);
//	        }
//	        else {
//	        	labelsPathname = "";
//	        }
//	        

			FileOutputStream labelsFos = new FileOutputStream(labelsPathname);
			ObjectOutputStream labelsOos = new ObjectOutputStream(labelsFos);
			labelsOos.writeObject(labels);
			labelsOos.flush();
			labelsOos.close();
			
			FileOutputStream mappingsFos = new FileOutputStream(mappingsPathname);
			ObjectOutputStream mappingsOos = new ObjectOutputStream(mappingsFos);
			mappingsOos.writeObject(mappings);
			mappingsOos.flush();
			mappingsOos.close();
			
			System.out.println("Labels file saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) { //for loop ID:1
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                   // Belongs to for loop ID:1
    return hexString;
  }                                         // Belongs to returnHex class
	/**
	 * Loads the labels and makes sure everything is nice and clean...ish
	 */
	public void loadLabels(){
		try{
			FileInputStream map;
			try {
				map = new FileInputStream(mappingsPathname);
				ObjectInputStream mapOis = new ObjectInputStream(map);
				mappings = (HashMap<String, String>) mapOis.readObject();
			}
			catch (FileNotFoundException exc) {
				createNewMappings();
				map = new FileInputStream(mappingsPathname);
				ObjectInputStream mapOis = new ObjectInputStream(map);
				mappings = (HashMap<String, String>) mapOis.readObject();
			}
			
			
			File input = new File(this.imageFilename);
	        
	        BufferedImage buffImg = ImageIO.read(input);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ImageIO.write(buffImg, "png", outputStream);
	        byte[] data = outputStream.toByteArray();

	        System.out.println("Start MD5 Digest");
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(data);
	        byte[] hash = md.digest();

	        String hashString = returnHex(hash);
	        String labelsPathname;
	        if (mappings.containsKey(hashString)) {
		        labelsPathname = mappings.get(hashString);
	        }
	        else {
	        	labelsPathname = "";
	        }
 
	        FileInputStream fis;
	        try {
	        	fis = new FileInputStream(labelsPathname);
	        	int answer = JOptionPane.showConfirmDialog(null, 
						"We have detected a labels file for this image. Do you want to load it?", "Warning", JOptionPane.YES_NO_OPTION);
			    if (answer == JOptionPane.YES_OPTION) {
			    	ObjectInputStream ois = new ObjectInputStream(fis);
					labels = (ImageLabels) ois.readObject();
					ois.close();
			    }
			    else {
			    	FileChooser fc = new FileChooser();
					labelsPathname = fc.getPath();
					this.imageFilename = fc.getPath();
		        	try {
		        		fis = new FileInputStream(labelsPathname);
		        		ObjectInputStream ois = new ObjectInputStream(fis);
						labels = (ImageLabels) ois.readObject();
						ois.close();
					} // TEST FOR WRONG LABEL FILES
		        	catch (FileNotFoundException e1) {
		        		System.out.println("There was a problem loading your labels file. Creating a new one.." );
		        	}
			    }
	        }
	        catch (FileNotFoundException e) {
        		System.out.println("Creating a new labels file .." );
        		labels = new ImageLabels();
			}
	        
	        this.currentPath = labelsPathname;

			imageLabeler.setLabels(labels);
			labelList.deleteAllElements();
			labelList.addAllElements(labels);
			imageLabeler.setLabelsList(labelList);
			System.out.println("File loaded");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


	private void createNewMappings() {
		try {
			FileOutputStream map = new FileOutputStream(mappingsPathname);
			ObjectOutputStream mapOis = new ObjectOutputStream(map);
			mapOis.writeObject(mappings);
			mapOis.flush();
			mapOis.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}