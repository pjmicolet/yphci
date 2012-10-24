package com.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.utils.ImageLabels;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final String mappingsPathname = "./labels/mappings";
	private static final String labelsFolder = "./labels";
	
	private String currentImagePath = "./";

	private JPanel appPanel = new JPanel();
	private JPanel toolPanel = new JPanel();
	private JPanel labelTools = new JPanel();
	private JPanel imagePanel = new JPanel();
	private JPanel imageTool = new JPanel();
	private JPanel nextBackImage = new JPanel();

	private JLabel imageLabel = new JLabel();

	private JButton newLabel = new JButton("(N)ew Label", new ImageIcon("./res/newlabel.gif"));
	private JButton editLabel = new JButton("(E)dit Label",new ImageIcon("./res/edit.gif"));
	private JButton deleteLabel = new JButton("(D)elete Label",new ImageIcon("./res/delete.gif"));
	private JButton deselectLabel = new JButton("Desele(c)t Label",new ImageIcon("./res/deselect.gif"));
	private JButton nextImages = new JButton("Ne(x)t Images", new ImageIcon("./res/next.gif"));
	private JButton previousImages = new JButton("(P)revious Images", new ImageIcon("./res/previous.gif"));

	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");
	private JMenu edit = new JMenu("Edit");

	private JMenuItem newImage = new JMenuItem("New Image",new ImageIcon("./res/new.gif"));
	private JMenuItem loadLabel = new JMenuItem("Load Label", new ImageIcon("./res/load.gif"));
	private JMenuItem saveLabel = new JMenuItem("Save Label", new ImageIcon("./res/save.gif"));
	private JMenuItem saveAsLabel = new JMenuItem("Save As Label", new ImageIcon("./res/save.gif"));

	private MainPanel imageLabeler = new MainPanel();
	
	private JButton labelColorChooser = new JButton("(L)abel colour", new ImageIcon("./res/colour.gif"));
	private JButton insideColorChooser = new JButton("(H)ighlight colour", new ImageIcon("./res/colour.gif"));

	private ImageLabels labels;
	
	private ImageList imageNav;

	private LabelList labelList;

	private String imageFilename;

	private BufferedImage image;
	
	private ImageIcon imageIcon;
	
	private HashMap<String, String> mappings = new HashMap<String, String>();
	
	private String currentPath = "";

	private boolean needToSave = false;
	
	private int lastClickedIndex = -1;
	
	
	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public MainFrame(String imageFilename, ImageLabels labels) {
		try {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		//Get the image and scale it.
		getImage();

		labelList = new LabelList(this.labels);
		

		imageNav = new ImageList(currentImagePath);

		menuBar.add(file);
		
		newImage.setMnemonic(KeyEvent.VK_I);
		newImage.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		loadLabel.setMnemonic(KeyEvent.VK_L);
		loadLabel.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		saveLabel.setMnemonic(KeyEvent.VK_S);
		saveLabel.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAsLabel.setMnemonic(KeyEvent.VK_A);
		saveAsLabel.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_A, ActionEvent.CTRL_MASK));
