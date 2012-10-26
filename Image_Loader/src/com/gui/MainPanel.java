package com.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
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
	private ImageLabels labels;
    private boolean dragging = false;
    private PointsLabelPair currentLabel = null;
	private int dragIndex;
	private LabelList labelsList;
	private Dimension panelSize = new Dimension(800,600);
	private boolean needToSave;
	private Color labelColor = Color.BLUE;
	private Color insideColor = Color.CYAN;

	public MainPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		}

	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public MainPanel(ImageLabels labels, LabelList labelsList, boolean needToSave) throws Exception{
		this();
		setSize(panelSize);
		setMinimumSize(panelSize);
	    setPreferredSize(panelSize);
		setMaximumSize(panelSize);
		this.labels = labels;
		this.labelsList = labelsList;
		this.needToSave = needToSave;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
//		g.setColor(labelColor);
//		g2.setColor(Color.CYAN);
		for(PointsLabelPair label : labels.getPoints()) {
			drawLabel(label, g);
			finishLabel(label, g);
		}
		drawLabel(labels.getCurrentLabel(), g);
		if (labelsList.getIsSelected() && labels.size() != 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g2.setColor(insideColor);
			try {
				g2.fillPolygon(labels.getPoints().get(labelsList.getSelectedIndex()).getPolygon());
				g2.setColor(labels.getPoints().get(labelsList.getSelectedIndex()).getColor());
				g2.drawPolygon(labels.getPoints().get(labelsList.getSelectedIndex()).getPolygon());
			}
			catch (Exception e) {
			}
			
		}
	}

	/**
	 * Given a PointsLabelPair object, it will draw each vertice and line
	 * @param label
	 * @param g2
	 */
	public void drawLabel(PointsLabelPair label, Graphics g2) {
		for(int i = 0; i < label.size(); i++) {
			Point currentLabel = label.get(i);
			if (i != 0) {
				g2.setColor(label.getColor());
				Point prevLabel = label.get(i - 1);
				g2.drawLine(prevLabel.getX(), prevLabel.getY(), currentLabel.getX(), currentLabel.getY());
			}
			g2.setColor(this.labelColor);
			g2.fillOval(currentLabel.getX() - 5, currentLabel.getY() - 5, 5, 5);
		}
	}

	public void finishLabel(PointsLabelPair label, Graphics g2) {
		//if there are less than 3 vertices than nothing to be completed
		if (label.size() >= 3) {
			Point firstPoint = label.get(0);
			Point lastPoint = label.get(label.size() - 1);
			g2.setColor(label.getColor());
			g2.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
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
			g.setColor(this.labelColor);
			g.setColor(label.getColor());
			g.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
		}

		if (isCurrentLabel) {
			String labelName = JOptionPane.showInputDialog(null,
					"Enter object's label: ", "Enter Label", 1);
			boolean complete = false;
			while (!complete) {

				if(labelName == null){
					int answer = JOptionPane.showConfirmDialog(null, 
							"Are you sure you want to delete this label?", "Warning", JOptionPane.YES_NO_OPTION);
				    if (answer == JOptionPane.YES_OPTION) {
				      labels.resetCurrentLabel();
				      repaint();
				      return;
				    }
				    else{
				    	labelName = "";
				    }
				}

				else if (!labelName.isEmpty()) {
					label.setLabel(labelName);
					complete = true;
				}
				else{
					labelName = JOptionPane.showInputDialog(null,
					"Please enter object's label: ", "HCI FTW", 1);
				}

			}
		}
		label.setColor(labelColor);
		labels.closeCurrentLabel();
		labelsList.addElement(label.getLabel());
		
		this.needToSave = true;
	}

	public void resetLabels(){
		labels = new ImageLabels();
		this.needToSave = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (x > panelSize.getWidth() || y > panelSize.getHeight()) {
			//if not do nothing
			return;
		}

		Graphics2D g = (Graphics2D)this.getGraphics();
		PointsLabelPair currentLabel = labels.getCurrentLabel();
		g.setColor(labelColor);
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
			g.fillOval(x-5,y-5,5,5);
			currentLabel.addPoint(x, y);
			labels.updateCurrentLabel(currentLabel);
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
		dragging = false;
		this.needToSave = true;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point clickedPoint = new Point(x, y);
		for (PointsLabelPair pair : labels.getPoints())
			for (Point p : pair.getPoints()) {
				if (clickedPoint.distance(p.getX(), p.getY()) <=5) {
					this.currentLabel = labels.getPoint(p);
					dragging = true;
					this.dragIndex = this.currentLabel.getClosest(clickedPoint);
					return;
				}
			}
		this.needToSave = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragging) {
			int x = e.getX();
			int y = e.getY();

			if (x > panelSize.getWidth() || y > panelSize.getHeight() 
					|| y < 2 || x < 2 ) {
				return;
			}
			Point clickedPoint = new Point(x, y);
			currentLabel.getPoints().set(dragIndex, clickedPoint);
			labels.updateCurrentLabel(currentLabel);
			labels.closeCurrentLabel();
			repaint();
		}
		this.needToSave = true;
	}
	
	public void setLabels(ImageLabels labels){
		this.labels = labels;
	}
	
	public void setLabelsList(LabelList labelList){
		this.labelsList = labelList;
		this.needToSave = false;
	}
	
	public boolean getNeedToSave(){
		return this.needToSave;
	}
	
	public void setNeedToSave(boolean bool){
		this.needToSave = bool;
	}

	public void changeLabelColor() {
		labelColor = JColorChooser.showDialog(null, "Label Color", labelColor);
		repaint();
	}
	
	public void changeInsideColor() {
		insideColor = JColorChooser.showDialog(null, "Highlight Color", insideColor);
		repaint();
	}
	
	@Override
	public void setEnabled(boolean enable){
		super.setEnabled(enable);
		for (Component component : getComponents())
			component.setEnabled(enable);
		}
}