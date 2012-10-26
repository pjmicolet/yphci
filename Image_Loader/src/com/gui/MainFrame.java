package com.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import java.util.Arrays;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.utils.ImageLabels;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String mappingsPathname = "./labels/mappings";
	private static final String labelsFolder = "./labels";

	private final String defaultImg = "./res/help.jpeg";

	private String currentImagePath = "./";

	private JPanel appPanel = new JPanel();
	private JPanel toolPanel = new JPanel();
	private JPanel labelTools = new JPanel();
	private JPanel imagePanel = new JPanel();
	private JPanel imageTool = new JPanel();
	private JPanel nextBackImage = new JPanel();

	private JLabel imageLabel = new JLabel();

	private JButton newLabel = new JButton("(F)inish Label", new ImageIcon(
			"./res/newlabel.gif"));
	private JButton editLabel = new JButton("(E)dit Label Name", new ImageIcon(
			"./res/edit.gif"));
	private JButton deleteLabel = new JButton("(D)elete Label", new ImageIcon(
			"./res/delete.gif"));
	private JButton cancelLabel = new JButton("C(a)ncel Label", new ImageIcon(
			"./res/cancel.gif"));
	private JButton deselectLabel = new JButton("Desele(c)t Label",
			new ImageIcon("./res/deselect.gif"));
	private JButton nextImages = new JButton("Ne(x)t Images", new ImageIcon(
			"./res/next.gif"));
	private JButton previousImages = new JButton("(P)revious Images",
			new ImageIcon("./res/previous.gif"));

	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");
	private JMenu help = new JMenu("Help");

	private JMenuItem about = new JMenuItem("About", new ImageIcon(
			"./res/info.gif"));
	private JMenuItem helpSection = new JMenuItem("How to?", new ImageIcon(
			"./res/help.gif"));

	private JMenuItem newImage = new JMenuItem("New Image", new ImageIcon(
			"./res/new.gif"));
	private JMenuItem loadLabel = new JMenuItem("Load Label", new ImageIcon(
			"./res/load.gif"));
	private JMenuItem saveLabel = new JMenuItem("Save Label", new ImageIcon(
			"./res/save.gif"));
	private JMenuItem saveAsLabel = new JMenuItem("Save As Label",
			new ImageIcon("./res/save.gif"));

	private MainPanel imageLabeler = new MainPanel();

	private JButton labelColorChooser = new JButton("(L)abel colour",
			new ImageIcon("./res/colour.gif"));
	private JButton insideColorChooser = new JButton("(H)ighlight colour",
			new ImageIcon("./res/colour.gif"));

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

	private String imgExts[] = { "jpg", "jpeg", "png", "gif" };

	/**
	 * Runs the program
	 * 
	 * @param argv
	 *            path to an image
	 */
	public MainFrame(String imageFilename, ImageLabels labels) {
		try {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.labels = labels;
			this.imageFilename = imageFilename;
			this.setTitle("Labeler Pro");
			setupGUI(imageFilename);
		} catch (Exception e) {
			System.err.println("Image: " + imageFilename);
			e.printStackTrace();
		}
	}

	// TODO: Make this pretty.
	/**
	 * sets up application window
	 * 
	 * @param imageFilename
	 *            image to be loaded for editing
	 * @throws Exception
	 */
	public void setupGUI(String imageFilename) throws Exception {
		// Get the image and scale it.
		getImage();

		labelList = new LabelList(this.labels);

		imageNav = new ImageList(currentImagePath);

		menuBar.add(file);
		menuBar.add(help);

		newImage.setMnemonic(KeyEvent.VK_I);
		newImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));
		loadLabel.setMnemonic(KeyEvent.VK_L);
		loadLabel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		saveLabel.setMnemonic(KeyEvent.VK_S);
		saveLabel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		saveAsLabel.setMnemonic(KeyEvent.VK_A);
		saveAsLabel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));

		helpSection.setMnemonic(KeyEvent.VK_H);
		helpSection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));

		about.setMnemonic(KeyEvent.VK_B);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				ActionEvent.CTRL_MASK));

		// newImage.getAccessibleContext().setAccessibleDescription(
		// "This doesn't really do anything");
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
		file.add(saveAsLabel);

		help.add(helpSection);
		help.add(about);

		imagePanel.add(imageLabel);
		imagePanel.setOpaque(true);

		/*
		 * We add all the buttons to the toolPanel
		 */
		toolPanel.setLayout(new GridLayout(0, 2));
		toolPanel.add(newLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(editLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(deleteLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(cancelLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(deselectLabel);
		toolPanel.add(Box.createGlue());
		toolPanel.add(labelList);

		nextBackImage.setLayout(new GridLayout(0, 2));
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
		 * This is is the panel that contains the list of labels and the
		 * buttons.
		 */
		labelTools.setLayout(new BoxLayout(labelTools, BoxLayout.Y_AXIS));
		labelTools.add(toolPanel);
		labelTools.add(intermediatePanel);
		labelTools.add(intermediatePanel2);
		labelTools.add(nextBackImage);

		// setup main window panel
		appPanel = new JPanel();
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);

		// Create and set up the image panel.

		imageLabeler = new MainPanel(labels, labelList, this.needToSave);
		imageLabeler.setOpaque(false); // content panes must be opaque
		imageTool.setLayout(new BorderLayout());

		imageTool.add(imageLabeler, BorderLayout.CENTER);
		imageTool.add(imagePanel, BorderLayout.CENTER);

		appPanel.add(labelTools);
		appPanel.add(imageTool);
		this.setJMenuBar(menuBar);

		this.setResizable(false);
		// display all the stuff
		this.pack();

		ListSelectionListener selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
				JList theList = (JList) listEvent.getSource();
				int index = theList.getSelectedIndex();
				// if (index >= 0) {
				// if((lastClickedIndex == index && index != -1)){
				//
				// labelList.deselect();
				// labelList.setIsSelected(false);
				// lastClickedIndex = -1;
				// imageLabeler.repaint();
				// }
				// else
				// {
				// lastClickedIndex = index;
				labelList.setIsSelected(true);
				labelList.setSelectedIndex(index);
				imageLabeler.repaint();
				// }
				// }

			}
		};

		this.labelList.getJList().addListSelectionListener(selectionListener);

		ListSelectionListener imageSelection = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent listEvent) {
				JList theList = (JList) listEvent.getSource();
				int index = theList.getSelectedIndex();
				if (index >= 0) {
					String[] paths = imageNav.getPaths();
					try {
						/*
						 * This is a quick load so set quickLoad to true and
						 * feed it the path.
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
					JOptionPane
							.showMessageDialog(
									null,
									"You can start drawing a new label by clicking on the image",
									"New Label", JOptionPane.OK_CANCEL_OPTION);
				} else if (labels.getCurrentLabel().size() < 3) {
					JOptionPane
							.showMessageDialog(
									null,
									"You need to draw at least 3 points to finish a label",
									"New Label",
									JOptionPane.INFORMATION_MESSAGE);
				} else {
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
				System.out.println("action");
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

		
		newLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('f'), "new label");
		newLabel.getActionMap().put("new label", newLabelAction);
		newLabel.addActionListener(newLabelAction);

		nextImages.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('x'), "next images");
		nextImages.getActionMap().put("next images", nextImagesAction);
		nextImages.addActionListener(nextImagesAction);


		previousImages.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('p'), "previous images");
		previousImages.getActionMap().put("previous images",
				previousImagesAction);
		previousImages.addActionListener(previousImagesAction);

		labelColorChooser.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('l'), "label colour");
		labelColorChooser.getActionMap().put("label colour", labelColorAction);
		labelColorChooser.addActionListener(labelColorAction);

		insideColorChooser.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('h'), "highlight colour");
		insideColorChooser.getActionMap().put("highlight colour",
				highlightColorAction);
		insideColorChooser.addActionListener(highlightColorAction);

		AbstractAction editAction = new AbstractAction() {

			/**
		 * 
		 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (labelList.getIsSelected()) {
					int index = labelList.getSelectedIndex();
					String defaultName = labels.getPoints().get(index).getLabel();
					String newName = "";
					newName = JOptionPane.showInputDialog(null,
							"Enter a new name for this label", "Edit Label", 1, null, null, defaultName).toString();
					if (newName != null) {
						if (!newName.isEmpty()) {
							index = labelList.getSelectedIndex();
							labels.getPoints().get(index).setLabel(newName);
							labelList.updateName(newName);
							JOptionPane.showMessageDialog(null,
									"Name was successfully changed!",
									"Edit Label",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}

					needToSave = true;
				} else {
					JOptionPane.showMessageDialog(null,
							"Please select a label!", "Edit Label",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};

		editLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('e'), "edit label");
		editLabel.getActionMap().put("edit label", editAction);
		editLabel.addActionListener(editAction);

		loadLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				loadLabels(true);
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
				if (labelList.getIsSelected()) {
					int index = labelList.getSelectedIndex();
					labelList.deleteElement(index);
					labels.removeLabel(index);
					labelList.setIsSelected(false);

					needToSave = true;
				} else {
					JOptionPane.showMessageDialog(null,
							"Please select a label!", "Delete Label",
							JOptionPane.INFORMATION_MESSAGE);
				}

				imageTool.repaint();
			}
		};

		AbstractAction cancelAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				labels.resetCurrentLabel();
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

		deleteLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('d'), "delete label");
		deleteLabel.getActionMap().put("delete label", deleteAction);
		deleteLabel.addActionListener(deleteAction);

		cancelLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('a'), "cancel label");
		cancelLabel.getActionMap().put("cancel label", cancelAction);
		cancelLabel.addActionListener(cancelAction);

		deselectLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('c'), "deselect label");
		deselectLabel.getActionMap().put("deselect label", deselectAction);
		deselectLabel.addActionListener(deselectAction);

		// nextImages.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// imageNav.nextImages();
		// imageTool.repaint();
		// }
		// });

		// previousImages.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		//
		// imageNav.previousImages();
		// imageTool.repaint();
		//
		// }
		// });

		newImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					// Tell load new image this isn't a quick load and give it
					// an empty string as getting the path will be done later.
					loadNewImage(false, "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					// Tell load new image this isn't a quick load and give it
					// an empty string as getting the path will be done later.
					JOptionPane
							.showMessageDialog(
									null,
									"Design and implementation by Paul-Jules Micolet and Yordan Petrov",
									"About", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		helpSection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					// Tell load new image this isn't a quick load and give it
					// an empty string as getting the path will be done later.
					// loadNewImage(true, defaultImg);
					JTextArea msg = new JTextArea(
							"Shortcuts:\n \nOpen New Image -> Ctrl + I\n"
									+ "Load labels file -> Ctrl + L\n"
									+ "Save labels file -> Ctrl + S\n"
									+ "Save labels file As -> Ctrl + A\n"
									+ "Help Section -> Ctrl + H\n"
									+ "About -> Ctrl + B\n"
									+ "Finish current label -> f\n"
									+ "Edit selected label -> e\n"
									+ "Delete selected label -> d\n"
									+ "Cancel drawing current label -> a\n"
									+ "De-select selected label -> c\n"
									+ "Display previous 4 images in the current directory -> p\n"
									+ "Display next 4 images in the current directory -> x\n"
									+ "Change label colour -> l\n"
									+ "Change highlight colour -> h\n"
									+ "\n"
									+ "Useful tip:\n"
									+ "You can resize the label polygons after creation by "
									+ "clicking on a vertex and dragging in the desired direction.\n"
									+ "\n"
									+ "Supported formats: .jpg, .jpeg, .png, .gif"
									+ "");
					msg.setLineWrap(true);
					msg.setWrapStyleWord(true);
					msg.setEditable(false);
					msg.setSize(400, 800);

					JScrollPane scrollPane = new JScrollPane(msg);

					JOptionPane.showMessageDialog(null, scrollPane, "Help",
							JOptionPane.INFORMATION_MESSAGE);
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
				} catch (Exception e) {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				// here we exit the program (maybe we should ask if the user
				// really wants to do it?)
				// maybe we also want to store the polygons somewhere? and read
				// them next time
				if (needToSave || imageLabeler.getNeedToSave()) {
					int answer = JOptionPane.showConfirmDialog(null,
							"Do you want to save your labels first?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						// Call save
						try {
							saveLabels(false);
							System.exit(0);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					/*
					 * Don't want to close the program if they don't wanna
					 */
					else if (answer == JOptionPane.CANCEL_OPTION) {
						return;
					} else {
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
	public void paint(Graphics g) {
		// super.paint(g);
		super.paint(g);
		imageTool.paint(imageTool.getGraphics());
	}

	/**
	 * Finishes the current label.
	 */
	public void finishLabel() {
		imageLabeler.finishLabel(true);
	}

	/**
	 * Starts a FileChooser window to choose a new image and then sets that
	 * image as the current JLabel.
	 * 
	 * @throws Exception
	 */
	public void loadNewImage(boolean quickLoad, String givenPath)
			throws Exception {

		if (this.needToSave || imageLabeler.getNeedToSave()) {
			int answer = JOptionPane.showConfirmDialog(null,
					"Do you want to save your labels first?", "Warning",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				// Call save
				try {
					saveLabels(false);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else if (answer == JOptionPane.CANCEL_OPTION) {
				// Don't do anything
				return;
			}
		}

		String path;

		if (!quickLoad) {
			String fc_path;
			if (imageFilename != "")
				fc_path = imageFilename.substring(0,
						imageFilename.lastIndexOf("/") + 1);
			else
				fc_path = "./";
			FileChooser fc = new FileChooser(fc_path);
			path = fc.getPath();

			if (path.isEmpty()) {
				return;
			}
			int lastDot = path.lastIndexOf('.');
			String extension = path.substring(lastDot + 1);
			if (!Arrays.asList(imgExts).contains(extension)) {
				JOptionPane
						.showMessageDialog(
								null,
								"An error occured while loading your image! Loading default help screen..",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			imageNav.clearAllElements();
			imageNav.resetPath(fc.returnDirectory());
		} else {
			path = givenPath;
		}
		/*
		 * this is to check if we've cancelled getting a new image.
		 */
		if (path.isEmpty()) {
			return;
		}
		this.imageFilename = path;
		imageIcon = new ImageIcon(imageFilename);
		Image img = imageIcon.getImage();
		img = img.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
		imageIcon.setImage(img);
		imageLabel.setIcon(imageIcon);
		resetLabels();
		loadLabels(false);
		imageTool.repaint();
		this.needToSave = false;
		imageLabeler.setNeedToSave(false);
	}

	/**
	 * Uses the image path to set the image, rescale it and put it in a JLabel.
	 * 
	 * @throws Exception
	 */
	public void getImage() throws Exception {

		this.image = ImageIO.read(new File(imageFilename));
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800
					: (image.getWidth() * 600) / image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600
					: (image.getHeight() * 800) / image.getWidth();
			Image scaledImage = image.getScaledInstance(newWidth, newHeight,
					Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}

		this.imageLabel = new JLabel(new ImageIcon(this.image));
	}

	/**
	 * Resets the current labels and deletes all elements within the labelList
	 * 
	 * @throws Exception
	 */
	public void resetLabels() throws Exception {
		this.labels = new ImageLabels();
		imageLabeler.setLabels(labels);
		this.labelList.deleteAllElements();
		imageLabeler.setLabelsList(labelList);
	}

	/**
	 * Saves a label using serialization magic. TODO: Get it so that we don't
	 * always save under file called labels. TODO: Change button to Save,
	 * instead of Open
	 */
	public void saveLabels(boolean saveAs) {
		try {
			FileInputStream map;
			try {
				map = new FileInputStream(mappingsPathname);
				ObjectInputStream mapOis = new ObjectInputStream(map);
				mappings = (HashMap<String, String>) mapOis.readObject();
				map.close();
			} catch (FileNotFoundException exc) {
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

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte[] hash = md.digest();

			String hashString = returnHex(hash);
			String labelsPathname;
			if (saveAs || !mappings.containsKey(hashString)) {
				FileChooser fc = new FileChooser(this.labelsFolder);
				labelsPathname = fc.getPath();
				if (labelsPathname.isEmpty()) {
					return;
				}
				this.currentPath = labelsPathname;
			} else {
				labelsPathname = this.currentPath;
			}
			mappings.put(hashString, labelsPathname);

			FileOutputStream labelsFos = new FileOutputStream(labelsPathname);
			ObjectOutputStream labelsOos = new ObjectOutputStream(labelsFos);
			labelsOos.writeObject(labels);
			labelsOos.flush();
			labelsOos.close();

			FileOutputStream mappingsFos = new FileOutputStream(
					mappingsPathname);
			ObjectOutputStream mappingsOos = new ObjectOutputStream(mappingsFos);
			mappingsOos.writeObject(mappings);
			mappingsOos.flush();
			mappingsOos.close();
			this.needToSave = false;
			imageLabeler.setNeedToSave(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String returnHex(byte[] inBytes) throws Exception {
		String hexString = null;
		for (int i = 0; i < inBytes.length; i++) { // for loop ID:1
			hexString += Integer.toString((inBytes[i] & 0xff) + 0x100, 16)
					.substring(1);
		} // Belongs to for loop ID:1
		return hexString;
	} // Belongs to returnHex class

	/**
	 * Loads the labels and makes sure everything is nice and clean...ish
	 */
	public void loadLabels(boolean clickedFromMenu) {
		String labelsPathname = "";
		try {
			FileInputStream map;
			if (!clickedFromMenu) {
				try {
					map = new FileInputStream(mappingsPathname);
					ObjectInputStream mapOis = new ObjectInputStream(map);
					mappings = (HashMap<String, String>) mapOis.readObject();
				} catch (FileNotFoundException exc) {
					createNewMappings();
					map = new FileInputStream(mappingsPathname);
					ObjectInputStream mapOis = new ObjectInputStream(map);
					mappings = (HashMap<String, String>) mapOis.readObject();
				}

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				File input = new File(this.imageFilename);
				BufferedImage buffImg = ImageIO.read(input);
				ImageIO.write(buffImg, "png", outputStream);
				byte[] data = outputStream.toByteArray();

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(data);
				byte[] hash = md.digest();

				String hashString = returnHex(hash);
				if (mappings.containsKey(hashString)) {
					labelsPathname = mappings.get(hashString);
				} else {
					labelsPathname = "";
				}

				FileInputStream fis;
				try {
					fis = new FileInputStream(labelsPathname);
					String options[] = new String[] { "Yes",
							"No, I will load it myself",
							"No, I don't want to load any labels" };
					String message = "We have detected a labels file for this image. Do you want to load it?";
					JLabel messageLabel = new JLabel(message, JLabel.CENTER);
					int answer = JOptionPane.showOptionDialog(null,
							messageLabel, "Warning", JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[0]);
					if (answer == 0) {
						ObjectInputStream ois = new ObjectInputStream(fis);
						labels = (ImageLabels) ois.readObject();
						ois.close();
					} else if (answer == 1) {
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
							JOptionPane
									.showMessageDialog(
											null,
											"There was a problem loading your labels file. Creating a new one..",
											"Warning",
											JOptionPane.INFORMATION_MESSAGE);
						}
					}
					// }
				} catch (FileNotFoundException e) {
				}
			} else {
				FileChooser fc = new FileChooser(this.labelsFolder);
				labelsPathname = fc.getPath();
				this.imageFilename = fc.getPath();
				try {
					FileInputStream fis = new FileInputStream(labelsPathname);
					ObjectInputStream ois = new ObjectInputStream(fis);
					labels = (ImageLabels) ois.readObject();
					ois.close();
				} // TEST FOR WRONG LABEL FILES
				catch (Exception e1) {
					if (!(labelsPathname == "")) {
						JOptionPane
								.showMessageDialog(
										null,
										"There was a problem loading your labels file. Creating a new one..",
										"Warning",
										JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			this.currentPath = labelsPathname;

			imageLabeler.setLabels(labels);
			labelList.deleteAllElements();
			labelList.addAllElements(labels);
			imageLabeler.setLabelsList(labelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// this.pack();
	}

	private void createNewMappings() {
		try {
			FileOutputStream map = new FileOutputStream(mappingsPathname);
			ObjectOutputStream mapOis = new ObjectOutputStream(map);
			mapOis.writeObject(mappings);
			mapOis.flush();
			mapOis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}