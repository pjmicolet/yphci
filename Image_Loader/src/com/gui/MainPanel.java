package com.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.utils.ImageLabels;
import com.utils.Point;
import com.utils.PointsLabelPair;


public class MainPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String imageName;
	private ImageLabels labels;
    private JLabel picLabel;
	
	public MainPanel() {
		addMouseListener(this);
		}

	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public MainPanel(String imageName, ImageLabels labels) throws Exception{
		this();
		this.labels = labels;
		this.imageName = imageName;
		//getImage();
		addImage();
	}

	/**
	 * Displays the image
	 */
	public void showImage() {
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
		this.image = ImageIO.read(new File(imageName));
		this.picLabel = new JLabel(new ImageIcon( this.image ));
		this.add( picLabel );
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//display iamge
		//showImage();

		for(PointsLabelPair label : labels.getPoints()) {
			drawLabel(label);
			finishLabel(label);
		}
		drawLabel(labels.getCurrentLabel());
	
	}
	
	public void drawLabel(PointsLabelPair label) {
		Graphics2D g = (Graphics2D)this.getGraphics();
		g.setColor(Color.PINK);
		for(int i = 0; i < label.size(); i++) {
			Point currentLabel = label.get(i);
			if (i != 0) {
				Point prevLabel = label.get(i - 1);
				g.drawLine(prevLabel.getX(), prevLabel.getY(), currentLabel.getX(), currentLabel.getY());
			}
			g.fillOval(currentLabel.getX() - 5, currentLabel.getY() - 5, 10, 10);
		}
	}
	public void finishLabel(PointsLabelPair label) {
		//if there are less than 3 vertices than nothing to be completed
		if (label.size() >= 3) {
			Point firstPoint = label.get(0);
			Point lastPoint = label.get(label.size() - 1);
		
			Graphics2D g = (Graphics2D)this.getGraphics();
			g.setColor(Color.GREEN);
			g.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}

		Graphics2D g = (Graphics2D)this.getGraphics();

		PointsLabelPair currentLabel = labels.getCurrentLabel();
		g.setColor(Color.PINK);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (currentLabel.size() != 0) {
				Point lastPoint = currentLabel.getLastPoint();
				g.drawLine(lastPoint.getX(), lastPoint.getY(), x, y);
			}
			g.fillOval(x-5,y-5,10,10);
			currentLabel.addPoint(x, y);
			labels.updateCurrentLabel(currentLabel);
			System.out.println(x + " " + y);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}