
package org.onebeartoe.development.tools.html.utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.onebeartoe.application.filesystem.FileSelectionMethods;
import org.onebeartoe.application.ui.GUITools;
import org.onebeartoe.application.ui.swing.FileSelectionPanel;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;
import org.onebeartoe.filesystem.FileType;

public class ImageTagPanel extends JPanel implements ActionListener 
{
    final static long serialVersionUID = 1321324897498189498L;

    public static String title = "Resize Image Helper";

    private final FileSelectionPanel fileSelectionPanel;

    private final JButton actionButton;

    private final ScrollableTextArea statusPanel;
    
//    private final JTextField qualityTextField;
    
    public ImageTagPanel() 
    {
        // this panel gives the user a button to click to pick an input direcotyr,  it also shows which files will be worked on.
        boolean showRecursive = true;
        fileSelectionPanel = new FileSelectionPanel(FileType.IMAGE, FileSelectionMethods.SINGLE_DIRECTORY, showRecursive);
        Border border = GUITools.factoryLineBorder("Input Images");
        fileSelectionPanel.setBorder(border);

        // these panel holds the components for the new image's dementions and filesize reduction percentage.  
//        Border border2 = GUITools.factoryLineBorder("% of Quality Kept");
//        qualityTextField = new JTextField("30");
//        qualityTextField.setBorder(border2);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(fileSelectionPanel, BorderLayout.CENTER);
//        inputPanel.add(qualityTextField, BorderLayout.SOUTH);

        // this panel holds the  buttons that start the thumbnail generation and displays the status of the application.  Tthis is the text area the displays the status of the application
        statusPanel = new ScrollableTextArea("\n\n");

        // this panel holds the  buttons that start the thumbnail generation				
        actionButton = new JButton("Resize");
        actionButton.addActionListener(this);

        // place the status and action components onto a panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(statusPanel, BorderLayout.CENTER);
        controlPanel.add(actionButton, BorderLayout.SOUTH);
        Border border3 = GUITools.factoryLineBorder("Control and Status");
        controlPanel.setBorder(border3);
//        controlPanel.setMinimumSize( new Dimension(650, 1) );

        // define the JFrame content layout        
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, controlPanel);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent ae) 
    {
        Object eventSource = ae.getSource();
        if (eventSource == actionButton) 
        {            
            File sourceDirectory = fileSelectionPanel.getCurrentDirectoty();
            TimerTask task = new ImageTask(sourceDirectory, statusPanel);
            Date date = new Date();
            Timer timer = new Timer();
            timer.schedule(task, date);           
        }
    }
}
