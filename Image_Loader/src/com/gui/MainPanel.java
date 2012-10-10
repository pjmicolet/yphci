package com.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.utils.ImageLabels;
import com.utils.Point;
import com.utils.PointsLabelPair;


public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String imageName;
	private ImageLabels labels;
    private JLabel picLabel;
    private boolean dragging = false;
    private PointsLabelPair currentLabel = null;
	private int dragIndex;
	
	public MainPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
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
		this.add( picLabel );
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

	@Deprecated
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
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}
		this.picLabel = new JLabel(new ImageIcon( this.image ));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//display iamge
		//showImage();

		for(PointsLabelPair label : labels.getPoints()) {
//			System.out.println(label.size());
			drawLabel(label);
			finishLabel(label);
		}
		//System.out.println(labels.getCurrentLabel());
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
			g.setColor(Color.PINK);
			g.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
		}
	}
	
	// Nicer finishLabel function, makes it easier to call outside of the MainPanel class.
	// Keeping the old version atm just for simplicity's sake but should consider replacing it.
	public void finishLabel(boolean isCurrentLabel){
		PointsLabelPair label = labels.getCurrentLabel();

		if (label.size() >= 3) {
			Point firstPoint = label.get(0);
			Point lastPoint = label.get(label.size() - 1);
		
			Graphics2D g = (Graphics2D)this.getGraphics();
			g.setColor(Color.PINK);
			g.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
		}
		
		if (isCurrentLabel) {
			String labelName = JOptionPane.showInputDialog(null,
					"Enter object's label: ", "HCI FTW", 1);
//			if (labelName != null && labelName == " ")
//				JOptionPane
//						.showMessageDialog(null, "Label set to : "
//								+ labelName, "HCI FTW", 1);
//			else
//				JOptionPane.showMessageDialog(null,
//						"Aborting current object..", "HCI FTW", 1);
			if (labelName != null && labelName == " ")
				label.setLabel(labelName);
		}
		labels.closeCurrentLabel();
		//System.out.println(labels.getPoints().size());
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
				Point firstPoint = currentLabel.get(0);
				Point lastPoint = currentLabel.getLastPoint();
				int distance = firstPoint.distance(x, y);
				if (distance <= 5) {
					finishLabel(true);
					return;
					//TODO: get labeling prompt to appear.
				}
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
	public void mouseReleased(MouseEvent e) {
		System.out.println("stop drag");
		dragging = false;
	}
	
	public void resetImage(String newPath, ImageLabels newLabels) throws IOException{
		labels = newLabels;
		imageName = newPath;
		ImageIcon icon = new ImageIcon(newPath);
		picLabel.setIcon(icon);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//PointsLabelPair currentLabel = new PointsLabelPair();
		Point clickedPoint = new Point(x, y);
//		if (labels.getPoints().contains(clickedPoint)) {
		for (PointsLabelPair pair : labels.getPoints())
			for (Point p : pair.getPoints()) {
				if (clickedPoint.distance(p.getX(), p.getY()) <=5) {
					this.currentLabel = labels.getPoint(p);
					dragging = true;
					this.dragIndex = this.currentLabel.getClosest(clickedPoint);
					System.out.println("here");
					return;
				}
			}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("dragged");
		if (dragging) {
			int x = e.getX();
			int y = e.getY();
			
			Point clickedPoint = new Point(x, y);
			//System.out.println("x: " + x + " y: " + y);
			currentLabel.getPoints().set(dragIndex, clickedPoint);
			labels.updateCurrentLabel(currentLabel);
			labels.closeCurrentLabel();
			paint(this.getGraphics());
		}
	}
}