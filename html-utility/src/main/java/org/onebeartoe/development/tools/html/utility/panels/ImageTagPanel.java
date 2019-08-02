
package org.onebeartoe.development.tools.html.utility.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

import org.onebeartoe.application.filesystem.FileSelectionMethods;
import org.onebeartoe.application.ui.GUITools;
import org.onebeartoe.application.ui.swing.FileSelectionPanel;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;
import org.onebeartoe.development.tools.html.utility.tasks.ImageTask;
import org.onebeartoe.filesystem.FileType;

public class ImageTagPanel extends JPanel implements ActionListener 
{
    final static long serialVersionUID = 8974787897798189498L;

    public static String title = "Resize Image Helper";

    private final FileSelectionPanel fileSelectionPanel;

    private final JButton actionButton;

    private final ScrollableTextArea statusPanel;
   
    private final JCheckBox outputToFileCheckbox;
    
    public ImageTagPanel() 
    {
        // this panel gives the user a button to click to pick an input direcotyr,  it also shows which files will be worked on.
        boolean showRecursive = true;
        fileSelectionPanel = new FileSelectionPanel(FileType.IMAGE, FileSelectionMethods.SINGLE_DIRECTORY, showRecursive);

        outputToFileCheckbox = new JCheckBox("Include JEE path");
        outputToFileCheckbox.setSelected(true);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        Border border = GUITools.factoryLineBorder("Input Images");
        inputPanel.setBorder(border);
        inputPanel.add(fileSelectionPanel, BorderLayout.CENTER);
        inputPanel.add(outputToFileCheckbox, BorderLayout.SOUTH);

        // this panel holds the  buttons that start the thumbnail generation and displays the status of the application.  Tthis is the text area the displays the status of the application
        statusPanel = new ScrollableTextArea("\n\n");

        // this panel holds the  buttons that start the thumbnail generation				
        actionButton = new JButton("Generate Image Tags");
        actionButton.addActionListener(this);

        // place the status, output, and action components onto a panel
        Border controlPanelBorder = GUITools.factoryLineBorder("Control and Status");
        JPanel statusAndActionControlPanel = new JPanel();
        statusAndActionControlPanel.setLayout(new BorderLayout());
        statusAndActionControlPanel.add(statusPanel, BorderLayout.CENTER);
        statusAndActionControlPanel.add(actionButton, BorderLayout.SOUTH);
        statusAndActionControlPanel.setBorder(controlPanelBorder);
        statusAndActionControlPanel.setMinimumSize( new Dimension(300, 1) );

//TODO: reinstate the progress panel         
//        outputPanel = new ScrollableTextArea("");        
//        JSplitPane controlPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, statusAndActionControlPanel, outputPanel);
        
        // define the JFrame content layout        
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, statusAndActionControlPanel);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        Object eventSource = ae.getSource();
        if (eventSource == actionButton) 
        {            
            File sourceDirectory = fileSelectionPanel.getCurrentDirectory();
                            
            boolean includeJeePath = outputToFileCheckbox.isSelected();
            
            TimerTask task = new ImageTask(sourceDirectory, statusPanel, includeJeePath);
            
            Date date = new Date();
            Timer timer = new Timer();
            timer.schedule(task, date);           
        }
    }
}
