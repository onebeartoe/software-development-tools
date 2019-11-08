
package org.onebeartoe.development.tools.html.utility.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import org.onebeartoe.application.PreferencesService;
import org.onebeartoe.application.filesystem.FileSelectionMethods;
import org.onebeartoe.application.ui.GUITools;
import org.onebeartoe.application.ui.swing.FileSelectionPanel;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;
import org.onebeartoe.filesystem.FileType;
import org.onebeartoe.web.utilities.jsp.JspSeedReport;
import org.onebeartoe.web.utilities.jsp.JspSeederService;
import org.onebeartoe.web.utilities.jsp.StreamedJspSeederService;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class JspSeederPanel extends JPanel implements ActionListener
{
    private transient Logger logger;
    
    // has package private for tests
    JTextField targetDirectory;
    
    // has package private for tests
    transient KeyListener targetDirKeyListener;
    
    private transient final JspSeederService seederService;
    
    private final FileSelectionPanel fileSelectionPanel;

    private final JButton actionButton;

    private final ScrollableTextArea statusPanel;
    
    private transient PreferencesService preferencesService;
    
    private final String JSP_SEEDER_CURRENT_DIRECTORY = "JSP_SEEDER_CURRENT_DIRECTORY";
    
    public JspSeederPanel(PreferencesService preferencesService)
    {
        logger = Logger.getLogger( getClass().getName() );
        
        seederService = new StreamedJspSeederService();
        
        this.preferencesService = preferencesService;
     
        // this panel gives the user a button to click to pick an input direcotyr,  it also shows which files will be worked on.
        boolean showRecursive = false;
        
        ActionListener preferenceSaveListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String key = JSP_SEEDER_CURRENT_DIRECTORY;
                File f = JspSeederPanel.this.fileSelectionPanel.getCurrentDirectory();
                String path = f.getAbsolutePath();
                
                try
                {
                    preferencesService.saveProperty(key, path);
                } 
                catch (BackingStoreException ex)
                {
                    String message = "The " + JSP_SEEDER_CURRENT_DIRECTORY + " could not be saved.";
                    logger.log(Level.SEVERE, message, ex);
                }
            }
        };
        
        fileSelectionPanel = new FileSelectionPanel(FileType.IMAGE, FileSelectionMethods.SINGLE_DIRECTORY, showRecursive, preferenceSaveListener);
        
        restorePreferences();
        
        targetDirectory = new JTextField();
        
        targetDirectory.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("inso");
                replaceBackslash();
            }
        });
        
        targetDirKeyListener = new TargetDirKeyListener();
        targetDirectory.addKeyListener(targetDirKeyListener);

        JPanel targetPanel = new JPanel( new GridLayout(2, 1, 5,5) );
        targetPanel.add( new JLabel("Target Path"));
        targetPanel.add(targetDirectory);
        
        Border border = GUITools.factoryLineBorder("Input");        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(border);
        inputPanel.add(fileSelectionPanel, BorderLayout.CENTER);
        inputPanel.add(targetPanel, BorderLayout.NORTH);

        // this panel holds the  buttons that start the thumbnail generation and displays the status of the application.  
        // Tthis is the text area the displays the status of the application
        statusPanel = new ScrollableTextArea("\n\n");

        // this panel holds the  buttons that start the thumbnail generation				
        actionButton = new JButton("Seed");
        actionButton.addActionListener(this);

        // place the status and action components onto a panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(statusPanel, BorderLayout.CENTER);
        controlPanel.add(actionButton, BorderLayout.SOUTH);
        Border border3 = GUITools.factoryLineBorder("Control and Status");
        controlPanel.setBorder(border3);
        controlPanel.setMinimumSize( new Dimension(300, 1) );

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
            File webRoot = fileSelectionPanel.getCurrentDirectory();
            replaceBackslash();
            String targetPath = targetDirectory.getText(); 
            
            SwingUtilities.invokeLater( () ->
            {
                try 
                {
                    JspSeedReport report = seederService.seedIndex(webRoot, targetPath);
                    
                    String text = report.toString();
                    
                    statusPanel.setText(text);
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                    
                    String message = "\n" + "Error: " + ex.getMessage();
                    statusPanel.setText(message);
                }
            });
        }
    }

    private void replaceBackslash()
    {
        String old = targetDirectory.getText();
        String newText = old.replace("\\", "/");

        targetDirectory.setText(newText);        
    }
    
    private void restorePreferences()
    {
        String defaultValue = null;
        String currentDirectory = preferencesService.get(JSP_SEEDER_CURRENT_DIRECTORY, defaultValue);
     
        if(currentDirectory == null)
        {
            String noSelectionMessage = "~/Desktop/";
            currentDirectory = noSelectionMessage;
        }
        
        File d = new File(currentDirectory);
        fileSelectionPanel.setCurrentDirectory(d);
    
        String message = "OpenSCAD file: " + currentDirectory;
        logger.log(Level.INFO, message);
    }

    private class TargetDirKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            System.out.println("kt");
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            System.out.println("kp");
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
            System.out.println("kr");

            String path = targetDirectory.getText();

            File file = new File(path);

            String webapp = "webapp";
            
            String absolutePath = file.getAbsolutePath();
            
            boolean isValidWebappPath = file.exists()
                                        && file.isAbsolute()
                                        && file.isDirectory()
                                        && absolutePath.contains(webapp);

            if(isValidWebappPath)
            {
                int webappStartIndex = absolutePath.indexOf(webapp);       

                int webappEndIndex = webappStartIndex + webapp.length();
                
                String subpath = absolutePath.substring(0, webappEndIndex);

                File subpathFile = new File(subpath);
                
                fileSelectionPanel.setCurrentDirectory(subpathFile);
                
                int targetPathEndIndex = absolutePath.length();
                
                int targetPathStartIndex = webappEndIndex + 1;
                
                String targetPath = absolutePath.substring(targetPathStartIndex, targetPathEndIndex);
                
                targetDirectory.setText(targetPath);
            }
            else
            {
                System.out.println("not at valid webapp path");
            }
        }        
    }
}
