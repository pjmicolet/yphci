package com.gui;

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
		
		add(pane);
	}

	public void addElement(String label) {
		// TODO Auto-generated method stub
		listModel.addElement(label);
		System.out.println("Size: " + listModel.size());
	}
	
	public void deleteElement(int index){
		listModel.remove(index);
	}

	public boolean getIsSelected() {
		// TODO Auto-generated method stub
		return isSelected;
	}
	
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return selectedIndex;
	}

	public void setIsSelected(boolean b) {
		isSelected = b;
	}
	
	public JList getJList() {
		return labelList;
	}

	public void setSelectedIndex(int index) {
		selectedIndex = index;
	}

}