//		newImage.getAccessibleContext().setAccessibleDescription(
//		        "This doesn't really do anything");
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
		file.add(saveAsLabel);

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
		toolPanel.add(deselectLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(labelList);
		
		nextBackImage.setLayout(new GridLayout(0,2));
		nextBackImage.add(previousImages);
		nextBackImage.add(nextImages);
		nextBackImage.add(labelColorChooser);
		nextBackImage.add(insideColorChooser);
		
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new BorderLayout());
		intermediatePanel.add(labelList, BorderLayout.WEST);
		
		JPanel intermediatePanel2 = new JPanel();
		intermediatePanel2.setLayout(new BorderLayout());
		intermediatePanel2.add(imageNav, BorderLayout.WEST);
		/*
		 * This is is the panel that contains the list of labels and the buttons.
		 */
		labelTools.setLayout(new BoxLayout(labelTools, BoxLayout.Y_AXIS));
		labelTools.add(toolPanel);
		labelTools.add(intermediatePanel);
		labelTools.add(intermediatePanel2);
		labelTools.add(nextBackImage);


		

		//setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
       
		//Create and set up the image panel.

		imageLabeler = new MainPanel(labels, labelList, this.needToSave);
		imageLabeler.setOpaque(false); //content panes must be opaque
		imageTool.setLayout(new BorderLayout());

		imageTool.add(imageLabeler, BorderLayout.CENTER);
		imageTool.add(imagePanel, BorderLayout.CENTER);

		appPanel.add(labelTools);
		appPanel.add(imageTool);
		this.setJMenuBar(menuBar);
		
		
		this.setResizable(false);
		//display all the stuff
		this.pack();	

		ListSelectionListener selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
		        JList theList = (JList) listEvent.getSource();
		          	int index = theList.getSelectedIndex();
		          	System.out.println("index: " + index);
//		        	if (index >= 0) {
//		        		if((lastClickedIndex == index && index != -1)){
//
//		        			labelList.deselect();
//		        			labelList.setIsSelected(false);
//		        			lastClickedIndex = -1;
//		        			imageLabeler.repaint();
//		        		}
//		        		else
//		        		{
//		        			lastClickedIndex = index;
		        			labelList.setIsSelected(true);
		        			labelList.setSelectedIndex(index);
		        			imageLabeler.repaint();
