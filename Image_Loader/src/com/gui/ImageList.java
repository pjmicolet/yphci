package com.gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList imageList;
	private DefaultListModel imageModel;
	private boolean imageSelected = false;
	private int selectedIndex;
	private int count = 0;
	private String path;
	private int nextFiles = 0;
	private int actuallyAdded = 0;
	private String[] paths = new String[4];
	private ImageIcon[] nextIcons = new ImageIcon[4];

	public ImageList(String path) {
		this.imageModel = new DefaultListModel();
		this.path = path;
		try {
			addImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.imageList = new JList(imageModel);
		this.imageList.setVisibleRowCount(12);

		JScrollPane pane = new JScrollPane(imageList);

		add(pane);

	}

	public void addImages() throws IOException {
		File folder = new File(path);
		System.out.println(folder);
		File[] fileList = folder.listFiles();
		String name = "";
		System.out.println("next files " + nextFiles);
		if ( nextFiles <= fileList.length) {
			int i = nextFiles;
			int d = 0;

			while (actuallyAdded < 4 && i < fileList.length) {
				name = fileList[i].getName();
				if (name.endsWith(".jpg") || name.endsWith(".png")
						|| name.endsWith(".jpeg")) {
					System.out.println("Added in addImages()");
					paths[actuallyAdded] = fileList[i].getAbsolutePath();
					ImageIcon image = new ImageIcon(ImageIO.read(fileList[i])
							.getScaledInstance(50, 50, 50));
					nextIcons[actuallyAdded] = image;
					actuallyAdded++;
				}
				i++;
				d++;
			}
			
			if(actuallyAdded > 0){
				clearAllElements();
				for(int image = 0; image < actuallyAdded; image++){
					imageModel.addElement(nextIcons[image]);
				}
				nextFiles += d;
			}
			System.out.println(nextFiles);
			actuallyAdded = 0;
		}
	}

	public void backImages() throws IOException {
		File folder = new File(path);
		System.out.println(folder);
		File[] fileList = folder.listFiles();
		String name = "";

		if (nextFiles > 4) {
			int i = nextFiles;
			int d = 0;
			while (actuallyAdded < 4 && i > 0) {
				i--;
				name = fileList[i].getName();
				if (name.endsWith(".jpg") || name.endsWith(".png")
						|| name.endsWith(".jpeg")) {

					paths[actuallyAdded] = fileList[i].getAbsolutePath();
					ImageIcon image = new ImageIcon(ImageIO.read(fileList[i])
							.getScaledInstance(50, 50, 50));
					nextIcons[actuallyAdded] = image;
					actuallyAdded++;
				}
				d ++;
			}
			if(actuallyAdded > 0){
				clearAllElements();
				for(int image = 0; image < actuallyAdded; image++){
					imageModel.addElement(nextIcons[image]);
				}

				nextFiles -= d;
			}
			

			System.out.println(nextFiles);
			actuallyAdded = 0;
		}
	}

	public void clearAllElements() {
		imageModel.clear();
	}

	public void resetPath(String path) throws IOException {
		nextFiles = 0;
		this.path = path;
		addImages();
	}

	public void nextImages() {
		System.out.println("Yolo " + count);
		try {
			addImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void previousImages() {
		if(nextFiles > 4){
		try {
			backImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public String[] getPaths(){
		return this.paths;
	}
	
	public JList getJList(){
		return this.imageList;
	}

}
