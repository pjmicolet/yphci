package com.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.utils.Point;
import com.utils.UtilityFunctions;


public class MainPanel extends JPanel implements MouseListener  {

	/**
	 * 
	 */
	
	private String imageName;
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private ArrayList<Point> polygon;
	public UtilityFunctions utility = new UtilityFunctions();



	public MainPanel() {
		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		}

	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public MainPanel( String imageName) throws Exception{
		this();
		image = ImageIO.read(new File(imageName));
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
			this.paint(image.getGraphics());
		}
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		//display iamge
		ShowImage();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
	
		if(utility.contained(x, y, image.getWidth(), image.getHeight()))
		{
			
		}
		
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	
}