package com.gui;

import javax.swing.*;
import java.awt.event.*;

public class LabelNameInput {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Input Dialog Box Frame");
		JButton button = new JButton("Show Input Dialog Box");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String str = JOptionPane.showInputDialog(null,
						"Enter some text : ", "Roseindia.net", 1);
				if (str != null)
					JOptionPane
							.showMessageDialog(null, "You entered the text : "
									+ str, "Roseindia.net", 1);
				else
					JOptionPane.showMessageDialog(null,
							"You pressed cancel button.", "Roseindia.net", 1);
			}
		});
		JPanel panel = new JPanel();
		panel.add(button);
		frame.add(panel);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
