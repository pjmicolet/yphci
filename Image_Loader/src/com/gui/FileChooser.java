package com.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FileChooser extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
    private File file;
    private String path;
    private String parentPath;
    private boolean done = false;
    
    public FileChooser(String pathname) {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
 
        //Create a file chooser
        fc = new JFileChooser(pathname);
 
        int returnVal = fc.showOpenDialog(FileChooser.this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           file = fc.getSelectedFile();
           path = file.getAbsolutePath();
           parentPath = file.getParent();
           //This is where a real application would open the file.
            log.append("Opening: " + file.getName() + "." + newline);
        } else {
        	//If we cancel just set the path to be empty.
        	path = "";
        	System.out.println("YOLO");
        	log.append("Open command cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());
    }
 
    public void actionPerformed(ActionEvent e) {
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileChooser.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    public String getPath(){
    	return path;
    }
    
    public boolean isDone(){
    	return done;
    }
    
    public String returnDirectory(){
    	return parentPath;
    }
}
