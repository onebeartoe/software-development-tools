
package org.onebeartoe.development.tools.html.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.onebeartoe.application.ui.swing.TabbedPane;
import org.onebeartoe.application.ui.swing.TaskPanel;

/**
	@author Roberto H. Marquez
*/
public class HtmlUtility extends JFrame implements ActionListener 
{
	JFileChooser fileChooser;
	
	// GUI components
	TabbedPane tabbedPane;
	private JPanel tab1;
	TaskPanel tab2;
	TaskPanel tab3;
	JButton selectBtn;
	private static File source_dir;

	// utility variables
	public String [] modes = {"Index Mode","Image Mode"};
	private String current_mode = modes[0];
	private int style;

	private Color colorValues[] = { Color.black, Color.blue, 
						  Color.red, Color.green };	
	private String fontNames[] = { "TimesRoman", "Courier",
						 "Helvetica" };

	public HtmlUtility() 
	{
		super( "HtmlUtility by onebeartoe.com" );
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		tab1 = new JPanel();
		String instructions = "\nClick the button to select directory you want to work with.\n";
		JLabel label = new JLabel(instructions);
		selectBtn = new JButton("Select a Directory");
		selectBtn.addActionListener(this);
		tab1.setLayout( new BorderLayout() );
		tab1.add(label, BorderLayout.CENTER);	
		tab1.add(selectBtn, BorderLayout.SOUTH);	

// TODO:                
//
// Make separate panels (like the one in the ResizeImageGui.java class) to fix 
// this mess by giving the index and image panels their own directoy chooser.
		IndexTask index_task = new IndexTask();
		tab2 = new TaskPanel(index_task, "index the directory");
	
		ImageTask image_task = new ImageTask(source_dir, null);	
		tab3 = new TaskPanel(image_task, "show images in the directory");
                
                ImageTagPanel itp = new ImageTagPanel();
                
		tabbedPane = new TabbedPane(tab1, tab2, tab3, itp);

		String [] titles = {"select directory", modes[0], modes[1], modes[1] + " - Real"};
		tabbedPane.setTabTitles(titles);
		getContentPane().add( tabbedPane );
		setSize(600, 500);
		setLocation(215,100);
		setVisible( true );
	}

	public void actionPerformed( ActionEvent e ) 
	{		
            int result = fileChooser.showOpenDialog(null);  
            
            source_dir = fileChooser.getSelectedFile();  

            if ( source_dir != null && source_dir.exists() && source_dir.isDirectory() ) 
            {
                tab2.setOnTask(true);
                tab3.setOnTask(true);
            }
            else 
            {
                tab2.setOnTask(false);
                tab3.setOnTask(false);
                JOptionPane.showMessageDialog( this, "The source path given was invalid.",
                                                 "Invalid Source", JOptionPane.ERROR_MESSAGE );
                tabbedPane.setSelectedIndex(0);			
            }
	}	

	public static void main(String [] args) 
	{
		HtmlUtility app = new HtmlUtility();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	public static File getSourceDir() 
        {
		return source_dir;
	}
}
