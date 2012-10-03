package com.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.utils.ImageLabels;
import com.utils.PointsLabelPair;

public class SideFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageLabels currentImageLabels = null;

	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu file = new JMenu("File");
	
	private JMenuItem newImage = new JMenuItem("New Image");
	private JMenuItem loadLabel = new JMenuItem("Load Label");
	private JMenuItem saveLabel = new JMenuItem("Save Label");
	
	private JPanel sidePanel = new JPanel();
	private JPanel undoPanel = new JPanel();
	private JPanel overallPanel = new JPanel();
	
	private JButton newLabel = new JButton("New Label");
	private JButton editLabel = new JButton("Edit Label");
	private JButton deleteLabel = new JButton("Delete Label");
	private JButton undo = new JButton("Undo");
	private JButton redo = new JButton("Redo");
	
	public SideFrame()
	{
		initSidePanel();
	}

	private void initSidePanel()
	{
		menuBar.add(file);
		file.add(newImage);
		file.add(loadLabel);
		file.add(saveLabel);
		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		newLabel.setToolTipText("Click to add new object");

		sidePanel.add(newLabel);
		sidePanel.add(editLabel);
		sidePanel.add(deleteLabel);
		
		undoPanel.setLayout(new BoxLayout(undoPanel, BoxLayout.X_AXIS));
		undoPanel.add(undo);
		undoPanel.add(redo);
		
		overallPanel.setLayout(new BorderLayout());
		overallPanel.add(sidePanel, BorderLayout.WEST);
		overallPanel.add(undoPanel, BorderLayout.SOUTH);
		
		getContentPane().add(overallPanel);
	    setTitle("Tool bar");
	    setSize(200,200);
		setJMenuBar(menuBar);
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void setCurrentImageLabels(ImageLabels imageLabels) {
		this.currentImageLabels = imageLabels;
	}
	
	private void addNewLabel() {
		PointsLabelPair current = currentImageLabels.getCurrentLabel();
		
		if (current.size() >= 3) {
			currentImageLabels.finishCurrentLabel(current);
			//drawNode(currentImageLabels.startPoint, currentImageLabels.finalPoint);
		}
		
	}
}