//		        		}
//		        }				

			}
		};
		
		this.labelList.getJList().addListSelectionListener(selectionListener);

		ListSelectionListener imageSelection = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
				JList theList = (JList) listEvent.getSource();
				int index = theList.getSelectedIndex();
				if(index >= 0){
					String[] paths = imageNav.getPaths();
					try {
						/*
						 * This is a quick load so set quickLoad to true and feed it the path.
						 */
						loadNewImage(true, paths[index]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		this.imageNav.getJList().addListSelectionListener(imageSelection);

		AbstractAction newLabelAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
					needToSave = true;
				}

			}
		};
		
		AbstractAction nextImagesAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				imageNav.nextImages();
				imageTool.repaint();
			}
		};
		
		AbstractAction previousImagesAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				imageNav.previousImages();
				imageTool.repaint();
			}
		};
		
		AbstractAction labelColorAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeLabelColor();
			}
		};
		
		AbstractAction highlightColorAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeInsideColor();
			}
		};
		
		newLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('n'), "new label");
		newLabel.getActionMap().put("new label", newLabelAction);
		newLabel.addActionListener(newLabelAction);
		
		
		nextImages.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('x'), "next images");
		nextImages.getActionMap().put("next images", nextImagesAction);
		nextImages.addActionListener(nextImagesAction);
		
		previousImages.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('p'), "previous images");
		previousImages.getActionMap().put("previous images", previousImagesAction);
		previousImages.addActionListener(previousImagesAction);
		
		labelColorChooser.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('l'), "label colour");
		labelColorChooser.getActionMap().put("label colour", labelColorAction);
		labelColorChooser.addActionListener(labelColorAction);
		
		insideColorChooser.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('h'), "highlight colour");
		insideColorChooser.getActionMap().put("highlight colour", highlightColorAction);
		insideColorChooser.addActionListener(highlightColorAction);

	AbstractAction editAction = new AbstractAction() {
			
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (labelList.getIsSelected()) {
					String newName = "";
					newName = JOptionPane.showInputDialog(null, 
							"Enter a new name for this label", "Edit Label", 1);
					if (newName != null) {
						if(!newName.isEmpty()){
							int index = labelList.getSelectedIndex();
							labels.getPoints().get(index).setLabel(newName);
							labelList.updateName(newName);
							JOptionPane.showMessageDialog(null, 
									"Name was successfully changed!", "Edit Label", JOptionPane.INFORMATION_MESSAGE);
						}
					}

					needToSave = true;	
				}
				else {
					JOptionPane.showMessageDialog(null, 
							"Please select a label!", "Edit Label", JOptionPane.INFORMATION_MESSAGE);
					}
			
			}
		};	

		editLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('e'), "edit label");
		editLabel.getActionMap().put("edit label", editAction);
		editLabel.addActionListener(editAction);
		

		loadLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Wololo");
				loadLabels();
				imageTool.repaint();
			}
		});
		
		AbstractAction deleteAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (labelList.getIsSelected()){
					int index = labelList.getSelectedIndex();
					System.out.println(index);
					labelList.deleteElement(index);
					labels.removeLabel(index);
					labelList.setIsSelected(false);

					needToSave = true;
				}
				else{
					JOptionPane.showMessageDialog(null, 
							"Please select a label!", "Delete Label", JOptionPane.INFORMATION_MESSAGE);
				}

				imageTool.repaint();
			}
		};
		
			AbstractAction deselectAction = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				labelList.deselect();
				imageTool.repaint();
			}
		};
		
		deleteLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "delete label");
		deleteLabel.getActionMap().put("delete label", deleteAction);
		deleteLabel.addActionListener(deleteAction);
		
		deselectLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('c'), "deselect label");
		deselectLabel.getActionMap().put("deselect label", deselectAction);
		deselectLabel.addActionListener(deselectAction);
		
		
		nextImages.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imageNav.nextImages();
				imageTool.repaint();
			}
		});

		previousImages.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
					imageNav.previousImages();
					imageTool.repaint();
				
			}
		});
		
		newImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					//Tell load new image this isn't a quick load and give it an empty string as getting the path will be done later.
					loadNewImage(false, "");
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
		
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		//here we exit the program (maybe we should ask if the user really wants to do it?)
		  		//maybe we also want to store the polygons somewhere? and read them next time
		  		System.out.println(needToSave + " " + imageLabeler.getNeedToSave());
		  		if(needToSave || imageLabeler.getNeedToSave()){
		  			int answer = JOptionPane.showConfirmDialog(null, 
							"Do you want to save your labels first?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					if(answer == JOptionPane.YES_OPTION){
						//Call save
						try{
							saveLabels(false);
					  		System.out.println("Exiting...");
					    	System.exit(0);
						}
						catch (Exception e) {
							// TODO: handle exception
						}
					}
					/*
					 * Don't want to close the program if they don't wanna
					 */
					else if(answer == JOptionPane.CANCEL_OPTION){
						System.out.println("not closing");
						return;
					}
					else{
						System.out.println("Not saving is a bad idea");
				  		System.out.println("Exiting...");
				    	System.exit(0);
					}
		  		}
		  		System.exit(0);
		  	}
		});
	}


	protected void changeInsideColor() {
		imageLabeler.changeInsideColor();
	}


	protected void changeLabelColor() {
		imageLabeler.changeLabelColor();
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
	public void loadNewImage(boolean quickLoad, String givenPath) throws Exception{
		
		if(this.needToSave || imageLabeler.getNeedToSave()){
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
		
		String path;
		
		if(!quickLoad){
			String fc_path;
			if (imageFilename != "")
				fc_path = imageFilename.substring(0, imageFilename.lastIndexOf("/") + 1);
			else 
				fc_path = "./";
			FileChooser fc = new FileChooser(fc_path);
			path = fc.getPath();
			

			if(path.isEmpty()){
				return;
			}

			imageNav.clearAllElements();
			imageNav.resetPath(fc.returnDirectory());
		}
		else{
			path = givenPath;
		}
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
		this.needToSave = false;
		imageLabeler.setNeedToSave(false);
		
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
	        System.out.println(mappings.keySet());
	        if (saveAs || !mappings.containsKey(hashString) ) {
	        	FileChooser fc = new FileChooser(this.labelsFolder);
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
			this.needToSave = false;
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
			    	FileChooser fc = new FileChooser(this.labelsFolder);
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