package com.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.utils.ImageLabels;

public class LabelList extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageLabels labels = new ImageLabels();
	private JList labelList;
	private DefaultListModel listModel;
	private boolean isSelected = false;
	private int selectedIndex;


	public LabelList(ImageLabels imgLabels) {
		this.listModel = new DefaultListModel();
		this.labels = imgLabels;
		this.labelList = new JList(this.listModel);

		labelList.setCellRenderer(new LabelCellRenderer());
		labelList.setVisibleRowCount(6);
		JScrollPane pane = new JScrollPane(labelList);
		
		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 1) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		            Object o = theList.getModel().getElementAt(index);
		            isSelected = true;
		            selectedIndex = index;
		            System.out.println("Clicked on: " + index);
		          }
		        }
		      }
		    }; 
		 this.labelList.addMouseListener(mouseListener);
		add(pane);
	}

	public void addElement(String label) {
		// TODO Auto-generated method stub
		listModel.addElement(label);
		System.out.println("Size: " + listModel.size());
	}

	public boolean getIsSelected() {
		// TODO Auto-generated method stub
		return isSelected;
	}
	
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return selectedIndex;
	}

}
