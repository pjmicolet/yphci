package com.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    private boolean done = false;
 
    public FileChooser() {
        super(new BorderLayout());
 
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        int returnVal = fc.showOpenDialog(FileChooser.this);
        System.out.println("Done");
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           file = fc.getSelectedFile();
           path = file.getAbsolutePath();
           done = true;
            //This is where a real application would open the file.
            log.append("Opening: " + file.getName() + "." + newline);
        } else {
            log.append("Open command cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());
    }
 
    public void actionPerformed(ActionEvent e) {
// 
//        //Handle open button action.
//        if (e.getSource() == openButton) {
//            int returnVal = fc.showOpenDialog(FileChooser.this);
// 
//            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                File file = fc.getSelectedFile();
//                //This is where a real application would open the file.
//                log.append("Opening: " + file.getName() + "." + newline);
//            } else {
//                log.append("Open command cancelled by user." + newline);
//            }
//            log.setCaretPosition(log.getDocument().getLength());
// 
//        //Handle save button action.
//        } else if (e.getSource() == saveButton) {
//            int returnVal = fc.showSaveDialog(FileChooser.this);
//            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                File file = fc.getSelectedFile();
//                //This is where a real application would save the file.
//                log.append("Saving: " + file.getName() + "." + newline);
//            } else {
//                log.append("Save command cancelled by user." + newline);
//            }
//            log.setCaretPosition(log.getDocument().getLength());
//        }
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
}
