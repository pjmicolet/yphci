package com.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainPanel extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String imageName;

	public MainPanel() {
		this.setVisible(true);

		}

	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public MainPanel( String imageName) throws Exception{
		this();
		this.imageName = imageName;
		//getImage();
		addImage();
	}

	/**
	 * Displays the image
	 */
	public void ShowImage() {
		Graphics g = this.getGraphics();

		if (image != null) {
			g.drawImage(
					image, 0, 0, null);
		}
	}

	public void getImage() throws IOException {
		image = ImageIO.read(new File(imageName));
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}

		//Dimension panelSize = new Dimension(image.getWidth(), image.getHeight());
//		Dimension panelSize = new Dimension(800, 600);
//		this.setSize(panelSize);
//		this.setMinimumSize(panelSize);
//		this.setPreferredSize(panelSize);
//		this.setMaximumSize(panelSize);
	}

	public void addImage() throws IOException {
		BufferedImage myPicture = ImageIO.read(new File(imageName));
		JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
		this.add( picLabel );
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//display iamge
		ShowImage();
	}
}