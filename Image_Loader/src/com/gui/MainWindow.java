package com.gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{

	public MainWindow(){
		initUI();
	}
	
	public final void initUI(){
		
		JPanel panel = new JPanel();
		SidePanel spanel = new SidePanel();
		panel.add(spanel.initTopPanel());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		getContentPane().add(panel);
		setTitle("Main Window");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
